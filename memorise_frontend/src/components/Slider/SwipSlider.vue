<template>
  <section id="slider">
    <Swiper
      v-if="imageList.length > 0 && imageList"
      :navigation="true"
      :modules="modules"
      :slidesPerView="4"
      :loop="true"
      class="mySwiper"
    >
      <SwiperSlide
        v-for="image in imageList"
        :key="image.id"
        style="position: relative"
      >
        <img
          v-lazy="image.url"
          :src="image.url"
          alt="image"
          style="object-fit: cover"
        />
        <p
          v-if="image.location != ''"
          style="
            position: absolute;
            left: 35px;
            bottom: 0;
            color: #fff;
            font-size: 1rem;
            font-weight: 700;
            text-shadow: 0px 4px 100px rgba(0, 0, 0, 0.25);
          "
        >
          {{ image.location }},
          {{ dayjs(image.createDate).locale("vi").format("YYYY") }}
        </p>
        <p
          v-else
          style="
            position: absolute;
            left: 35px;
            bottom: 0;
            color: #fff;
            font-size: 1rem;
            font-weight: 700;
            text-shadow: 0px 4px 100px rgba(0, 0, 0, 0.25);
          "
        >
          {{ dayjs(image.createDate).locale("vi").format("YYYY") }}
        </p>
      </SwiperSlide>
    </Swiper>
  </section>
</template>

<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";

// Import Swiper styles
import "swiper/css";

import "swiper/css/navigation";

// import required modules
import { Navigation } from "swiper/modules";
import dayjs from "dayjs";
import "dayjs/locale/vi";

export default {
  components: {
    Swiper,
    SwiperSlide,
  },
  props: {
    imageList: {
      type: Array,
    },
  },
  setup() {
    return {
      modules: [Navigation],
      dayjs,
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

.home-slider {
  .swiper-slide {
    img {
      height: 170px;
      width: 85%;
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