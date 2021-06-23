<template>
  <div class="login-page-container">
    <b-card no-body>
      <b-tabs card>
        <b-tab title="Login">
          <b-form @submit="loginSubmit">
            <b-form-group
                id="login-username-group"
                label="Benutzername"
                label-for="register-username">
              <b-form-input id="login-username"
                            v-model="loginFormData.username"
                            trim
                            required
                            @keypress.enter="loginSubmit"></b-form-input>
            </b-form-group>

            <b-form-group
                id="login-password-group"
                label="Passwort"
                label-for="login-password">
              <b-form-input id="login-password"
                            type="password"
                            v-model="loginFormData.password"
                            trim
                            required
                            @keypress.enter="loginSubmit"></b-form-input>
            </b-form-group>
            <b-button type="submit" @click="loginSubmit" variant="primary">Login</b-button>
          </b-form>
        </b-tab>


        <b-tab title="Registrieren">
          <b-form @submit="registerSubmit">
            <b-form-group
                id="register-username-group"
                description="Gib einen Benutzernamen ein (4-16 Zeichen)"
                label="Benutzername"
                label-for="register-username"
                valid-feedback="Alles passt!"
                :invalid-feedback="usernameInvalidFeedback(registerFormData.username)"
                :state="usernameState(registerFormData.username)">
              <b-form-input id="register-username"
                            v-model="registerFormData.username"
                            :state="usernameState(registerFormData.username)"
                            trim
                            autocomplete="false"
                            required
                            @keypress.enter="registerSubmit"></b-form-input>
            </b-form-group>

            <b-form-group
                id="register-password-group"
                description="Gib ein Passwort ein (mind 8 Zeichen)"
                label="Passwort"
                label-for="register-password"
                valid-feedback="Alles passt!"
                :invalid-feedback="passwordInvalidFeedback(registerFormData.password)"
                :state="passwordState(registerFormData.password)">
              <b-form-input id="register-password"
                            type="password"
                            v-model="registerFormData.password"
                            :state="passwordState(registerFormData.password)"
                            trim
                            required
                            @keypress.enter="registerSubmit"></b-form-input>
            </b-form-group>

            <b-form-group
                id="register-password-repeat-group"
                description="Wiederhole das Passwort"
                label="Passwort wiederholen"
                label-for="register-password-repeat"
                valid-feedback="Alles passt!"
                invalid-feedback="Passwort stimmt nicht überein!"
                :state="passwordRepeatState()">
              <b-form-input id="register-password-repeat"
                            type="password"
                            v-model="registerFormData.passwordRepeat"
                            :state="passwordRepeatState()"
                            trim
                            required
                            @keypress.enter="registerSubmit"></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary" :disabled="loginPending">Registrieren</b-button>
          </b-form>
        </b-tab>
      </b-tabs>
    </b-card>
  </div>
</template>

<script>
import endpoints from "@/config/endpoints.config"

export default {
  name: "Login",
  data() {
    return {
      registerFormData: {
        username: "",
        password: "",
        passwordRepeat: "",
      },
      loginFormData: {},
      loginPending: false
    }
  },
  mounted() {
    if(this.$route.params.showLoginToast)
      this.$bvToast.toast("Bitte melde dich vorher an",{
        title:"Anmelden"
      })
  },
  methods: {
    usernameState(username) {
      if (username === "" || username === undefined) return null;
      return this.usernameInvalidFeedback(username) == null
    },
    usernameInvalidFeedback(username) {
      if (username.length < 4) return "Mindestens 4 Zeichen";
      if (username.length > 16) return "Maximal 16 Zeichen";
      if (username.charAt(0).match(/^[A-Za-z]/) === null) return "Muss mit Buchstaben beginnen";
      if (username.match(/^[A-Za-z][A-Za-z1-9_]{3,15}$/) === null) return "Darf nur Buchstaben, Zahlen und Unterstriche enthalten";
      return null;
    },
    passwordState(password) {
      if (password === "" || password === undefined) return null;
      return this.passwordInvalidFeedback(password) == null;
    },
    passwordInvalidFeedback(password) {
      if (password.length < 8) return "Mindestens 8 Zeichen";
      return null;
    },
    passwordRepeatState() {
      if (this.registerFormData.password === "" || this.registerFormData.password === undefined) return null;
      return this.registerFormData.password === this.registerFormData.passwordRepeat;

    },
    registerSubmit: async function (event) {
      event.preventDefault();
      this.loginPending = true;

      let requestBody = {
        username: this.registerFormData.username,
        password: btoa(this.registerFormData.password)
      }
      let response = await fetch(endpoints.api.users.register, {
        method: "POST",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json"
        }
      });

      let responseBody = await response.json();

      if (response.status === 201) {
        this.showCustomToast("Erfolg", "Der Account wurde erfolgreich erstellt", "success");
        localStorage.setItem("username", responseBody.username);
        this.$emit("login-event")
        await this.$router.push("/");
      } else {
        this.showCustomToast("Fehler", `Ein unbekannter Fehler ist aufgetreten: ${responseBody.message}`, "danger");
        this.loginPending = false;
      }
    },
    showCustomToast(title, message, variant) {
      this.$root.$bvToast.toast(message, {
        title: title,
        variant: variant,
      })
    },
    async loginSubmit(event) {
      event.preventDefault();
      this.loginPending = true;

      let requestBody = {
        username: this.loginFormData.username,
        password: btoa(this.loginFormData.password)
      }
      let response = await fetch(endpoints.api.users.login, {
        method: "POST",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json"
        }
      });

      let responseBody = await response.json();


      switch (response.status) {
        case 200:
          this.showCustomToast("Erfolg", "Du bist jetzt angemeldet", "success");
          localStorage.setItem("username", responseBody.username);
          this.$emit("login-event")
          await this.$router.push(this.$route.query.afterLogin !== undefined ? this.$route.query.afterLogin : "/");
          break;
        case 404:
          this.showCustomToast("Fehler", `Der Benutzer ${responseBody.username} existiert nicht!`, "danger");
          this.loginPending = false;
          break;
        default:
          this.showCustomToast("Fehler", `Ein unbekannter Fehler ist aufgetreten: ${responseBody.message}`, "danger");
          this.loginPending = false;
          break;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.login-page-container {
  margin: 5rem auto;
  width: 50vw;

  @media screen and (max-width: 1200px) {
    width: 90vw;
    margin: 6rem auto;
  }
}
</style>
