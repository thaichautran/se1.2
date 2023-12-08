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
      @finish="handeSendOTP"
    >
      <a-form-item
        label="Nhập email"
        name="email"
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
          v-model:value="formState.email"
        />
      </a-form-item>

      <a-form-item>
        <a-button
          style="width: 80%; padding: 0.75rem 0; height: 50px"
          class="btn-dark"
          html-type="submit"
          :loading="loading"
        >
          Gửi mã xác nhận
        </a-button>
      </a-form-item>
    </a-form>
  </section>
</template>
  
  <script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { sendOTP } from "@/apis/user";
export default {
  components: {},
  setup() {
    const router = useRouter();
    const formState = reactive({
      email: "",
    });
    const loading = ref(false);
    const handeSendOTP = async () => {
      loading.value = true;
      await sendOTP(formState.email)
        .then(() => {
          router.push({
            path: "/authentication/check_otp",
            query: { data: formState.email },
          });
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          loading.value = false;
        });
    };

    return {
      handeSendOTP,
      formState,
      loading,
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