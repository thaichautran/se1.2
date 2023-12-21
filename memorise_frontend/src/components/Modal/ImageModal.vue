<template>
  <div id="image-modal">
    <a-row :gutter="16">
      <a-col :span="12">
        <a-image
          v-if="
            image.url.includes('.jpg') ||
            image.url.includes('.png') ||
            image.url.includes('.jpeg')
          "
          style="height: 100%; width: 100%; object-fit: cover"
          :src="image?.url"
        />
        <div v-else-if="image.url.includes('.mp4')">
          <video
            :src="image?.url"
            style="aspect-ratio: 1 / 1; width: 100%; object-fit: cover"
            controls
          ></video>
        </div>
      </a-col>
      <a-col :span="12">
        <div class="image-modal-right">
          <div class="image-modal-bar">
            <a-dropdown :trigger="['click']">
              <a class="ant-dropdown-link" @click.prevent>
                <EllipsisOutlined />
              </a>
              <template #overlay>
                <a-menu>
                  <a-menu-item key="0" v-if="route.path != '/trash'">
                    <span class="icon-delete" @click="handleRemoveImage"
                      ><img
                        class="image-modal-icon"
                        src="../../assets/image/Photo_delete.svg"
                        alt=""
                      />
                      Xóa ảnh</span
                    >
                  </a-menu-item>
                  <a-menu-item key="4" v-else>
                    <span @click="handleRemoveImage"
                      ><img
                        class="image-modal-icon"
                        src="../../assets/image/Restore.svg"
                        alt=""
                      />
                      Khôi phục</span
                    >
                  </a-menu-item>
                  <!-- <a-menu-item key="1">
                    <span class="icon-delete"
                      ><img
                        class="image-modal-icon"
                        src="../../assets/image/Restore.svg"
                        alt=""
                      />
                      Xóa kỷ niệm</span
                    >
                  </a-menu-item> -->
                  <a-menu-item key="2">
                    <span
                      ><img
                        class="image-modal-icon"
                        src="../../assets/image/Photo album.svg"
                        alt=""
                      />
                      Loại bỏ ảnh khỏi album</span
                    ></a-menu-item
                  >
                  <a-menu-item key="3">
                    <img
                      class="image-modal-icon"
                      src="../../assets/image/Data download.svg"
                      alt=""
                    />
                    <span @click="handleDownload"> Tải xuống</span></a-menu-item
                  >
                </a-menu>
              </template>
            </a-dropdown>
          </div>
          <div class="image-modal-update">
            <div
              style="
                display: flex;
                justify-content: space-between;
                margin-top: 2rem;
              "
            >
              <a-button style="border-radius: 18px; font-size: 16px">
                <img
                  style="margin-right: 0.25rem; margin-bottom: 0.25rem"
                  src="../../assets/image/userEdit.svg"
                  class="image-modal-icon"
                  alt=""
                />
                Chỉnh sửa kỷ niệm
              </a-button>

              <a-button style="border-radius: 18px; font-size: 16px">
                <img
                  style="margin-right: 0.25rem; margin-bottom: 0.25rem"
                  src="../../assets/image/Photo album.svg"
                  class="image-modal-icon"
                  alt=""
                />
                Thêm vào album
              </a-button>
            </div>
          </div>
          <div class="image-modal-content">
            <h1 style="font-weight: bold">{{ image.name }}</h1>
            <p
              v-if="image.location != ''"
              style="font-size: 18px; font-weight: bold; color: #565e6c"
            >
              <img src="../../assets/image/destination.svg" alt="" />
              {{ image.location }}
            </p>
            <p
              v-else
              style="font-size: 18px; font-weight: bold; color: #565e6c"
            >
              <img src="../../assets/image/destination.svg" alt="" />
              Vị trí
            </p>
            <p style="color: #565e6c; font-weight: 700">
              {{
                dayjs(image.createDate)
                  .locale("vi")
                  .format("Ngày D MMMM [năm] YYYY, [lúc] HH [giờ] mm [phút]")
              }}
            </p>
            <p>{{ image.description }}</p>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { EllipsisOutlined } from "@ant-design/icons-vue";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import { downloadImage, removeImageToTrash } from "@/apis/images";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
export default {
  components: {
    EllipsisOutlined,
  },
  props: {
    image: {
      type: Object,
    },
  },
  setup(props, { emit }) {
    const store = useStore();
    const route = useRoute();
    const token = computed(() => store.state.user.userLogin.token);
    const isRemove = ref(props.image.remove);
    const handleRemoveImage = async () => {
      if (isRemove.value) {
        await removeImageToTrash(props.image.id, false, token.value)
          .then((res) => {
            isRemove.value = res.data.remove;
            emit("closeModal");
            emit("getNewList");
            store.dispatch("image/getTrashImagesAction", { data: res.data });
          })
          .catch((err) => console.log(err));
      } else {
        await removeImageToTrash(props.image.id, true, token.value)
          .then((res) => {
            isRemove.value = res.data.remove;
            emit("closeModal");
            emit("getNewList");
            store.dispatch("image/getTrashImagesAction", { data: res.data });
          })
          .catch((err) => console.log(err));
      }
    };
    // function convertToHttps(httpLink) {

    //   if (httpLink.startsWith("https://")) {
    //     return httpLink;
    //   }

    //   return httpLink.replace(/^http:/, "https:");
    // }
    // async function downloadImages(imageSrc, nameOfDownload) {
    //   const response = await fetch(convertToHttps(imageSrc));

    //   const blobImage = await response.blob();

    //   const href = URL.createObjectURL(blobImage);

    //   const anchorElement = document.createElement("a");
    //   anchorElement.href = href;
    //   anchorElement.download = nameOfDownload;

    //   document.body.appendChild(anchorElement);
    //   anchorElement.click();

    //   document.body.removeChild(anchorElement);
    //   window.URL.revokeObjectURL(href);
    // }

    const handleDownload = async () => {
      const res = await downloadImage(props.image.url, token.value);

      const href = URL.createObjectURL(new Blob([res]));

      const anchorElement = document.createElement("a");
      anchorElement.href = href;
      anchorElement.download = props.image.name;

      document.body.appendChild(anchorElement);
      anchorElement.click();

      document.body.removeChild(anchorElement);
      window.URL.revokeObjectURL(href);
    };

    return { handleRemoveImage, dayjs, route, handleDownload, downloadImage };
  },
};
</script>

<style lang="scss" scoped>
.image-modal-icon {
  width: 20px;
  height: 20px;
}
.icon-delete {
  color: #e05858;
}
.image-modal-content {
  padding: 0.5rem;
}
:deep(.ant-image) {
  height: 100%;
}
</style>