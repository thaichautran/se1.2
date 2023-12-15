<template>
  <a-col :span="3">
    <a-image
      v-if="
        image.url.includes('.jpg') ||
        image.url.includes('.png') ||
        image.url.includes('.jpeg')
      "
      style="aspect-ratio: 1 / 1; width: 100%"
      :src="image?.url"
    />
    <video
      v-else-if="image.url.includes('.mp4')"
      :src="image?.url"
      style="height: 100px; width: 100%"
      controls
    ></video>
    <div v-else></div>
  </a-col>
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