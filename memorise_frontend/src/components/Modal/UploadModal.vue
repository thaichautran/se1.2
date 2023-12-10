<template>
  <section id="modal-upload">
    <span style="font-size: 16px" class="interactive" @click="showModal">
      <UploadOutlined />
      Tải lên
    </span>
    <a-modal
      v-model:open="open"
      title="Thêm kỷ niệm"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      width="700px"
    >
      <template #footer>
        <a-button key="back" style="border-radius: 18px" @click="handleCancel"
          >Hủy</a-button
        >
        <a-button
          key="submit"
          style="border-radius: 18px"
          :loading="loading"
          @click="handleOk"
          >Tải lên</a-button
        >
      </template>
      <a-row class="modal-upload-input" :gutter="32">
        <a-col :span="14" class="modal-upload-input-left">
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
                    style="height: 128px"
                  />
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
        <a-col :span="10" class="modal-upload-input-right">
          <a-upload-dragger
            v-model:fileList="fileList"
            name="file"
            :multiple="true"
            @change="handleChange"
            @drop="handleDrop"
          >
            <p class="ant-upload-drag-icon">
              <CloudUploadOutlined />
            </p>
            <p class="ant-upload-text">Keo thả ảnh hoặc video vào đây</p>
            <p class="ant-upload-hint">
              Định dạng được hỗ trợ: PNG, JPG và MP4
            </p>
            <p class="ant-upload-text">Hoặc</p>
            <p class="ant-upload-hint">Lựa chọn tệp từ máy tính</p>
          </a-upload-dragger>
        </a-col>
      </a-row>
    </a-modal>
  </section>
</template>

<script>
import { ref, reactive } from "vue";
import { UploadOutlined, CloudUploadOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
export default {
  components: {
    UploadOutlined,
    CloudUploadOutlined,
  },
  setup() {
    const open = ref(false);
    const confirmLoading = ref(false);

    const formState = reactive({
      name: "",
      location: "",
      desc: "",
    });

    //show modal
    const showModal = () => {
      open.value = true;
    };
    const handleOk = () => {
      confirmLoading.value = true;
    };
    const handleCancel = () => {
      open.value = false;
    };
    //upload
    const fileList = ref([]);
    const handleChange = (info) => {
      const status = info.file.status;
      if (status !== "uploading") {
        console.log(info.file, info.fileList);
      }
      if (status === "done") {
        message.success(`${info.file.name} file uploaded successfully.`);
      } else if (status === "error") {
        message.error(`${info.file.name} file upload failed.`);
      }
    };
    function handleDrop(e) {
      console.log(e);
    }
    return {
      open,
      confirmLoading,
      showModal,
      handleOk,
      handleCancel,
      formState,
      fileList,
      handleChange,
      handleDrop,
    };
  },
};
</script>
<style lang="scss" scoped>
.interactive {
  cursor: pointer;
}
</style>