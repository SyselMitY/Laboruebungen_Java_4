<template>
  <div class="post-list">
    <div v-if="posts !== undefined" class="post-list-container">
      <PostCard v-for="post in posts" :key="post.id" :post="post"/>
    </div>
  </div>
</template>

<script>
import PostCard from "@/components/PostCard";
import endpoints from "@/config/endpoints.config"

export default {
  name: "PostList",
  components: {PostCard},
  data() {
    return {
      posts: undefined,
    }
  },
  mounted() {
    fetch(endpoints.api.posts.all)
        .then(value => value.json())
        .then(json => this.posts = json);
  }
}
</script>

<style scoped lang="scss">
.post-list-container {
  margin: 7vmax auto;
  width: fit-content;
}

</style>
