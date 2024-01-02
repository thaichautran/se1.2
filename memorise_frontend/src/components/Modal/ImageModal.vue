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
                  <a-menu-item key="1" v-if="route.path == '/trash'">
                    <a-popconfirm
                      title="Are you sure delete this task?"
                      ok-text="Yes"
                      cancel-text="No"
                      @confirm="confirm"
                      @cancel="cancel"
                    >
                      <span class="icon-delete" @click="handleDeleteImage"
                        ><img
                          class="image-modal-icon"
                          src="../../assets/image/Photo_delete.svg"
                          alt=""
                        />
                        Xóa vĩnh viễn</span
                      >
                    </a-popconfirm>
                  </a-menu-item>
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

                  <a-menu-item key="2" v-if="route.path == '/album'">
                    <span @click="handleRemoveImageFromAlbum"
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
                margin-top: 1rem;
              "
            >
              <a-button
                class="image-modal-button"
                style="border-radius: 18px; font-size: 16px"
                @click="
                  () => {
                    isUpdate = true;
                  }
                "
              >
                <img
                  style="margin-right: 0.25rem; margin-bottom: 0.25rem"
                  src="../../assets/image/userEdit.svg"
                  class="image-modal-icon"
                  alt=""
                />
                Chỉnh sửa kỷ niệm
              </a-button>

              <a-button
                style="border-radius: 18px; font-size: 16px"
                @click="showModal"
              >
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
          <div class="image-modal-content" v-if="!isUpdate">
            <h1 style="font-weight: bold">{{ formState.name }}</h1>
            <p
              v-if="image.location != ''"
              style="font-size: 18px; font-weight: bold; color: #565e6c"
            >
              <img src="../../assets/image/destination.svg" alt="" />
              {{ formState.location }}
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
            <p>{{ formState.description }}</p>
          </div>
          <div class="image-modal-content" v-else>
            <a-form
              :model="formState"
              name="basic"
              @finish="onFinish"
              @finishFailed="onFinishFailed"
            >
              <a-form-item name="name">
                <a-input
                  placeholder="Tên ảnh"
                  v-model:value="formState.name"
                  :bordered="false"
                  style="border-bottom: solid 1px #9095a0; border-radius: 0"
                />
              </a-form-item>
              <a-form-item name="location">
                <a-input
                  placeholder="Vị trí"
                  v-model:value="formState.location"
                  style="border-bottom: solid 1px #9095a0; border-radius: 0"
                  :bordered="false"
                >
                  <template #prefix>
                    <img src="../../assets/image/destination.svg" alt="" />
                  </template>
                </a-input>
              </a-form-item>
              <p style="color: #565e6c; font-weight: 700">
                {{
                  dayjs(image.createDate)
                    .locale("vi")
                    .format("Ngày D MMMM [năm] YYYY, [lúc] HH [giờ] mm [phút]")
                }}
              </p>
              <a-form-item name="description">
                <a-textarea
                  style="height: 170px"
                  v-model:value="formState.description"
                  placeholder="Mô tả"
                />
              </a-form-item>
              <a-form-item style="text-align: right">
                <a-button
                  style="border-radius: 18px; margin-right: 0.5rem"
                  @click="
                    () => {
                      isUpdate = false;
                    }
                  "
                  >Hủy</a-button
                >
                <a-button
                  style="border-radius: 18px"
                  html-type="submit"
                  :loading="loading"
                  >Hoàn tất</a-button
                >
              </a-form-item>
            </a-form>
          </div>
        </div>
      </a-col>
    </a-row>
    <a-modal v-model:open="open" style="width: 100%">
      <template #footer> </template>
      <div class="album-modal-title">
        <span>Chọn Album</span>
      </div>
      <AlbumListModal
        @closeModal="handleUploadImageToAlbum"
        @setImage="setImage"
        style="margin-top: 1rem"
      ></AlbumListModal>
    </a-modal>
  </div>
</template>

