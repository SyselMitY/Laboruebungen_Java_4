<template>
  <b-card
      class="post-card"
      :title="post.title"
      :img-src="imageDataString"
      img-top>
    <template #footer>
      <div class="custom-card-footer">
        <span>Gepostet am {{ post.date }}</span>
        <span class="post-card-username">{{ post.user.username }}</span>
        <b-avatar class="post-card-avatar" :src="avatarData"></b-avatar>
        <b-button variant="danger" v-if="ableToDelete">Löschen</b-button>
      </div>
    </template>

    <template #header>
      <div class="custom-card-header">
        <span class="tag" v-for="tag in post.tags" :key="tag">{{ tag }}</span>
        <span class="flex-right">Upvotes: {{ post.upvoteCount }}</span>
        <b-button @click="upvoteHandler" v-if="loggedInUser" :variant="upvoted?'success':'secondary'">
          {{ upvoted ? "Upvote entfernen" : "Upvoten" }}
        </b-button>
      </div>
    </template>
  </b-card>
</template>
<script>
//TODO implement post deletion
import endpoints from "@/config/endpoints.config"

export default {
  name: 'PostCard',
  props: ["post", "upvoted", "loggedInUser"],
  data() {
    return {
      imageDataString: undefined,
      avatarData: undefined
    }
  },
  computed: {
    ableToDelete() {
      if (this.loggedInUser === undefined) return false
      else return this.loggedInUser.username === this.post.user.username;
    }
  },
  mounted() {
    fetch(endpoints.api.images.id(this.post.postImageId))
        .then(response => response.json())
        .then(json => this.imageDataString = json.imageDataString);

    if (this.post.user.profilePictureId !== null)
      fetch(endpoints.api.images.id(this.post.user.profilePictureId))
          .then(response => response.json())
          .then(json => this.avatarData = json.imageDataString);

  },
  methods: {
    async upvoteHandler() {
      let requestBody = {
        username: this.loggedInUser.username,
        upvoteState: !this.upvoted
      }
      await fetch(endpoints.api.posts.upvote(this.post.id), {
        method: "PUT",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json"
        }
      })
      this.$emit("upvoteevent");
      this.post.upvoteCount += requestBody.upvoteState?1:-1;
    },
  }
}
</script>

<style lang="scss" scoped>
.post-card {
  width: 55vw;
  max-height: 70vh;
  margin: 3rem 0;

  @media screen and (max-width: 770px) {
    width: 80vw;
  }

  img {
    height: 90%;
    object-fit: cover;
    width: 100%;
    flex-shrink: 1;
  }
}

.tag {
  border: solid black 1px;
  border-radius: 5px;
  padding: .5rem;
}

.custom-card-header {
  display: flex;
  gap: 1rem;
  align-items: center;

  & .flex-right {
    margin-left: auto;
  }
}

.custom-card-footer {
  display: flex;
  gap: 1rem;
  align-items: center;

  & .post-card-username {
    margin-left: auto;
  }
}

.card-title {
  margin-bottom: 0;
}
</style>
