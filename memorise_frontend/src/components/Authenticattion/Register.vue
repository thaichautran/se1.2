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
    >
      <a-form-item label="Họ và tên" name="name">
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
      <a-form-item label="Email" name="email">
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
        <a-button
          style="width: 80%; height: 48px; padding: 0"
          type="submit"
          @click="
            () => {
              handleRegister();
            }
          "
          class="btn-dark"
          :loading="loading"
        >
          Tạo tài khoản
        </a-button>
        <p
          v-if="registerFaild"
          style="text-align: center; color: red; width: 80%; margin-top: 0.5rem"
        >
          {{ registerFaild }}
        </p>
      </a-form-item>
    </a-form>
  </section>
</template>

<script>
import { reactive, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { register } from "@/apis/user";
export default {
  components: {},
  setup() {
    const store = useStore();
    const router = useRouter();
    const formRef = ref();
    const formState = reactive({
      name: "",
      email: "",
      password: "",
      checkpass: "",
    });
    const loading = ref(false);
    const registerFaild = ref("");
    const disabled = computed(() => {
      return !(formState.name && formState.password);
    });
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
    const validateName = async (_rule, value) => {
      if (value === "") {
        return Promise.reject("Vui lòng nhập họ và tên!");
      } else {
        return Promise.resolve();
      }
    };
    const validateEmail = async (_rule, value) => {
      if (value === "") {
        return Promise.reject("Vui lòng nhập email!");
      }
      {
        return Promise.resolve();
      }
    };
    const rules = {
      name: [{ required: true, validator: validateName, trigger: "change" }],
      email: [{ required: true, validator: validateEmail, trigger: "change" }],
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
    const handleRegister = async () => {
      const rqBody = {
        username: formState.email,
        password: formState.password,
        name: formState.name,
      };
      loading.value = true;
      await register(rqBody)
        .then((res) => {
          store.dispatch("user/registerAction", { data: res, router });
        })
        .catch((err) => {
          console.log(err);
          registerFaild.value = "Tài khoản đã tồn tại!";
        })
        .finally(() => {
          loading.value = false;
        });
    };
    return {
      disabled,
      formState,
      rules,
      handleRegister,
      loading,
      registerFaild,
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