<template>
  <section id="home">
    <div class="home-slider">
      <!-- <swiper
        :navigation="true"
        :modules="modules"
        :slidesPerView="4"
        :loop="true"
        class="mySwiper"
      >
        <swiper-slide>
          <img src="../assets/image/bg1.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg2.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg3.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg4.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg1.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg2.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg3.jpg" alt="" />
        </swiper-slide>
        <swiper-slide>
          <img src="../assets/image/bg4.jpg" alt="" />
        </swiper-slide>
      </swiper> -->
    </div>
    <div class="home-gallery"></div>
  </section>
</template>
<script>
// Import Swiper Vue.js components
// import { Swiper, SwiperSlide } from "swiper/vue";

// // Import Swiper styles
// import "swiper/css";

// import "swiper/css/navigation";

// // import required modules
// import { Navigation } from "swiper/modules";

export default {
  components: {
    // Swiper,
    // SwiperSlide,
  },
  setup() {
    return {
      // modules: [Navigation],
    };
  },
};
</script>
<style lang="scss" scoped>
.swiper {
  width: 100%;
  height: 100%;
}

.swiper-slide {
  text-align: center;
  font-size: 18px;
  background: #fff;

  /* Center slide text vertically */
  display: flex;
  justify-content: center;
  align-items: center;
}

.swiper-slide img {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

#home {
  text-align: center;
  .home-slider {
    .swiper-slide {
      img {
        height: 170px;
        width: 85%;
      }
    }
  }
}
:deep(.swiper-button-prev),
:deep(.swiper-button-next) {
  height: 50px;
  width: 50px;
  border-radius: 50%;
  background-color: #d9d9d9;

  &::after {
    font-size: 1rem;
    color: #000;
  }
}
:deep(.swiper-button-prev) {
  left: var(--swiper-navigation-sides-offset, 0);
}
:deep(.swiper-button-next) {
  right: var(--swiper-navigation-sides-offset, 0);
}
</style>
