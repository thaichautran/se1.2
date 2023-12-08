<template>
  <section id="auth">
    <div
      class="logo"
      :style="{
        width: '200px',
        position: 'absolute',
        left: '1.5rem',
        top: '-3rem',
      }"
    >
      <a href="/">
        <img
          :style="{ width: '100%' }"
          src="../assets/image/memorise_logo_with_text.png"
          alt="logo"
        />
      </a>
    </div>
    <a-row :gutter="16" class="auth-row">
      <a-col :span="16" :style="{ height: '100%' }">
        <div class="auth-carousel">
          <a-carousel autoplay>
            <div>
              <img src="../assets/image/cheering.png" alt="slide-1" />
              <a-row class="auth-carousel-desc">
                <a-col :span="12">
                  <h2>Gợi nhắc kỷ niệm</h2>
                </a-col>
                <a-col :span="12">
                  <p>
                    Để những khoảnh khắc đáng nhớ không bao giờ rơi vào quên
                    lãng
                  </p>
                </a-col>
              </a-row>
            </div>
            <div>
              <img src="../assets/image/sharing.png" alt="slide-2" />
              <a-row
                class="auth-carousel-desc"
                :style="{ marginBottom: '4rem' }"
              >
                <a-col :span="12">
                  <h2>Dễ dàng chia sẻ</h2>
                </a-col>
                <a-col :span="12">
                  <p>
                    Chia sẻ những khoảnh khắc đáng nhớ trong cuộc sống với bạn
                    bè và gia đình
                  </p>
                </a-col>
              </a-row>
            </div>
            <div>
              <img
                src="../assets/image/multiple_devices.png"
                :style="{ width: '100%    ' }"
                alt="slide-3"
              />
              <a-row class="auth-carousel-desc">
                <a-col :span="12">
                  <h2>Trình chiếu đỉnh cao</h2>
                </a-col>
                <a-col :span="12">
                  <p>
                    Trình chiếu ảnh của bạn kèm những lời nhắn nhủ thân thương
                    nhất
                  </p>
                </a-col>
              </a-row>
            </div>
          </a-carousel>
        </div>
      </a-col>
      <a-col :span="8" :style="{ height: '100%' }">
        <div v-if="$route.params.name == 'login'">
          <Login></Login>
        </div>
        <div v-else-if="$route.params.name == 'register'">
          <Register></Register>
        </div>
        <div v-else-if="$route.params.name == 'forgot_password'">
          <ForgotPass></ForgotPass>
        </div>
        <div v-else-if="$route.params.name == 'check_otp'">
          <OTPInput></OTPInput>
        </div>
        <div v-else-if="$route.params.name == 'reset_password'">
          <ResetPassword></ResetPassword>
        </div>
      </a-col>
    </a-row>
  </section>
</template>

<script>
import Login from "@/components/Authenticattion/Login.vue";
import Register from "@/components/Authenticattion/Register.vue";
import ForgotPass from "@/components/Authenticattion/ForgotPass.vue";
import OTPInput from "@/components/Authenticattion/OTPInput.vue";
import ResetPassword from "@/components/Authenticattion/ResetPassword.vue";
export default {
  components: { Login, Register, ForgotPass, OTPInput, ResetPassword },
  setup() {},
};
</script>

<style lang="scss" scoped>
#auth {
  position: relative;
  height: 100%;
  width: 100%;
  .auth-row {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80%;
    height: 60%;
    .auth-carousel {
      height: 100%;
      width: 100%;

      :deep(.ant-carousel) {
        height: 100% !important;
      }
      :deep(.slick-list) {
        height: 100% !important;
      }
      :deep(.slick-slide) {
        height: 100% !important;
      }

      :deep(.slick-slide img) {
        object-fit: fill;
        max-width: 50%;
        width: 100%;
        height: 300px;
        margin: 0 auto;
      }
      :deep(.slick-dots) {
        position: absolute;
        bottom: 1rem;
      }
      :deep(.slick-dots li) {
        height: 8px;
        width: 8px;
        border-radius: 50%;
      }
      :deep(.slick-dots button) {
        height: 8px;
        width: 8px;
        border-radius: 50%;
        background-color: #000;
      }

      .auth-carousel-desc {
        align-items: center;
        padding: 5rem 5rem 0;
        h2 {
          text-align: center;
          font-size: 30px;
        }
        p {
          width: 90%;
        }
      }
    }
  }
}
</style>