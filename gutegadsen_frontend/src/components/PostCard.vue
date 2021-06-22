<template>
  <b-card
      class="post-card"
      :title="post.title"
      :img-src="imageDataString"
      img-top></b-card>
</template>
<script>
import endpoints from "@/config/endpoints.config"

export default {
  name: 'PostCard',
  props: ["post"],
  data() {
    return {
      imageDataString: undefined
    }
  },
  mounted() {
    fetch(endpoints.api.images.id(this.post.postImageId))
        .then(response => response.json())
        .then(json => this.imageDataString = json.imageDataString);
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

  }

}
</style>
