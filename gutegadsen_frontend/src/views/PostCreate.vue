<template>
  <div class="create-post-container">
    <b-card
        title="Post erstellen">
      <b-form>
        <b-form-group
            id="login-username-group"
            label="Titel"
            label-for="register-username">
          <b-form-input id="login-username"
                        trim
                        required></b-form-input>
        </b-form-group>
      </b-form>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "PostCreate"
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
</style>