<script>
import { EllipsisOutlined } from "@ant-design/icons-vue";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import {
  downloadImage,
  removeImageToTrash,
  updateImage,
  deleteImage,
} from "@/apis/images";
import { computed, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import AlbumListModal from "./AlbumListModal.vue";
import {
  uploadImageToAlbumFromHome,
  removeImageFromAlbum,
} from "@/apis/albums";
import { message } from "ant-design-vue";
export default {
  components: {
    EllipsisOutlined,
    AlbumListModal,
  },
  props: {
    image: {
      type: Object,
    },
    albumId: {
      type: Number,
    },
  },
  setup(props, { emit }) {
    const store = useStore();
    const route = useRoute();
    const token = computed(() => store.state.user.userLogin.token);
    const isRemove = ref(props.image.remove);
    const isUpdate = ref(false);
    const loading = ref(false);
    const open = ref(false);
    const albumId = ref();
    const formState = reactive({
      name: props.image.name,
      location: props.image.location,
      description: props.image.description,
    });
    const showModal = () => {
      console.log(props);
      open.value = true;
    };
    const setImage = (image) => {
      albumId.value = image.id;
    };
    const handleRemoveImage = async () => {
      if (isRemove.value) {
        await removeImageToTrash(props.image.id, false, token.value)
          .then((res) => {
            isRemove.value = res.data.remove;
            emit("closeModal");
            emit("getNewList");
            message.success("Ảnh đã được khôi phục!");
            store.dispatch("image/getTrashImagesAction", { data: res.data });
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        await removeImageToTrash(props.image.id, true, token.value)
          .then((res) => {
            isRemove.value = res.data.remove;
            emit("closeModal");
            emit("getNewList");
            message.success("Ảnh đã được xóa vào thùng rác!");
            store.dispatch("image/getTrashImagesAction", { data: res.data });
          })
          .catch((err) => {
            message.error("Xóa ảnh vào thùng rác thất bại!");
            console.log(err);
          });
      }
    };
    const handleRemoveImageFromAlbum = async () => {
      await removeImageFromAlbum(props.albumId, props.image.id, token.value)
        .then((res) => {
          open.value = false;
          message.success("Loại bỏ ảnh khỏi album thành công!");
          emit("getNewList");
          console.log(res);
        })
        .catch((err) => {
          open.value = false;
          message.error("Loại bỏ ảnh khỏi album thất bại!");
          console.log(err);
        });
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
    const onFinish = async () => {
      loading.value = true;
      const requestBody = {
        id: props.image.id,
        name: formState.name,
        location: formState.location,
        description: formState.description,
      };
      await updateImage(requestBody, token.value)
        .then((res) => {
          formState.name = res.data.name;
          formState.description = res.data.description;
          formState.location = res.data.location;
          isUpdate.value = false;
          loading.value = false;
        })
        .catch((err) => {
          console.log(err);
          loading.value = false;
        });
    };
    const handleUploadImageToAlbum = async () => {
      loading.value = true;
      await uploadImageToAlbumFromHome(
        albumId.value,
        props.image.id,
        token.value
      )
        .then((res) => {
          console.log(res);
          open.value = false;
          loading.value = false;
          message.success("Thêm ảnh vào album thành công");
        })
        .catch((err) => {
          console.log(err);
          loading.value = false;
          message.error("Thêm ảnh vào album thất bại");
        });
    };

    const handleDeleteImage = async () => {
      await deleteImage(props.image.id, token.value)
        .then((res) => {
          isRemove.value = res.data.remove;
          emit("closeModal");
          emit("getNewList");
          message.success("Xóa ảnh thành công!");
        })
        .catch((err) => {
          console.log(err);
          message.error("Xóa ảnh thất bại!");
        });
    };
    return {
      handleRemoveImageFromAlbum,
      handleUploadImageToAlbum,
      handleRemoveImage,
      dayjs,
      route,
      handleDownload,
      downloadImage,
      isUpdate,
      formState,
      onFinish,
      loading,
      open,
      showModal,
      setImage,
      handleDeleteImage,
    };
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
  margin-top: 1rem;
  padding: 0.5rem;
}
:deep(.ant-image) {
  height: 100%;
}
.image-modal-button {
}
.album-modal-title {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  margin-top: 1.5rem;
}
</style>