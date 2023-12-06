<template>
  <section id="forgot-pass">
    <div class="forgot-pass-top">
      <h2 class="text-title">Quên mật khẩu</h2>
    </div>
    <a-form
      :model="formState"
      name="normal_forgot-pass"
      layout="vertical"
      class="forgot-pass-form"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="Nhập email"
        name="name"
        :rules="[
          {
            required: true,
            message: 'Vui lòng nhập email!',
          },
        ]"
      >
        <a-input
          style="
            width: 80%;
            padding: 0.5rem 1rem;
            border-radius: 13px;
            font-size: 1rem;
          "
          v-model:value="formState.name"
        />
      </a-form-item>

      <a-form-item>
        <button
          style="width: 80%; padding: 0.75rem 0"
          type="submit"
          class="btn-dark"
        >
          Gửi mã xác nhận
        </button>
      </a-form-item>
    </a-form>
  </section>
</template>
  
  <script>
import { reactive } from "vue";
export default {
  components: {},
  setup() {
    const formState = reactive({
      email: "",
    });

    const onFinish = (values) => {
      console.log("Success:", values);
    };
    const onFinishFailed = (errorInfo) => {
      console.log("Failed:", errorInfo);
    };
    console.log(formState.email);
    return {
      onFinish,
      onFinishFailed,
      formState,
    };
  },
};
</script>
  
  <style lang="scss" scoped>
.ant-form {
  .ant-form-item {
    justify-content: center;
    margin-bottom: 1rem;
  }
}
</style>