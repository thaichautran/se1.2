<template>
  <div class="album-detail">
    <div class="album-detail-top">
      <div class="album-detail-top-left">
        <h1 class="text-title" style="margin-bottom: 1rem">
          {{ route.query.name }}
        </h1>
        <p style="color: #565e6c; font-weight: 700">
          {{
            dayjs(route.query.date)
              .locale("vi")
              .format("Ngày D MMMM [năm] YYYY, [lúc] HH [giờ] mm [phút]")
          }}
        </p>
        <a-button
          class="album-detail-btn"
          @click="
            () => {
              router.push({
                name: 'slideshow',
                query: { id: route.query.id, name: route.query.name },
              });
            }
          "
        >
          <PlayCircleOutlined /> Trình chiếu
        </a-button>
      </div>
      <div class="album-detail-top-right">
        <p style="text-align: left">{{ route.query.desc }}</p>
        <div class="album-icon">
          <a-tooltip>
            <template #title>Thêm ảnh vào album</template>
            <PlusCircleOutlined
              :onLoad="loading"
              @click="showModal"
              class="album-detail-icon"
            />
          </a-tooltip>

          <a-dropdown :trigger="['click']">
            <a class="ant-dropdown-link" @click.prevent>
              <a-tooltip>
                <template #title>Tùy chọn</template>
                <EllipsisOutlined class="album-detail-icon" />
              </a-tooltip>
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item key="0">
                  <span class="icon-delete" @click="handleRemoveImage"
                    ><img
                      class="image-modal-icon"
                      src="../assets/image/Photo album.svg"
                      alt=""
                    />
                    Thay đổi ảnh bìa</span
                  >
                </a-menu-item>
                <a-menu-item key="1">
                  <span
                    ><img
                      class="image-modal-icon"
                      src="../assets/image/Edit.svg"
                      alt=""
                    />
                    Chỉnh sửa</span
                  ></a-menu-item
                >
                <a-menu-item key="3">
                  <span class="icon-delete" @click="handleRemoveAlbum"
                    ><img
                      class="image-modal-icon"
                      src="../assets/image/Photo_delete.svg"
                      alt=""
                    />
                    Xóa album</span
                  >
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </div>
    </div>
    <div class="album-detail-bot">
      <div v-if="imageAlbumList.length > 0">
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
                style="font-size: 24px; margin-bottom: 0"
                v-if="
                  dayjs(month, 'MM-YYYY').locale('vi').format('YYYY') == year
                "
              >
                {{ dayjs(month, "MM-YYYY").locale("vi").format("MMMM") }}
              </p>
              <div v-for="date in createdDateList" :key="date">
                <p
                  class="text-sub-3-title text-upper"
                  style="font-size: 18px; margin-top: 1rem"
                  v-if="
                    dayjs(date, 'DD-MM-YYYY').locale('vi').format('MM-YYYY') ===
                      month &&
                    dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') ===
                      year &&
                    dayjs(date, 'DD-MM-YYYY')
                      .locale('vi')
                      .format('DD-MM-YYYY') == today
                  "
                >
                  Hôm nay
                  <ImageList
                    style="margin-top: 1rem; margin-bottom: 3rem"
                    :imageList="getImageListByDate(date)"
                    @getNewList="getImageList"
                  />
                </p>

                <p
                  class="text-sub-3-title text-upper"
                  style="font-size: 18px; margin-top: 1rem"
                  v-if="
                    dayjs(date, 'DD-MM-YYYY').locale('vi').format('MM-YYYY') ===
                      month &&
                    dayjs(date, 'DD-MM-YYYY').locale('vi').format('YYYY') ===
                      year &&
                    dayjs(date, 'DD-MM-YYYY')
                      .locale('vi')
                      .format('DD-MM-YYYY') == yesterday
                  "
                >
                  Hôm qua
                  <ImageList
                    style="margin-top: 2rem; margin-bottom: 3rem"
                    :imageList="getImageListByDate(date)"
                    @getNewList="getImageList"
                  />
                </p>

                <p
                  class="text-sub-3-title text-upper"
                  style="font-size: 18px; margin-top: 1rem"
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
                    style="margin-top: 2rem; margin-bottom: 3rem"
                    :imageList="getImageListByDate(date)"
                    @getNewList="getImageList"
                  />
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <EmptyView v-else />
    </div>
    <a-modal v-model:open="open" style="width: 100%">
      <div class="album-modal-title">
        <span>Chọn ảnh</span>
        <a-upload
          name="file2"
          :multiple="false"
          :max-count="1"
          :before-upload="beforeUpload"
          @change="handleChange"
          @remove="handleRemove"
          style="display: block; width: 150px"
          :height="150"
        >
          <a-button style="border-radius: 18px">
            Chọn ảnh từ thiết bị
          </a-button>
        </a-upload>
      </div>
      <ImageListModal
        @closeModal="handleUploadImage"
        @setImage="setImage"
        style="margin-top: 1rem"
      ></ImageListModal>
    </a-modal>
  </div>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import { getImagesFromAlbum } from "@/apis/albums";
