<template>
  <div id="album">
    <div class="album-header">
      <h1 class="text-title" style="margin-bottom: 2rem">Tạo album mới</h1>
    </div>
    <div class="album-body">
      <a-row :gutter="16">
        <a-col :span="16">
          <a-form
            :model="formState"
            name="basic"
            @finish="onFinish"
            @finishFailed="onFinishFailed"
          >
            <div class="album-body-top">
              <a-form-item>
                <a-upload-dragger
                  name="file"
                  :multiple="false"
                  :max-count="1"
                  :before-upload="beforeUpload"
                  @drop="handleDrop"
                  @change="handleChange"
                  @remove="handleRemove"
                  style="display: block; width: 150px"
                  :height="150"
                >
                  <img
                    v-if="imageUrl"
                    :src="imageUrl"
                    style="width: 100%; height: 100%; object-fit: fill"
                    alt="preview Image"
                  />
                  <div
                    v-else
                    style="
                      position: absolute;
                      top: 60%;
                      left: 50%;
                      transform: translate(-50%, -50%);
                    "
                  >
                    <span class="ant-upload-drag-icon">
                      <ArrowUpOutlined
                        style="font-size: 1.25rem; color: #d9d9d9"
                      />
                    </span>
                    <p
                      style="
                        font-size: 0.75rem;
                        color: #d9d9d9;
                        white-space: nowrap;
                        margin-top: 2rem;
                      "
                    >
                      Tải lên ảnh bìa
                    </p>
                  </div>
                </a-upload-dragger>
              </a-form-item>
              <a-form-item
                name="name"
                :rules="[
                  { required: true, message: 'Vui lòng nhập tên album!' },
                ]"
              >
                <a-input
                  placeholder="Tên album"
                  v-model:value="formState.name"
                  :bordered="false"
                  style="
                    border-bottom: solid 1px #9095a0;
                    border-radius: 0;
                    width: 100%;
                    height: 50%;
                    margin-left: 1rem;
                  "
                />
              </a-form-item>
            </div>
            <a-form-item
              name="description"
              :rules="[{ required: true, message: 'Vui lòng nhập mô tả!' }]"
            >
              <a-textarea
                style="height: 200px"
                v-model:value="formState.description"
                placeholder="Mô tả"
              />
            </a-form-item>
          </a-form>
        </a-col>

        <a-col :span="8" style="text-align: right; cursor: pointer">
          <div
            class="album-body-right"
            style="position: relative; text-align: center"
          >
            <div
              v-if="!imageId"
              class="album-body-right-center"
              @click="showModal"
              style="
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
              "
            >
              <PlusCircleOutlined
                style="font-size: 5rem; color: #d9d9d9; font-weight: lighter"
              />
              <p
                style="
                  font-size: 0.75rem;
                  color: #d9d9d9;
                  white-space: nowrap;
                  margin-top: 2rem;
                  font-size: 24px;
                "
              >
                Thêm ảnh vào album
              </p>
            </div>
            <div
              v-else
              class="album-body-right-center"
              @click="showModal"
              style="
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
              "
            >
              <img
                :src="imageUrlImage"
                style="width: 100%; height: 100%"
                alt=""
              />
            </div>
          </div>

          <a-button
            style="border-radius: 18px; margin-top: 0.5rem"
            :loading="loading"
            @click="handleCreateAlbum"
            >Tạo album mới</a-button
          >
        </a-col>
      </a-row>
    </div>
    <a-modal v-model:open="open" style="width: 100%">
      <div class="album-modal-title">
        <span>Chọn ảnh</span>
        <!-- <a-upload
          name="file2"
          :multiple="false"
          :max-count="1"
          :before-upload="beforeUploadImage"
          @change="handleChangeImage"
          @remove="handleRemoveImage"
          style="display: block; width: 150px"
          :height="150"
        >
          <a-button style="border-radius: 18px">
            Chọn ảnh từ thiết bị
          </a-button>
        </a-upload> -->
      </div>
      <ImageListModal
        @closeModal="
          () => {
            open = false;
          }
        "
        @setImage="setImage"
        style="margin-top: 1rem"
      ></ImageListModal>
    </a-modal>
  </div>
</template>
  
  <script>
