<template>
  <section id="image-view">
    <div v-if="list.length > 0">
      <div class="image-view-gallery">
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
                  @getNewList="getImageList"
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
                  @getNewList="getImageList"
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
                  @getNewList="getImageList"
                />
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <h1 class="text-title" v-else>Không có gì ở đây!</h1>
  </section>
</template>
  <script>
import { ref, watchEffect } from "vue";
import ImageList from "../../components/Image/ImageList.vue";

import dayjs from "dayjs";
import "dayjs/locale/vi";

export default {
  components: {
    ImageList,
  },
  props: {
    list: {
      type: Array,
    },
  },
  setup(props, { emit }) {
    const createdDateList = ref([]);
    const createdMonthList = ref([]);
    const createdYearList = ref([]);
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
    };

    const formatDate = (date) => {
      return dayjs(date).format("DD-MM-YYYY");
    };
    const getCreatedDateList = () => {
      createdDateList.value = [];
      props.list.forEach((image) => {
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
      return props.list.filter((image) => {
        return formatDate(image.createDate) === date;
      });
    };
    const getImageList = () => {
      emit("getNewList");
    };
    watchEffect(() => {
      getCreatedDateList();
      getMonth();
      getYear();
      getToday();
      getYesterday();
      props;
    });

    return {
      yesterday,
      today,
      createdDateList,
      formatDate,
      dayjs,
      getImageListByDate,
      getMonth,
      getYear,
      createdMonthList,
      createdYearList,
      getImageList,
    };
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
  