import { useStore } from "vuex";
import { ref, watchEffect } from "vue";
import { computed } from "vue";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import ImageList from "../components/Image/ImageList.vue";
import EmptyView from "./EmptyView.vue";
import ImageListModal from "../components/Modal/ImageListModal.vue";
import { message } from "ant-design-vue";
import { uploadImageToAlbumFromHome, deleteAlbum } from "@/apis/albums";
import {
  PlayCircleOutlined,
  EllipsisOutlined,
  PlusCircleOutlined,
} from "@ant-design/icons-vue";
export default {
  components: {
    ImageList,
    EmptyView,
    PlayCircleOutlined,
    EllipsisOutlined,
    PlusCircleOutlined,
    ImageListModal,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const token = computed(() => store.state.user.userLogin.token);
    const imageAlbumList = ref([...store.state.album.imageAlbumList]);
    const createdDateList = ref([]);
    const createdMonthList = ref([]);
    const createdYearList = ref([]);
    const today = ref(new Date());
    const yesterday = ref();
    const open = ref(false);
    const fileUpload = ref("");
    const loading = ref(false);
    const imageId = ref();
    const showModal = () => {
      open.value = true;
    };
    const getImageList = async () => {
      await getImagesFromAlbum(route.query.id, token.value)
        .then((res) => {
          imageAlbumList.value = [...res.data];
          store.dispatch("album/getImageAlbumList", { data: res.data });
        })
        .catch((err) => {
          console.log(err);
        });
    };

    watchEffect(() => {
      imageAlbumList.value = [...store.state.album.imageAlbumList];
    });
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

    const formatDate = (date) => {
      return dayjs(date).format("DD-MM-YYYY");
    };
    const getCreatedDateList = () => {
      createdDateList.value = [];
      imageAlbumList.value.forEach((image) => {
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
      return imageAlbumList.value.filter((image) => {
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

    const beforeUpload = (file) => {
      const isJpgOrPng =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg";
      if (!isJpgOrPng) {
        message.error("Chỉ có thể tải lên ảnh!");
        fileUpload.value = "";

        return false;
      }
      if (
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/jpg"
      ) {
        const isLt10M = file.size / 1024 / 1024 < 10;
        if (!isLt10M) {
          message.error("Ảnh phải nhỏ hơn 10MB!");

          fileUpload.value = "";
          return false;
        }
        fileUpload.value = file;
        return false;
      }
      fileUpload.value = file;
      return false;
    };
    const handleChange = (info) => {
      console.log(info);
      //   if (info.fileList.length > 0) {
      //     if (
      //       info.file.type == "image/jpeg" ||
      //       info.file.type == "image/png" ||
      //       info.file.type == "image/jpg"
      //     ) {
      //     }
      //   }
    };
    const handleRemove = () => {
      fileUpload.value = "";
    };
    const setImage = (image) => {
      imageId.value = image.id;
    };
    const handleUploadImage = async () => {
      loading.value = true;
      await uploadImageToAlbumFromHome(
        route.query.id,
        imageId.value,
        token.value
      )
        .then((res) => {
          console.log(res);
          loading.value = false;
          message.success("Thêm ảnh thành công!");
          open.value = false;
          getImageList();
        })
        .catch((err) => {
          console.log(err);
          message.error("Thêm ảnh thất bại!");
          loading.value = false;
          open.value = false;
        });
    };
    const handleRemoveAlbum = async () => {
      await deleteAlbum(route.query.id, token.value)
        .then((res) => {
          console.log(res);
          message.success("Xóa album thành công!");
          router.push("/albums");
        })
        .catch((err) => {
          console.log(err);
          message.error("Xóa album thất bại!");
        });
    };
    return {
      getImageList,
      imageAlbumList,
      route,
      dayjs,
      createdDateList,
      getImageListByDate,
      createdYearList,
      createdMonthList,
      showModal,
      handleChange,
      beforeUpload,
      handleRemove,
      fileUpload,
      open,
      handleUploadImage,
      loading,
      setImage,
      handleRemoveAlbum,
      router,
    };
  },
  created() {
    this.getImageList();
  },
};
</script>

<style lang="scss" scoped>
.album-detail-btn {
  border-radius: 18px;
  font-size: 1rem;
  &:hover {
    background-color: #171a1f;
    color: #ffffff;
  }
}
.album-detail-top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 2rem;
}
.album-detail-top-right {
  width: 60%;
  .album-icon {
    text-align: right;
    .album-detail-icon {
      text-align: right;
      font-size: 1.2rem;
      &:first-child {
        margin-right: 1rem;
      }
    }
  }
}
.text-upper {
  &::first-letter {
    text-transform: uppercase;
  }
}
.album-modal-title {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  margin-top: 1.5rem;
}
.image-modal-icon {
  width: 20px;
  height: 20px;
}
</style>