<template>
  <div id="app">
    <header class="app-header" @click="headerShown = !headerShown" :class="{shown: headerShown}">
      <router-link class="header-link" to="/">Home</router-link>
      <router-link class="header-link" to="/posts">Posts</router-link>
      <router-link class="header-link" to="/posts/create">Post erstellen</router-link>

      <span class="header-right"/>

      <div class="header-user" v-if="loggedIn">
        <router-link :to="profileLink" class="username header-link">{{ loggedInUser.username }}</router-link>
        <b-avatar class="profile-picture" :src="profilePicture"/>
      </div>
      <a @click="logOut" class="header-link" v-if="loggedIn">Logout</a>

      <router-link to="/login" v-else>Login</router-link>

      <a class="header-link header-button"> {{headerShown?"Menü schließen":"Menü öffnen"}} </a>
    </header>
    <router-view v-on:login-event="fetchUsernameFromLS"/>
  </div>
</template>

<script>
import endpoints from "@/config/endpoints.config"

export default {
  name: "App",
  data() {
    return {
      loggedInUser: undefined,
      profilePicture: undefined,
      headerShown: false
    }
  },
  computed: {
    loggedIn() {
      if (this.loggedInUser === undefined) return false;
      return this.loggedInUser !== "";
    },
    profileLink() {
      return endpoints.api.users.id(this.loggedInUser)
    }
  },
  watch: {
    async loggedInUser(newUser) {
      fetch(endpoints.api.images.id(newUser.profilePictureId))
          .then(response => response.json())
          .then(json => this.profilePicture = json.imageDataString)
    }
  },
  methods: {
    logOut() {
      localStorage.removeItem("username");
      this.fetchUsernameFromLS();
      this.$bvToast.toast("Du wurdest abgemeldet", {
        title: "Erfolg",
        variant: "success",
        autoHideDelay: 5000
      })
    },
    async fetchUsernameFromLS() {
      let item = localStorage.getItem("username");
      if (item == null) {
        this.loggedInUser = undefined;
      } else {
        let userResponse = await fetch(endpoints.api.users.id(item))
        let responseBody = await userResponse.json();

        if (userResponse.status === 404) localStorage.removeItem("username");
        else this.loggedInUser = responseBody;
      }
    }
  },
  mounted() {
    this.fetchUsernameFromLS();
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


.app-header {
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

  & .header-right {
    margin-left: auto;
  }

  & .header-button {
    display: none;
  }

  & a {
    color: white;
  }

  @media screen and (max-width: 1200px) {
    top: 0;
    height: 6.5vh;
    opacity: 1;
    padding: .5rem 2rem;

    &:hover {
      height: 6.5vh;
    }

  }

  @media screen and (max-width: 720px) {
    flex-direction: column;
    height: 90vh;
    padding: 1rem 1rem;
    font-size: 1.5rem;
    top: -80vh;
    gap: 2rem;

    & .header-right {
      margin-top: auto;
    }

    & .header-user {
      flex-direction: column;
    }

    & .profile-picture {
      width: 6rem;
      height: 6rem;
    }

    & .header-button {
      display: inherit;
    }

    &:hover {
      height: 90vh;
      top: -80vh;
    }

    &.shown {
      top: 0;
    }

  }
}

.header-link {
  cursor: pointer;
}

.header-user {
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: space-around;
}

</style>
