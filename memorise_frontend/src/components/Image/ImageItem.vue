<template>
  <a-col :span="3" @click.prevent="showModal" style="cursor: pointer">
    <a-modal v-model:open="open" title="Title" @ok="handleOk"> </a-modal>
    <img
      v-if="
        image.url.includes('.jpg') ||
        image.url.includes('.png') ||
        image.url.includes('.jpeg')
      "
      style="aspect-ratio: 1 / 1; width: 100%"
      :src="image?.url"
    />
    <div v-else-if="image.url.includes('.mp4')">
      <video
        :src="image?.url"
        style="aspect-ratio: 1 / 1; width: 100%"
        controls
      ></video>
    </div>
    <div v-else></div>
  </a-col>
</template>

<script>
import { onMounted, ref } from "vue";
export default {
  props: {
    image: {
      type: Object,
    },
  },
  setup() {
    const open = ref(false);
    const showModal = () => {
      open.value = true;
    };

    const handleOk = (e) => {
      console.log(e);

      open.value = false;
    };
    onMounted(() => {
      if (!open.value) {
        document.body.style.overflow = "unset";
      }
    });
    return {
      showModal,
      handleOk,
      open,
    };
  },
};
</script>

<style>
</style>
