<template>
  <a-row :gutter="[16, 16]">
    <AlbumItem v-for="item in albumList" :key="item.id" :album="item" />
  </a-row>
</template>

<script>
import AlbumItem from "./AlbumItem.vue";
export default {
  components: {
    AlbumItem,
  },
  props: {
    albumList: {
      type: Array,
    },
  },
  setup() {
    return {};
  },
};
</script>

<style>
</style>