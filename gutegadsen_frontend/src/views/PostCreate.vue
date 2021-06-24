<template>
  <div class="create-post-container">
    <b-card
        title="Post erstellen">
      <b-form @submit="submitPost">
        <b-form-group
            id="title-group"
            label="Titel"
            label-for="title">
          <b-form-input id="title"
                        v-model="title"
                        trim
                        required></b-form-input>
        </b-form-group>
        <b-form-group
            id="tags-group"
            label="Tags">
          <b-select :options="tags" v-model="tagToAdd"></b-select>
        </b-form-group>

        <b-form-group
            id="tags-selected-group"
            label="Ausgewählte Tags">
          <span class="selected-tag" v-for="tag in selectedTags" :key="tag"
                @click="() => removeTag(tag)">{{ tag }}</span>
        </b-form-group>

        <b-form-group
            id="image-canvas-group"
            label="Bild">
          <canvas height="720" width="1280" id="image-canvas" class="image-canvas"></canvas>
          <b-form-input type="range" min=".01" max="5" step=".01" v-model="imageScale"></b-form-input>
        </b-form-group>

        <b-form-group
            id="image-upload-group"
            label="Bild hochladen"
        >
          <b-form-file
              placeholder="Bild hier einfügen oder auswählen"
              v-model="imageFile"
              :state="Boolean(imageFile)"
          ></b-form-file>
        </b-form-group>
        <b-button type="submit" :disabled="postCreationPending" variant="primary">Post erstellen</b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import endpoints from "@/config/endpoints.config"

export default {
  name: "PostCreate",
  data() {
    return {
      title: "",
      tags: [],
      selectedTags: [],
      tagToAdd: undefined,
      tagToRemove: undefined,
      imageFile: undefined,
      imageScale: 1,
      postCreationPending: false
    }
  },
  mounted() {
    fetch(endpoints.api.tags.all)
        .then(response => response.json())
        .then(json => this.tags = json)
  },
  methods: {
    submitPost(event) {
      event.preventDefault();
      let requestBody = {
        title: this.title,
        tags: this.selectedTags,
        imageDataString: document.getElementById("image-canvas").toDataURL(),
        username: localStorage.getItem("username"),

      }

      fetch(endpoints.api.posts.create, {
        method: "POST",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json"
        }
      })
      //TODO redirect to post list
    },
    removeTag(tag) {
      this.tags.push(tag)
      this.selectedTags = this.selectedTags.filter(tagelem => tagelem !== tag);
    },
    showImageLoadError() {
      this.$bvToast.toast("Das Bild konnte nicht geladen werden!", {
        title: "Fehler",
        variant: "danger",
        autoHideDelay: 5000
      })
    },
    drawImageFirstTime() {
      let canvas = document.getElementById('image-canvas');
      // canvas.width = this.imageObject.width;
      // canvas.height = this.imageObject.height;
      let ctx = canvas.getContext('2d');
      ctx.drawImage(this.imageObject, 0, 0);
    }
  },
  computed: {
    imageObject() {
      let img = new Image();
      img.onload = this.drawImageFirstTime;
      img.onerror = this.showImageLoadError;
      img.src = URL.createObjectURL(this.imageFile);
      return img;
    }
  },
  watch: {
    tagToAdd(newTag) {
      if (newTag === undefined) return;
      this.selectedTags.push(newTag)
      this.tags = this.tags.filter(tag => tag !== newTag);
    },
    imageScale(newScale) {
      let canvas = document.getElementById('image-canvas');
      let ctx = canvas.getContext("2d");
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ctx.setTransform(newScale, 0, 0, newScale, canvas.width / 2 - this.imageObject.width / 2 * newScale, canvas.height / 2 - this.imageObject.height / 2 * newScale);
      ctx.drawImage(this.imageObject, 0, 0);
    }
  }
}
</script>

<style lang="scss" scoped>
.create-post-container {
  margin: 5rem auto;
  width: 50vw;

  @media screen and (max-width: 1200px) {
    width: 90vw;
    margin: 6rem auto;
  }
}

.selected-tag {
  margin-right: 1rem;
  border: solid black 1px;
  border-radius: 5px;
  padding: .5rem;

  transition: all 300ms ease;

  &:hover {
    border-color: red;
    color: red;
  }
}

.image-canvas {
  border: solid 1px black;
  border-radius: 2px;
  width: 70%;
  height: 50%;
}
</style>
