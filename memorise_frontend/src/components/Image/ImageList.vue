<template>
  <a-row :gutter="[16, 16]">
    <ImageItem
      v-for="item in imageList"
      :key="item.id"
      :image="item"
      @getNewList="getNewList"
    />
  </a-row>
</template>

<script>
import { watch } from "vue";
import ImageItem from "./ImageItem.vue";

export default {
  props: {
    imageList: {
      type: Array,
    },
  },

  components: {
    ImageItem,
  },
  setup(props, { emit }) {
    watch(
      () => props.imageList,
      (newVal) => {
        console.log(newVal);
      }
    );
    const getNewList = () => {
      emit("getNewList");
    };
    return { getNewList };
  },
};
</script>

<style>
</style>