<template>
  <div class="pick-item" ref="pickItem">
    <div class="pick-modal-title">
      <span class="text-title">Chọn ảnh vào album</span>

      <div class="pick-modal-tilte-right">
        <TheSearch style="margin-right: 1rem" />
        <a-upload
          name="file2"
          :multiple="false"
          :max-count="1"
          :before-upload="beforeUpload"
          @change="handleChange"
          @remove="handleRemove"
          style="display: block"
          :height="150"
          :show-upload-list="false"
        >
        </a-upload>
        <UploadModal :albumId="route.query.id" />
        <a-button class="pick-modal-btn" @click="router.back()"
          >Hoàn tất</a-button
        >
      </div>
    </div>
    <ImageListModal
      @closeModal="handleUploadImage"
      @setImage="setImage"
      style="margin-top: 1rem; padding: 2rem"
    ></ImageListModal>
    <a-back-top />
  </div>
</template>

<script>
import { computed, onMounted, onBeforeUnmount, ref } from "vue";
import { message } from "ant-design-vue";
import UploadModal from "@/components/Modal/UploadModal.vue";
import ImageListModal from "@/components/Modal/ImageListModal.vue";
import { uploadImageToAlbumFromHome } from "@/apis/albums";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import TheSearch from "@/components/Search/TheSearch.vue";
export default {
  components: {
    ImageListModal,
    TheSearch,
    UploadModal,
  },
  setup() {
    const imageId = ref();
    const loading = ref(false);
    const store = useStore();
    const router = useRouter();
    const route = useRoute();
    const pickItem = ref(null);
    const token = computed(() => store.state.user.userLogin.token);
    const setImage = (image) => {
      imageId.value = image.id;
    };
    const handleUploadImage = async () => {
      loading.value = true;
      await uploadImageToAlbumFromHome(
        route.query.id,
        imageId.value,
        token.value
      )
        .then((res) => {
          console.log(res);
          loading.value = false;
          message.success("Thêm ảnh thành công!");
          router.back();
        })
        .catch((err) => {
          console.log(err);
          message.error("Thêm ảnh thất bại!");
          loading.value = false;
          router.back();
        });
    };
    onMounted(() => {
      pickItem.value.style.animation = "up 0.5s ease-in-out";
    });
    onBeforeUnmount(() => {
      pickItem.value.style.animation = "down 0.5s ease-in-out";
    });
    return {
      imageId,
      handleUploadImage,
      setImage,
      route,
      router,
      pickItem,
    };
  },
};
</script>

<style lang="scss">
@keyframes up {
  0% {
    transform: translateY(100%);
  }
  100% {
    transform: translateY(0);
  }
}
@keyframes down {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
}
.pick-item {
  padding: 2rem 4rem 0;
  //   animation: up 0.5s ease-in-out;

  transition: all 0.5s;
}
.pick-modal-title {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  margin-top: 1.5rem;
}
.pick-modal-tilte-right {
  width: 55%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.pick-modal-btn {
  font-size: 1rem !important;
  background-color: rgb(243, 244, 246) !important;
  border-radius: 18px !important;

  &:hover {
    background-color: #171a1f !important;
    color: #ffffff !important;
    border: none !important;
  }
}
</style>