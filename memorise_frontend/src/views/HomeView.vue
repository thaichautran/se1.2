<template>
  <section id="home">
    <div v-if="token">
      <div class="home-slider">
        <SwipSlider />
      </div>
      <div class="home-gallery">
        <div
          v-for="date in createdDateList"
          style="margin-bottom: 2rem"
          :key="date"
        >
          <p
            style="
              margin-top: 2rem;
              margin-bottom: 0.75rem;
              text-transform: capitalize;
            "
            class="text-title"
          >
            {{ dayjs(date, "DD-MM-YYYY").locale("vi").format("MMMM") }}
          </p>
          <p
            v-if="
              dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') === '2023'
            "
            class="text-upper text-sub-3-title"
          >
            {{
              dayjs(date, "DD-MM-YYYY")
                .locale("vi")
                .format("dddd, [ngày] DD [tháng] M")
            }}
          </p>
          <p v-else class="text-upper text-sub-3-title">
            {{
              dayjs(date, "DD-MM-YYYY")
                .locale("vi")
                .format("dddd, [ngày] DD [tháng] M [năm] YYYY")
            }}
          </p>
          <ImageList :imageList="getImageListByDate(date)" />
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import SwipSlider from "../components/Slider/SwipSlider.vue";
import { useStore } from "vuex";
import { computed, ref, watchEffect } from "vue";
import { getAllImageByUser } from "@/apis/images";
import ImageList from "../components/Image/ImageList.vue";
import { useRouter } from "vue-router";
import dayjs from "dayjs";
import "dayjs/locale/vi";
export default {
  components: {
    SwipSlider,
    ImageList,
  },

  setup() {
    const store = useStore();
    const router = useRouter();
    const token = computed(() => store.state.user.userLogin.token);
    const imageList = ref([]);
    const createdDateList = ref([]);
    const imageListByDate = ref([]);

    const getImageList = async () => {
      await getAllImageByUser(token.value)
        .then((res) => {
          imageList.value = [...res.data];
          store.dispatch("image/getAllImagesAction", { data: imageList.value });
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const formatDate = (date) => {
      return dayjs(date).format("DD-MM-YYYY");
    };
    const getCreatedDateList = () => {
      imageList.value.forEach((image) => {
        createdDateList.value.push(image.createDate);
      });
      createdDateList.value.sort();
      createdDateList.value = createdDateList.value.map((createdDate) => {
        return formatDate(createdDate);
      });

      createdDateList.value.reverse();
      createdDateList.value = [...new Set(createdDateList.value)];
    };

    const getImageListByDate = (date) => {
      return imageList.value.filter((image) => {
        return formatDate(image.createDate) === date;
      });
    };
    watchEffect(() => {
      getCreatedDateList();
    });

    return {
      token,
      imageList,
      router,
      createdDateList,
      formatDate,
      dayjs,
      getImageListByDate,
      getImageList,
      imageListByDate,
    };
  },
  created() {
    this.getImageList();
  },
};
</script>
<style lang="scss" scoped>
.text-upper {
  margin-bottom: 1rem;
  &::first-letter {
    text-transform: uppercase;
  }
}
</style>
