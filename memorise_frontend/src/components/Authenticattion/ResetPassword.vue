<template>
  <section id="forgot-pass">
    <div class="forgot-pass-top">
      <h2 class="text-title">Đổi mật khẩu</h2>
    </div>
    <a-form
      :model="formState"
      name="normal_forgot-pass"
      layout="vertical"
      class="forgot-pass-form"
      :rules="rules"
      @finish="handleResetPassword"
    >
      <a-form-item label="Nhập mật khẩu mới" name="password" has-feedback>
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
      <a-form-item has-feedback label="Xác nhận mật khẩu mới" name="checkPass">
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
        <a-button
          style="width: 80%; padding: 0.75rem 0; height: 50px"
          class="btn-dark"
          html-type="submit"
          :loading="loading"
        >
          Đổi mật khẩu
        </a-button>
      </a-form-item>
    </a-form>
  </section>
</template>
    
    <script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { resetPassword } from "@/apis/user";
export default {
  components: {},
  setup() {
    const router = useRouter();
    const store = useStore();
    const formRef = ref();
    const formState = reactive({
      password: "",
      checkpass: "",
    });
    const loading = ref(false);
    const otp = store.state.user.otp;

    const handleResetPassword = async () => {
      loading.value = true;
      const requestBoday = {
        otp: otp,
        newPassword: formState.password,
      };
      await resetPassword(requestBoday)
        .then(() => {
          router.push("/authentication/login");
        })
        .catch(() => {
          loading.value = false;
        })
        .finally(() => {
          loading.value = false;
        });
    };
    //validate password
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
      handleResetPassword,
      formState,
      loading,
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