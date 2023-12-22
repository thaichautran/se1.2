<template>
  <a-row :gutter="[8, 8]">
    <a-col
      :span="3"
      v-for="item in imageList"
      :key="item.id"
      style="cursor: pointer"
      @click="
        () => {
          $emit('setImage', item);
          $emit('closeModal');
        }
      "
    >
      <img
        style="width: 100%; aspect-ratio: 1/1; object-fit: cover"
        v-lazy="item.url"
        :src="item.url"
      />
    </a-col>
  </a-row>
</template>

<script>
import { ref, watchEffect } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const imageList = ref([...store.state.image.imageList]);

    watchEffect(() => {
      imageList.value = [...store.state.image.imageList];
    });
    console.log(store.state.image.imageList);
    return {
      imageList,
    };
  },
};
</script>

<style>
</style>