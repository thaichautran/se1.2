<template>
  <div id="image-modal">
    <a-row>
      <a-col :span="12">
        <a-image :src="image.url"></a-image>
      </a-col>
      <a-col :span="12"> <h1>hello</h1></a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  props: {
    image: {
      type: Object,
    },
  },
  setup() {
    return {};
  },
};
</script>

<style>
</style>