<template>
  <div class="slideshow">
    <div class="container">
      <div id="slide">
        <div v-for="item in imageList" :key="item.id" class="item">
          <video
            v-if="item.url.includes('.mp4')"
            :src="item.url"
            style="
              width: 100%;
              height: 100%;
              object-fit: cover;
              border-radius: 20px;
            "
            controls
            @click="handlePause"
          ></video>
          <img
            v-else
            style="
              width: 100%;
              height: 100%;
              object-fit: cover;
              border-radius: 20px;
            "
            :src="item.url"
            alt="image"
            @click="
              () => {
                handlePause();
              }
            "
          />
          <div class="content">
            <p class="name">{{ item.name }}</p>
            <p class="location">
              <img src="../assets/image/destination-white.svg" alt="" />
              {{ item.location }}
            </p>
            <p class="time">
              {{
                dayjs(item.createDate)
                  .locale("vi")
                  .format("Ngày D MMMM [năm] YYYY, [lúc] HH [giờ] mm [phút]")
              }}
            </p>
            <p class="des">
              {{ item.description }}
            </p>
          </div>
        </div>
      </div>
      <span
        style="
          position: absolute;
          bottom: 7%;
          left: 2%;
          color: #eee;
          font-weight: bold;
        "
        >{{ route.query.name }}</span
      >
      <p
        style="
          position: absolute;
          bottom: 7%;
          right: 2%;
          color: #eee;
          margin-bottom: 0;
        "
      >
        Ảnh <span style="font-weight: bold">{{ current }}</span> /
        {{ imageList.length }}
      </p>
      <button id="close" @click="router.back"><CloseOutlined /></button>
      <div class="buttons">
        <button
          id="prev"
          @click="
            () => {
              handlePrev();
            }
          "
        >
          <LeftOutlined />
        </button>
        <button id="play" v-if="!isPlay" @click="handlePlay">
          <CaretRightOutlined />
        </button>
        <button id="pause" v-else @click="handlePause">
          <PauseOutlined />
        </button>
        <button
          id="next"
          @click="
            () => {
              handleNext();
            }
          "
        >
          <RightOutlined />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { getImagesFromAlbum } from "@/apis/albums";
import { computed } from "vue";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import {
  LeftOutlined,
  RightOutlined,
  CaretRightOutlined,
  PauseOutlined,
  CloseOutlined,
} from "@ant-design/icons-vue";
import router from "@/router";
export default {
  components: {
    LeftOutlined,
    RightOutlined,
    CaretRightOutlined,
    PauseOutlined,
    CloseOutlined,
  },
  setup() {
    const route = useRoute();
    const store = useStore();
    const albumId = ref(route.query.id);
    const imageList = ref([]);
    const token = computed(() => store.state.user.userLogin.token);
    const current = ref(1);
    const isPlay = ref(false);
    const interval = ref(null);
    const getImageList = async () => {
      const res = await getImagesFromAlbum(albumId.value, token.value);
      imageList.value = [...res.data];
    };

    const handleNext = () => {
      clearInterval(interval.value);
      if (isPlay.value) {
        handlePlay();
      }
      imageList.value.push(imageList.value.shift());
      current.value === imageList.value.length
        ? (current.value = 1)
        : (current.value = current.value + 1);
    };
    const handlePrev = () => {
      clearInterval(interval.value);
      if (isPlay.value) {
        handlePlay();
      }
      imageList.value.unshift(imageList.value.pop());
      current.value === 1
        ? (current.value = imageList.value.length)
        : (current.value = current.value - 1);
    };
    const handlePlay = () => {
      isPlay.value = true;
      interval.value = setInterval(() => {
        handleNext();
      }, 5000);
    };
    const handlePause = () => {
      clearInterval(interval.value);
      isPlay.value = false;
    };
    return {
      albumId,
      getImageList,
      imageList,
      dayjs,
      route,
      current,
      handlePlay,
      handleNext,
      handlePrev,
      isPlay,
      handlePause,
      router,
    };
  },
  created() {
    this.getImageList();
  },
};
</script>

<style  scoped>
.container {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  height: 100%;
  background-image: url("../assets/image/Sunset-Gradientimage-after-edit (1).jpg");
  background-size: cover;
  background-position: 50% 50%;
  box-shadow: 0 30px 50px #dbdbdb;
  animation: showSlide 1s linear;
}

#slide {
  width: max-content;
  margin-top: 50px;
}
.item {
  width: 300px;
  height: 380px;
  display: inline-block;
  transition: 0.5s;
  position: absolute;
  z-index: 1;
  top: 50%;
  transform: translate(0, -50%);
  border-radius: 20px;
  box-shadow: 0 30px 50px #505050;
}

.item:nth-child(1) {
  width: 350px;
  height: 450px;
  left: 38%;
}
.item:nth-child(2) {
  left: calc(38% + 380px);
}
.item:nth-child(3) {
  left: calc(38% + 710px);
}
.item:nth-child(n + 4) {
  left: calc(38% + 1040px);
  opacity: 0;
}
.item .content {
  position: absolute;
  top: 50%;
  right: 170%;
  width: 300px;
  text-align: left;
  padding: 0;
  color: #eee;
  transform: translate(0, -50%);
  display: none;
}
.item:nth-child(1) .content {
  display: block;
  z-index: 11111;
}
.item .name {
  font-size: 3rem;
  font-weight: bold;
  margin-bottom: 0;
  opacity: 0;
  animation: showcontent 1s ease-in-out 1 forwards;
}
.item .location {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  font-weight: bold;
  opacity: 0;
  animation: showcontent 1s ease-in-out 0.2s 1 forwards;
}
.item .time {
  font-size: 1rem;
  white-space: nowrap;
  opacity: 0;
  animation: showcontent 1s ease-in-out 0.4s 1 forwards;
}
.item .des {
  width: 130%;
  margin: 20px 0;
  opacity: 0;
  animation: showcontent 1s ease-in-out 0.6s 1 forwards;
}

@keyframes showcontent {
  from {
    opacity: 0;
    transform: translate(0, 100px);
    filter: blur(33px);
  }
  to {
    opacity: 1;
    transform: translate(0, 0);
    filter: blur(0);
  }
}
@keyframes showSlide {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.buttons {
  position: absolute;
  bottom: 30px;
  z-index: 222222;
  text-align: center;
  width: 100%;
}
.buttons button {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid #555;
  transition: 0.5s;
}
#close {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 1px solid #000;
  margin-left: 1rem;
  transition: 0.5s;
  background-color: #fff;
  opacity: 0.5;
}

.buttons button:hover {
  background-color: #bac383;
}
.buttons #play,
.buttons #pause {
  margin: 0 1rem;
}
</style>