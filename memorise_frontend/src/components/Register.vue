<template>
  <section id="register">
    <div class="register-top">
      <h2 class="text-title">Tạo tài khoản mới</h2>
    </div>
    <a-form
      :model="formState"
      name="normal_register"
      layout="vertical"
      :rules="rules"
      class="register-form"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="Họ và tên"
        name="name"
        :rules="[
          {
            required: true,
            message: 'Vui lòng nhập họ và tên!',
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
      <a-form-item
        label="Email"
        name="email"
        :rules="[
          {
            required: true,
            message: 'Vui lòng nhập tài khoản!',
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
          v-model:value="formState.email"
        >
        </a-input>
      </a-form-item>
      <a-form-item label="Mật khẩu" name="password" has-feedback>
        <a-input-password
          style="
            width: 80%;
            padding: 0.5rem 1rem;
            border-radius: 13px;
            font-size: 1rem;
          "
          v-model:value="formState.password"
        >
        </a-input-password>
      </a-form-item>
      <a-form-item has-feedback label="Xác nhận mật khẩu" name="checkPass">
        <a-input
          style="
            width: 80%;
            padding: 0.5rem 1rem;
            border-radius: 13px;
            font-size: 1rem;
          "
          v-model:value="formState.checkPass"
          type="password"
          autocomplete="off"
        />
      </a-form-item>

      <a-form-item>
        <button style="width: 80%" type="submit" class="btn-dark">
          Tạo tài khoản
        </button>
      </a-form-item>
    </a-form>
  </section>
</template>

<script>
import { reactive, ref, computed } from "vue";
export default {
  components: {},
  setup() {
    const formRef = ref();
    const formState = reactive({
      name: "",
      email: "",
      password: "",
      checkpass: "",
    });
    const onFinish = (values) => {
      console.log("Success:", values);
    };
    const onFinishFailed = (errorInfo) => {
      console.log("Failed:", errorInfo);
    };
    const disabled = computed(() => {
      return !(formState.name && formState.password);
    });

    const validatePass = async (_rule, value) => {
      if (value === "") {
        return Promise.reject("Vui lòng nhập mật khẩu!");
      } else if (value.length < 6) {
        return Promise.reject("Mật khẩu phải dài hơn 6 ký tự!");
      } else {
        if (formState.checkpass !== "") {
          formRef.value.validateFields("checkPass");
        }
        return Promise.resolve();
      }
    };
    const validatePass2 = async (_rule, value) => {
      if (value === "") {
        return Promise.reject("Vui lòng xác nhận mật khẩu!");
      } else if (value !== formState.password) {
        return Promise.reject("Xác nhận mật khẩu không khớp!");
      } else {
        return Promise.resolve();
      }
    };
    const rules = {
      password: [
        {
          required: true,
          validator: validatePass,
          trigger: "change",
        },
      ],
      checkPass: [
        {
          validator: validatePass2,
          trigger: "change",
          required: true,
        },
      ],
    };
    return {
      onFinish,
      onFinishFailed,
      disabled,
      formState,
      rules,
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