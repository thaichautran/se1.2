<template>
  <section id="check-otp">
    <div class="check-otp-top">
      <h2 class="text-title">Nhập mã xác thực</h2>
      <p style="font-size: 16px">
        Chúng tôi đã gửi mã xác thực vào địa chỉ email
        <span style="font-weight: bold">{{ email }}</span>
      </p>
    </div>
    <div class="check-otp-bot">
      <v-otp-input v-model="otp" :loading="loading"></v-otp-input>
      <v-btn
        :disabled="otp.length < 5 || loading"
        class="my-5 btn-dark"
        text="Xác thực tài khoản"
        :style="{ width: '80%', padding: '0.75rem 0', height: '50px' }"
        @click="handleCheckOTP"
      ></v-btn>
    </div>
  </section>
</template>
    
<script>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { checkOTP } from "@/apis/user";
import { useStore } from "vuex";
export default {
  components: {},
  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore();
    const email = route.query.data;
    const otp = ref("");
    const loading = ref(false);

    const handleCheckOTP = async () => {
      loading.value = true;

      await checkOTP(otp.value)
        .then((res) => {
          store.dispatch("user/checkOTPAction", { data: res.data.otp, router });
        })
        .catch((err) => {
          loading.value = false;
          console.log(err);
        })
        .finally(() => {
          loading.value = false;
        });
    };
    return {
      email,
      otp,
      loading,
      handleCheckOTP,
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
.check-otp-bot {
  margin-top: 4rem;
  text-align: center;
}
</style>