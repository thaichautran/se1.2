<template>
  <section id="modal-upload">
    <span style="font-size: 16px" class="interactive" @click="showModal">
      <UploadOutlined />
      Tải lên
    </span>
    <a-modal
      v-model:open="open"
      :confirm-loading="confirmLoading"
      width="1000px"
    >
      <h1>Thêm kỷ niệm</h1>
      <p style="color: #6e7787; font-size: 16px">
        Bạn có thể thêm kỷ niệm sau khi tải ảnh lên
      </p>
      <template #footer>
        <a-button key="back" style="border-radius: 18px" @click="handleCancel"
          >Hủy</a-button
        >

        <a-button
          key="submit"
          style="border-radius: 18px"
          :loading="loading"
          @click="handleOk"
          :disabled="fileUpload == ''"
          >Tải lên</a-button
        >
      </template>
      <a-row class="modal-upload-input" :gutter="32">
        <a-col :span="12" class="modal-upload-input-left">
          <a-form :model="formState">
            <a-row :gutter="16">
              <a-col :span="12">
                <a-form-item>
                  <a-input
                    placeholder="Tên"
                    name="title"
                    v-model:value="formState.name"
                  />
                </a-form-item>
              </a-col>

              <a-col :span="12">
                <a-form-item>
                  <a-input
                    placeholder="Vị trí"
                    name="location"
                    v-model:value="formState.location" /></a-form-item
              ></a-col>
            </a-row>

            <a-row>
              <a-col :span="24" style="height: 100px">
                <a-form-item>
                  <a-textarea
                    v-model:value="formState.desc"
                    placeholder="Mô tả kỷ niệm"
                    :rows="4"
                    style="height: 170px"
                  />
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
        <a-col
          :span="12"
          class="modal-upload-input-right"
          style="margin-bottom: 2rem"
        >
          <a-upload-dragger
            name="file"
            :multiple="false"
            :max-count="1"
            :before-upload="beforeUpload"
            @drop="handleDrop"
            @change="handleChange"
            @remove="handleRemove"
            style="display: block; max-height: 250px"
          >
            <img
              v-if="imageUrl"
              :src="imageUrl"
              style="width: 100%; max-height: 226px; object-fit: cover"
              alt="preview Image"
            />
            <video
              v-else-if="videoUrl"
              controls
              style="width: 100%; max-height: 226px"
            >
              <source :src="videoUrl" type="video/mp4" />
              Your browser does not support the video tag.
            </video>
            <div v-else>
              <p class="ant-upload-drag-icon">
                <CloudUploadOutlined />
              </p>
              <p class="ant-upload-text">Kéo thả ảnh hoặc video vào đây</p>
              <p class="ant-upload-hint">
                Định dạng được hỗ trợ: PNG, JPG và MP4
              </p>
              <p class="ant-upload-text">Hoặc</p>
              <p class="ant-upload-hint">Lựa chọn tệp từ máy tính</p>
            </div>
          </a-upload-dragger>
        </a-col>
      </a-row>
    </a-modal>
  </section>
</template>

<script>
import { ref, reactive, computed } from "vue";
import { UploadOutlined, CloudUploadOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import { uploadImage, uploadVideo } from "@/apis/images";
import { useStore } from "vuex";
export default {
  components: {
    UploadOutlined,
    CloudUploadOutlined,
  },
  setup() {
    const store = useStore();
    const open = ref(false);
    const loading = ref(false);
    const confirmLoading = ref(false);
    const fileUpload = ref("");
    const fileList = ref([]);
    const imageUrl = ref("");
    const videoUrl = ref("");
    const formState = reactive({
      name: "",
      location: "",
      desc: "",
    });
    const token = computed(() => store.state.user.userLogin.token);
    //show modal
    const showModal = () => {
      if (token.value) {
        open.value = true;
      } else {
        message.error("Bạn cần đăng nhập để tải lên");
      }
    };
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
        file.type === "image/jpg" ||
        file.type === "video/mp4";
      if (!isJpgOrPng) {
        message.error("Chỉ có thể tải lên ảnh hoặc video!");
        fileUpload.value = "";
        imageUrl.value = "";
        videoUrl.value = "";
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
          videoUrl.value = "";
          fileUpload.value = "";
          return false;
        }
        fileUpload.value = file;
        return false;
      } else if (file.type === "video/mp4") {
        const isLt100M = file.size / 1024 / 1024 < 100;
        if (!isLt100M) {
          message.error("Video phải nhỏ hơn 100MB!");
          imageUrl.value = "";
          videoUrl.value = "";
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
      if (info.fileList.length > 0) {
        if (
          info.file.type == "image/jpeg" ||
          info.file.type == "image/png" ||
          info.file.type == "image/jpg"
        ) {
          getBase64(info.file, (base64Url) => {
            imageUrl.value = base64Url;
            videoUrl.value = null;
            loading.value = false;
          });
        } else if (info.file.type == "video/mp4") {
          imageUrl.value = null;
          videoUrl.value = URL.createObjectURL(info.file);
          loading.value = false;
        }
      }
    };

    const handleRemove = () => {
      imageUrl.value = "";
    };

    function handleDrop(e) {
      console.log(e);
    }
    const callApiUpload = (bodyFormData, token) => {
      if (
        fileUpload.value.type == "image/jpeg" ||
        fileUpload.value.type == "image/jpg" ||
        fileUpload.value.type == "image/png"
      ) {
        return uploadImage(bodyFormData, token);
      } else if (fileUpload.value.type == "video/mp4") {
        return uploadVideo(bodyFormData, token);
      }
    };
    const handleOk = async () => {
      let bodyFormData = new FormData();
      bodyFormData.append("file", fileUpload.value);
      bodyFormData.append("name", formState.name);
      bodyFormData.append("description", formState.desc);
      bodyFormData.append("location", formState.location);

      confirmLoading.value = true;
      loading.value = true;
      await callApiUpload(bodyFormData, token.value)
        .then((res) => {
          console.log(res);
          confirmLoading.value = false;
          loading.value = false;
          open.value = false;
          message.success("Tải lên thành công");
        })
        .catch((err) => {
          console.log(err);
          confirmLoading.value = false;
          loading.value = false;
          open.value = false;
          message.error("Tải lên thất bại");
        });
    };
    const handleCancel = () => {
      open.value = false;
    };

    return {
      open,
      confirmLoading,
      showModal,
      handleOk,
      handleCancel,
      formState,
      fileList,
      loading,
      handleChange,
      handleDrop,
      fileUpload,
      beforeUpload,
      handleRemove,
      imageUrl,
      getBase64,
      videoUrl,
      callApiUpload,
    };
  },
};
</script>

<style>
.interactive {
  cursor: pointer;
}
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>