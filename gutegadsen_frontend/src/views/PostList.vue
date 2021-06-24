<template>
  <div class="post-list">
    <div v-if="posts !== undefined" class="post-list-container">
      <PostCard v-for="post in posts" @upvoteevent="getUpvoteList(loggedInUser)" :logged-in-user="loggedInUser" :upvoted="userUpvoteList.indexOf(post.id) !== -1" :key="post.id" :post="post"/>
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
      userUpvoteList: []
    }
  },
  props: ["loggedInUser"],
  mounted() {
    fetch(endpoints.api.posts.all)
        .then(value => value.json())
        .then(json => this.posts = json);
    this.getUpvoteList(this.loggedInUser);
  },
  watch: {
    loggedInUser(newUser) {
      this.getUpvoteList(newUser);
    }
  },
  methods: {
    getUpvoteList(user) {
      if(user === undefined) return;
      fetch(endpoints.api.users.upvotes(user.username))
          .then(response => response.json())
          .then(json => this.userUpvoteList = json);
    }
  }
}
</script>

<style scoped lang="scss">
.post-list-container {
  margin: 6vmax auto;
  width: fit-content;

  @media screen and (max-width: 770px) {
    margin-top: 11vh;
  }
}

</style>
