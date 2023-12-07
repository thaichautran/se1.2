<template>
  <section id="login">
    <div class="login-top">
      <h2 class="text-title">Đăng nhập</h2>
      <div>
        <span class="text-sub-title">Chưa có tài khoản?</span> &nbsp;
        <router-link to="/authentication/register" class="text-link"
          >Tạo tài khoản mới</router-link
        >
      </div>
    </div>
    <a-form :model="formState" name="normal_login" class="login-form">
      <a-form-item
        name="email"
        style="margin-top: 2rem"
        :rules="[
          {
            required: true,
            message: 'Vui lòng nhập tài khoản!',
          },
        ]"
      >
        <a-input
          placeholder="Email"
          style="
            width: 85%;
            padding: 0.75rem 1rem;
            border-radius: 13px;
            font-size: 1rem;
          "
          v-model:value="formState.email"
        >
          <template #prefix>
            <MailOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item
        name="password"
        :rules="[{ required: true, message: 'Vui lòng nhập mật khẩu!' }]"
      >
        <a-input-password
          style="
            width: 85%;
            padding: 0.75rem 1rem;
            border-radius: 13px;
            font-size: 1rem;
          "
          placeholder="Mật khẩu"
          v-model:value="formState.password"
        >
          <template #prefix>
            <LockOutlined class="site-form-item-icon" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item :style="{ marginBottom: 0 }">
        <a-form-item name="remember" no-style>
          <a-checkbox v-model:value="formState.remember">Ghi nhớ</a-checkbox>
        </a-form-item>
        <router-link
          style="margin-left: 8rem"
          class="login-form-forgot text-sub-2-title"
          to="/authentication/forgot_password"
          >Quên mật khẩu?</router-link
        >
      </a-form-item>

      <a-form-item>
        <a-button
          style="width: 85%; height: 48px"
          type="submit"
          class="btn-dark"
          :loading="loading"
          @click="
            () => {
              handleLogin();
            }
          "
        >
          Đăng nhập
        </a-button>
        <p
          v-if="loginFaild"
          style="text-align: center; color: red; width: 85%; margin-top: 0.5rem"
        >
          {{ loginFaild }}
        </p>
      </a-form-item>
    </a-form>

    <div class="login-other">
      <p class="login-other-text text-sub-title">
        Đăng nhập bằng tài khoản khác
      </p>
      <div>
        <GoogleLogin :callback="googleLogin" :popup-type="TOKEN">
          <button
            type="submit"
            class="login-form-button btn-white"
            style="margin-bottom: 0.5rem; min-width: 124%"
          >
            <span style="margin-right: 0.25rem"
              ><img
                src="../../assets/image/google 1.png"
                style="width: 20px; height: 20px"
                alt=""
                srcset=""
            /></span>
            Đăng nhập bằng <span style="font-weight: bold"> Google</span>
          </button>
        </GoogleLogin>
      </div>
    </div>
  </section>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { MailOutlined, LockOutlined } from "@ant-design/icons-vue";
import { decodeCredential } from "vue3-google-login";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { login } from "@/apis/user";
export default {
  components: {
    MailOutlined,
    LockOutlined,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const formState = reactive({
      email: "",
      password: "",
      remember: true,
    });
    const loading = ref(false);
    const loginFaild = ref("");
    const handleLogin = async () => {
      const rqBody = {
        username: formState.email,
        password: formState.password,
      };
      loading.value = true;
      await login(rqBody)
        .then((res) => {
          store.dispatch("user/loginAction", { data: res, router });
          store.dispatch("user/loadFromLocalStorageAction");
        })
        .catch((err) => {
          console.log("Login failed", err);
          loginFaild.value = "Tài khoản hoặc mật khẩu không đúng!";
        })
        .finally(() => {
          loading.value = false;
        });
    };

    const disabled = computed(() => {
      return !(formState.username && formState.password);
    });
    const googleLogin = (res) => {
      console.log("login in");
      const userData = decodeCredential(res.credential);
      console.log("Handle the userData", userData);
    };

    return {
      handleLogin,

      disabled,
      formState,
      googleLogin,
      loading,
      loginFaild,
    };
  },
};
</script>

<style lang="scss" scoped>
.login-other-text {
  margin-left: 2rem;
  text-align: left;
}

.ant-form {
  .ant-form-item {
    justify-content: center;
    &:nth-child(3) {
      margin-bottom: 0;
    }
  }
}
</style>