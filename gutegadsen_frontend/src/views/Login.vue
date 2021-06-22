<template>
  <div class="login-page-container">
    <b-card no-body>
      <b-tabs card>
        <b-tab title="Login">
          <b-form @submit="loginSubmit">

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
                            trim></b-form-input>
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
                            trim></b-form-input>
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
                            trim></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary" :disabled="loginPending">Registrieren</b-button>
          </b-form>
        </b-tab>
      </b-tabs>
      <b-button @click="() => showCustomToast('hehe hoho','Toaster','primary')">Knopferl</b-button>
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
    async registerSubmit(event) {
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

      if (response.status === 201) {
        this.$root.$bvToast.toast("Der Account wurde erfolgreich erstellt", {
          title: "Erfolg",
          // variant: "success",
          autoHideDelay: 5000
        });
      } else {
        this.showCustomToast("Fehler", "Ein unbekannter Fehler ist aufgetreten", "danger");
        this.loginPending = false;
      }
    },
    showCustomToast(title, message, variant) {
      this.$root.$bvToast.toast(message, {
        title: title,
        variant: variant,
      })
    },
    loginSubmit(event) {
      event.preventDefault();
      this.loginPending = true;
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
