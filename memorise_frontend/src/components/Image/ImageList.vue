<template>
  <a-row :gutter="[16, 16]">
    <ImageItem
      v-for="item in imageList"
      :key="item.id"
      :image="item"
      :albumId="albumId"
      @getNewList="getNewList"
      @setImage="setImage"
      @closeModal="
        () => {
          $emit('closeModal');
        }
      "
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
    albumId: {
      type: Number,
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
    const setImage = (item) => {
      emit("setImage", item);
    };
    return { getNewList, setImage };
  },
};
</script>

<style>
</style>