<template>
  <section id="trash">
    <div v-if="imageList.length > 0">
      <div class="trash-button" style="margin-bottom: 3rem; text-align: right">
        <a-button
          style="border-radius: 18px; margin-right: 1rem"
          @click="restoreAll"
          :disabled="imageList.length === 0"
          :loading="loading"
        >
          <HistoryOutlined /> Khôi phục tất cả</a-button
        >
        <a-button
          @click="removeAll"
          :loading="loading"
          :disabled="imageList.length === 0"
          style="border-radius: 18px; background-color: #e05858; color: #ffffff"
        >
          <DeleteOutlined /> Xóa tất cả</a-button
        >
      </div>
      <div class="trash-gallery">
        <div v-for="year in createdYearList" :key="year">
          <p
            v-if="
              dayjs(today, 'DD-MM-YYYY').format('YYYY') != year && imageList
            "
            style="
              margin-top: 2rem;
              margin-bottom: 2rem;
              text-transform: capitalize;
            "
            class="text-title"
          >
            {{ dayjs(year).locale("vi").format("YYYY") }}
          </p>
          <p v-else></p>
          <div v-for="month in createdMonthList" :key="month">
            <p
              class="text-sub-title text-upper"
              style="font-size: 24px"
              v-if="dayjs(month, 'MM-YYYY').locale('vi').format('YYYY') == year"
            >
              {{ dayjs(month, "MM-YYYY").locale("vi").format("MMMM") }}
            </p>
            <p v-else></p>
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
              <p v-else></p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <EmptyView v-else></EmptyView>
  </section>
</template>
<script>
import { useStore } from "vuex";
import { computed, ref, watchEffect } from "vue";
import { getTrashImage } from "@/apis/images";
import ImageList from "../components/Image/ImageList.vue";
import { useRouter } from "vue-router";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import { message } from "ant-design-vue";
import { DeleteOutlined, HistoryOutlined } from "@ant-design/icons-vue";
import EmptyView from "./EmptyView.vue";
import {
  removeAllImageFromTrash,
  restoreAllImageFromTrash,
} from "@/apis/images";
export default {
  components: {
    EmptyView,
    DeleteOutlined,
    ImageList,
    HistoryOutlined,
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
    const loading = ref(false);
    const getToday = () => {
      today.value = dayjs().format("DD-MM-YYYY");
    };
    const getYesterday = () => {
      const today = new Date();
      const preDay = new Date(today);
      preDay.setDate(today.getDate() - 1);

      yesterday.value = dayjs(preDay).format("DD-MM-YYYY");
    };
    const getImageList = async () => {
      await getTrashImage(token.value)
        .then((res) => {
          imageList.value = [...res.data];

          store.dispatch("image/getTrashImagesAction", {
            data: imageList.value,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const formatDate = (date) => {
      return dayjs(date).format("DD-MM-YYYY");
    };
    const getCreatedDateList = () => {
      createdDateList.value = [];
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

    const removeAll = async () => {
      loading.value = true;
      await removeAllImageFromTrash(token.value)
        .then((res) => {
          imageList.value = [];
          loading.value = false;
          message.success("Đã xóa tất cả ảnh!");
          console.log(res);
        })
        .catch((err) => {
          loading.value = false;
          message.error("Xóa tất cả ảnh thất bại!");
          console.log(err);
        });
    };

    const restoreAll = async () => {
      loading.value = true;
      await restoreAllImageFromTrash(token.value)
        .then((res) => {
          imageList.value = [];
          loading.value = false;
          message.success("Đã khôi phục tất cả ảnh!");
          console.log(res);
        })
        .catch((err) => {
          loading.value = false;
          message.error("Khôi phục tất cả ảnh thất bại!");
          console.log(err);
        });
    };
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
      restoreAll,
      removeAll,
      loading,
      getToday,
      getYesterday,
      getCreatedDateList,
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
