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
          style="display: block; margin-right: 1rem"
          :height="150"
        >
          <a-button class="pick-modal-btn">
            <UploadOutlined /> Tải lên
          </a-button>
        </a-upload>
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
import ImageListModal from "@/components/Modal/ImageListModal.vue";
import { uploadImageToAlbumFromHome } from "@/apis/albums";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import { UploadOutlined } from "@ant-design/icons-vue";
import TheSearch from "@/components/Search/TheSearch.vue";
export default {
  components: {
    ImageListModal,
    UploadOutlined,
    TheSearch,
  },
  setup() {
    const imageId = ref();
    const fileUpload = ref();
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
    const beforeUpload = (file) => {
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        message.error("Chỉ có thể tải lên ảnh!");
        fileUpload.value = "";

        return false;
      }
      if (
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg"
      ) {
        const isLt10M = file.size / 1024 / 1024 < 10;
        if (!isLt10M) {
          message.error("Ảnh phải nhỏ hơn 10MB!");

          fileUpload.value = "";
          return false;
        }
        fileUpload.value = file;
        return false;
      }
      fileUpload.value = file;
      return false;
    };
    const handleChange = (info) => {
      console.log(info);
      //   if (info.fileList.length > 0) {
      //     if (
      //       info.file.type == "image/jpeg" ||
      //       info.file.type == "image/png" ||
      //       info.file.type == "image/jpg"
      //     ) {
      //     }
      //   }
    };
    const handleRemove = () => {
      fileUpload.value = "";
    };

    onMounted(() => {
      pickItem.value.style.animation = "up 0.5s ease-in-out";
    });
    onBeforeUnmount(() => {
      pickItem.value.style.animation = "down 0.5s ease-in-out";
    });
    return {
      handleChange,
      handleRemove,
      beforeUpload,
      imageId,
      handleUploadImage,
      fileUpload,
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
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.pick-modal-btn {
  font-size: 1rem;
  background-color: rgb(243, 244, 246);
  border-radius: 18px;

  &:hover {
    background-color: #171a1f;
    color: #ffffff !important;
    border: none;
  }
}
</style>