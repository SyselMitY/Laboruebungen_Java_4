<template>
  <div id="app">
    <header>
      <router-link to="/">Home</router-link>
      <router-link to="/about">About</router-link>
      <router-link to="/posts">Posts</router-link>
      <img class="split profile-picture" v-if="userToken !== undefined"/>
    </header>
    <router-view/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loggedInUser: undefined
    }
  },
  computed: {
    userToken() {
      return localStorage.getItem("username")
    }
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

header {
  width: 100vw;
  height: 3.5vmax;
  background-color: #101010;
  opacity: .7;
  position: fixed;
  top: -1.5vmax;
  padding: .5rem 4rem;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 3rem;
  z-index: 10;
  transition: all 300ms cubic-bezier(.17, .84, .44, 1);
  min-height: 2rem;

  &:hover {
    top: 0;
    opacity: 1;
  }

  &.split {
    margin-left: auto;
  }

  & a {
    color: white;
  }

  @media screen and (max-width: 1200px) {
    top: 0;
    height: 6.5vh;
    opacity: 1;

    &:hover {
      height: 6.5vh;
    }

  }
}

</style>
