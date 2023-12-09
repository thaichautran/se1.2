<template>
  <section id="check-otp">
    <div class="check-otp-top">
      <h2 class="text-title">Nhập mã xác thực</h2>
      <p style="font-size: 16px">
        Chúng tôi đã gửi mã xác thực vào địa chỉ email
        <span style="font-weight: bold">{{ email }}</span>
      </p>
      <p style="font-size: 14px">
        Không nhận được mã sau ({{ countdown }})?
        <span class="text-link" @click="handeSendOTP">Gửi lại</span>
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
import { onMounted, ref, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { checkOTP, sendOTP } from "@/apis/user";
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
    const countdown = ref(60);
    let intervalId = null;
    const formState = reactive({
      email: route.query.data,
    });
    onMounted(() => {
      startCountdown();
    });
    const startCountdown = () => {
      if (intervalId !== null) {
        clearInterval(intervalId);
      }
      intervalId = setInterval(() => {
        if (countdown.value > 0) {
          countdown.value--;
        } else {
          clearInterval(intervalId);
        }
      }, 1000);
    };

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
          countdown.value = 60;
          startCountdown();
        });
    };
    return {
      countdown,
      email,
      otp,
      loading,
      handleCheckOTP,
      startCountdown,
      handeSendOTP,
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