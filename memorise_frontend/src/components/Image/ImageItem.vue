<template>
  <a-col :span="3" @click.prevent="showModal" style="cursor: pointer">
    <a-modal v-model:open="open" style="width: 1100px">
      <template #footer>
        <div style="text-align: -webkit-right">
          <div class="ant-footer-custom">
            <a-button
              key="favourite"
              style="border-radius: 18px"
              @click="handleFavourite"
              ><StarOutlined /> Yêu thích</a-button
            >
            <a-button
              key="share"
              style="border-radius: 18px"
              @click="handleShare"
              ><ShareAltOutlined /> Chia sẻ</a-button
            >
          </div>
        </div>
      </template>
      <ImageModal :image="image"></ImageModal>
    </a-modal>
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
import ImageModal from "../Modal/ImageModal.vue";
import { StarOutlined, ShareAltOutlined } from "@ant-design/icons-vue";
export default {
  props: {
    image: {
      type: Object,
    },
  },
  components: {
    ImageModal,
    StarOutlined,
    ShareAltOutlined,
  },
  setup() {
    const open = ref(false);
    const showModal = () => {
      open.value = true;
    };

    onMounted(() => {
      if (!open.value) {
        document.body.style.overflow = "unset";
      }
    });

    return {
      showModal,
      open,
    };
  },
};
</script>

<style lang="scss" scoped>
.ant-modal-content {
  .ant-footer-custom {
    display: flex;
    justify-content: space-between;
    width: 50%;
  }
}
</style>