import { computed, ref, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { message } from "ant-design-vue";
import { ArrowUpOutlined, PlusCircleOutlined } from "@ant-design/icons-vue";
import ImageListModal from "@/components/Modal/ImageListModal.vue";
import { createAlbum, uploadImageToAlbumFromHome } from "@/apis/albums";
export default {
  components: { ArrowUpOutlined, PlusCircleOutlined, ImageListModal },
  setup() {
    const store = useStore();
    const router = useRouter();
    const token = computed(() => store.state.user.userLogin.token);
    const imageUrl = ref("");
    const imageUrlImage = ref("");
    const loading = ref(false);
    const open = ref(false);
    const imageId = ref();
    const imageLink = ref();
    const showModal = () => {
      open.value = true;
    };
    const formState = reactive({
      fileUpload: "",
      name: "",
      description: "",
    });
    const fileUploadImaged = ref("");
    //upload
    function getBase64(img, callback) {
      const reader = new FileReader();
      reader.addEventListener("load", () => callback(reader.result));
      reader.readAsDataURL(img);
    }
    const beforeUpload = (file) => {
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        message.error("Chỉ có thể tải lên ảnh!");
        formState.fileUpload = "";
        imageUrl.value = "";
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
          imageUrl.value = "";
          formState.fileUpload = "";
          return false;
        }
        formState.fileUpload = file;
        return false;
      }
      formState.fileUpload = file;
      return false;
    };
    const beforeUploadImage = (file) => {
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        message.error("Chỉ có thể tải lên ảnh!");
        fileUploadImaged.value = "";
        imageUrlImage.value = "";
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
          imageUrlImage.value = "";
          fileUploadImaged.value = "";
          return false;
        }
        fileUploadImaged.value = file;
        return false;
      }
      fileUploadImaged.value = file;
      return false;
    };
    const handleChange = (info) => {
      if (info.fileList.length > 0) {
        if (
          info.file.type == "image/jpeg" ||
          info.file.type == "image/png" ||
          info.file.type == "image/jpg"
        ) {
          getBase64(info.file, (base64Url) => {
            imageUrl.value = base64Url;
            loading.value = false;
          });
        }
      }
    };
    const handleChangeImage = (info) => {
      if (info.fileList.length > 0) {
        if (
          info.file.type == "image/jpeg" ||
          info.file.type == "image/png" ||
          info.file.type == "image/jpg"
        ) {
          getBase64(info.file, (base64Url) => {
            imageUrlImage.value = base64Url;
            loading.value = false;
          });
        }
      }
    };
    const handleRemove = () => {
      formState.fileUpload = "";
      imageUrl.value = "";
    };
    const handleRemoveImage = () => {
      fileUploadImaged.value = "";
      imageUrlImage.value = "";
    };
    function handleDrop(e) {
      console.log(e);
    }

    const setImage = (image) => {
      console.log(image);
      imageId.value = image.id;
      imageUrlImage.value = image.url;
    };

    const handleCreateAlbum = async () => {
      loading.value = true;
      if (formState.fileUpload == "") {
        message.error("Vui lòng chọn ảnh bìa!");
        return;
      }
      // if (formState.name == "") {
      //   message.error("Vui lòng nhập tên album!");
      //   return;
      // }
      // if (formState.description == "") {
      //   message.error("Vui lòng nhập mô tả!");
      //   return;
      // }
      if (!imageId.value) {
        message.error("Vui lòng chọn 1 ảnh!");
        return;
      }

      const formData = new FormData();
      formData.append("file", formState.fileUpload);

      const album = await createAlbum(
        formState.name,
        formState.description,
        formData,
        token.value
      );

      if (album) {
        await uploadImageToAlbumFromHome(
          album.data.id,
          imageId.value,
          token.value
        )
          .then((res) => {
            console.log(res);
            loading.value = false;
            message.success("Tạo album thành công!");
            router.push("/albums");
          })
          .catch((err) => {
            console.log(err);
            loading.value = false;
          });
      }
    };
    return {
      setImage,
      imageId,
      router,
      token,
      imageUrl,
      imageLink,
      imageUrlImage,
      beforeUpload,
      handleChange,
      handleRemove,
      handleDrop,
      formState,
      open,
      showModal,
      handleCreateAlbum,
      loading,
      beforeUploadImage,
      handleChangeImage,
      handleRemoveImage,
      fileUploadImaged,
    };
  },
  created() {},
};
</script>
  
  <style lang="scss" scoped>
.album-header {
  display: flex;
  justify-content: space-between;
}
.ant-upload-drag-icon {
  padding: 20px;
  border: solid 1px #d9d9d9;
  border-radius: 50%;
}
.album-body {
  position: relative;
}
.album-body-top {
  display: flex;
  align-items: end;
  margin-bottom: 1rem;
}
.album-body-right {
  width: 100%;
  height: 95%;
  border: dashed 1px #d9d9d9;
  border-radius: 8px;
}
.album-modal-title {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  margin-top: 1.5rem;
}
</style>