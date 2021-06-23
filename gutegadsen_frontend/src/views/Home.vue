<template>
  <div class="home">
    <h2>Gute Gadsen</h2>
    <h1>🐈</h1>
    <p>Hier gibts liabe Katzen zum Anschaun. Wennst selber ane host, kannst ah beitragen</p>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
}
</script>

<style scoped lang="scss">
.home {
  text-align: center;
  padding: 7rem 5rem;

  & h2 {
    font-size: 5rem;
  }

  & h1 {
    font-size: 10rem;
  }

  & p {
    margin: 2rem 4rem;
    font-size: 2rem;
  }
}
</style>