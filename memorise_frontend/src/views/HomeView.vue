<template>
  <section id="home">
    <div v-if="token">
      <div class="home-slider" style="margin-bottom: 3rem">
        <SwipSlider />
      </div>
      <div class="home-gallery">
        <div v-for="year in createdYearList" :key="year">
          <p
            v-if="dayjs(today, 'DD-MM-YYYY').format('YYYY') != year"
            style="
              margin-top: 2rem;
              margin-bottom: 2rem;
              text-transform: capitalize;
            "
            class="text-title"
          >
            {{ dayjs(year).locale("vi").format("YYYY") }}
          </p>
          <div v-for="month in createdMonthList" :key="month">
            <p
              class="text-sub-title text-upper"
              style="font-size: 24px"
              v-if="dayjs(month, 'MM-YYYY').locale('vi').format('YYYY') == year"
            >
              {{ dayjs(month, "MM-YYYY").locale("vi").format("MMMM") }}
            </p>
            <div v-for="date in createdDateList" :key="date">
              <p
                class="text-sub-3-title text-upper"
                style="font-size: 18px; margin-top: 3rem"
                v-if="
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('MM-YYYY') ===
                    month &&
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') ===
                    year &&
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('DD-MM-YYYY') ==
                    today
                "
              >
                Hôm nay
                <ImageList
                  style="margin-top: 2rem"
                  :imageList="getImageListByDate(date)"
                />
              </p>

              <p
                class="text-sub-3-title text-upper"
                style="font-size: 18px; margin-top: 3rem"
                v-if="
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('MM-YYYY') ===
                    month &&
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') ===
                    year &&
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('DD-MM-YYYY') ==
                    yesterday
                "
              >
                Hôm qua
                <ImageList
                  style="margin-top: 2rem"
                  :imageList="getImageListByDate(date)"
                />
              </p>

              <p
                class="text-sub-3-title text-upper"
                style="font-size: 18px; margin-top: 3rem"
                v-else-if="
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('MM-YYYY') ===
                    month &&
                  dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') ===
                    year &&
                  dayjs(date, 'DD-MM-YYYY')
                    .locale('vi')
                    .format('DD-MM-YYYY') !== today
                "
              >
                {{
                  dayjs(date, "DD-MM-YYYY")
                    .locale("vi")
                    .format("dddd, [ngày] DD")
                }}
                <ImageList
                  style="margin-top: 2rem"
                  :imageList="getImageListByDate(date)"
                />
              </p>
            </div>
          </div>
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
    const createdMonthList = ref([]);
    const createdYearList = ref([]);
    const imageListByDate = ref([]);
    const today = ref(new Date());
    const yesterday = ref();

    const getToday = () => {
      today.value = dayjs().format("DD-MM-YYYY");
    };
    const getYesterday = () => {
      const today = new Date();
      const preDay = new Date(today);
      preDay.setDate(today.getDate() - 1);

      yesterday.value = dayjs(preDay).format("DD-MM-YYYY");
      console.log(yesterday.value);
    };
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

    const getMonth = () => {
      createdDateList.value.forEach((date) => {
        createdMonthList.value.push(
          dayjs(date, "DD-MM-YYYY").format("MM-YYYY")
        );
      });
      createdMonthList.value = [...new Set(createdMonthList.value)];
    };
    const getYear = () => {
      createdDateList.value.forEach((date) => {
        createdYearList.value.push(dayjs(date, "DD-MM-YYYY").format("YYYY"));
      });
      createdYearList.value = [...new Set(createdYearList.value)];
    };

    const getImageListByDate = (date) => {
      return imageList.value.filter((image) => {
        return formatDate(image.createDate) === date;
      });
    };

    watchEffect(() => {
      getCreatedDateList();
      getMonth();
      getYear();
      getToday();
      getYesterday();
    });

    return {
      yesterday,
      today,
      token,
      imageList,
      router,
      createdDateList,
      formatDate,
      dayjs,
      getImageListByDate,
      getImageList,
      getMonth,
      getYear,
      imageListByDate,
      createdMonthList,
      createdYearList,
    };
  },
  created() {
    this.getImageList();
  },
};
</script>
<style lang="scss" scoped>
.text-upper {
  &::first-letter {
    text-transform: uppercase;
  }
}
</style>
