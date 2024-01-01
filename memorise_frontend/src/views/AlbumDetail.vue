<template>
  <div class="album-detail">
    <div class="album-detail-top">
      <div class="album-detail-top-left">
        <h1 class="text-title" style="margin-bottom: 1rem" v-if="!isUpdate">
          {{ formState.name }}
        </h1>
        <a-input
          placeholder="Tên album"
          v-model:value="formState.name"
          :bordered="false"
          style="
            border-bottom: solid 1px #9095a0;
            border-radius: 0;
            margin-bottom: 1rem;
          "
          v-else
          @keyup.enter="handleUpdateAlbum"
        >
          {{ route.query.name }}
        </a-input>
        <p
          v-if="createdDateList.length >= 2"
          style="color: #565e6c; font-weight: 700"
        >
          {{
            dayjs(createdDateList[createdDateList.length - 1], "DD-MM-YYYY")
              .locale("vi")
              .format("Ngày D MMMM [năm] YYYY")
          }}
          -
          {{
            dayjs(createdDateList[0], "DD-MM-YYYY")
              .locale("vi")
              .format("Ngày D MMMM [năm] YYYY")
          }}
        </p>
        <p
          style="color: #565e6c; font-weight: 700"
          v-else-if="createdDateList.length == 1"
        >
          {{
            dayjs(createdDateList[0], "DD-MM-YYYY")
              .locale("vi")
              .format("Ngày D MMMM [năm] YYYY")
          }}
        </p>
        <p
          style="color: #565e6c; font-weight: 700"
          v-else-if="createdDateList.length <= 0"
        >
          {{
            dayjs(route.query.date)
              .locale("vi")
              .format("Ngày D MMMM [năm] YYYY")
          }}
        </p>
        <a-button
          :disabled="imageAlbumList.length <= 0"
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
        <p style="text-align: left" v-if="!isUpdate">{{ formState.desc }}</p>
        <a-textarea
          v-else
          style="height: 170px"
          v-model:value="formState.desc"
          placeholder="Mô tả"
          @keyup.enter="handleUpdateAlbum"
        />
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
                  <span class="icon-delete" @click="showModal2"
                    ><img
                      class="image-modal-icon"
                      src="../assets/image/Photo album.svg"
                      alt=""
                    />
                    Thay đổi ảnh bìa</span
                  >
                </a-menu-item>
                <a-menu-item key="1">
                  <span @click="isUpdate = true"
                    ><img
                      class="image-modal-icon"
                      src="../assets/image/Edit.svg"
                      alt=""
                    />
                    Chỉnh sửa</span
                  ></a-menu-item
                >
                <a-menu-item key="3">
                  <span class="icon-delete" @click="open2 = true"
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
                    :albumId="route.query.id"
                  />
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <EmptyView v-else />
      <a-modal v-model:open="open2" title="Bạn chắc chắn muốn xóa album?">
        <template #footer>
          <a-button
            key="back"
            class="modal-footer"
            style="border-radius: 18px"
            @click="open = false"
            >Hủy</a-button
          >
          <a-button
            key="submit"
            class="modal-footer"
            :loading="loading"
            style="border-radius: 18px"
            @click="handleRemoveAlbum"
            >Xóa</a-button
          >
        </template>
        <p>Toàn bộ ảnh và video trong album vẫn được giữ nguyên!</p>
      </a-modal>
    </div>
  </div>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import { getImagesFromAlbum } from "@/apis/albums";
import { useStore } from "vuex";
import { reactive, ref, watchEffect } from "vue";
import { computed } from "vue";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import ImageList from "../components/Image/ImageList.vue";
import EmptyView from "./EmptyView.vue";
import { message } from "ant-design-vue";
import { deleteAlbum, updateAlbum } from "@/apis/albums";
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
    const open2 = ref(false);
    const fileUpload = ref("");
    const loading = ref(false);
    const imageId = ref();
    const isUpdate = ref(false);
    const formState = reactive({
      name: route.query.name,
      desc: route.query.desc,
    });
    const showModal = async () => {
      open.value = true;
      await router.push({
        path: "/album/pick_item",
        query: { id: route.query.id, type: "image" },
      });
    };
    const showModal2 = async () => {
      open.value = true;
      await router.push({
        path: "/album/pick_item",
        query: {
          id: route.query.id,
          type: "coverImage",
          name: formState.name,
          desc: formState.desc,
        },
      });
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
    const handleUpdateAlbum = async () => {
      const rqbd = {
        id: route.query.id,
        coverPhoto: route.query.url,
        name: formState.name,
        desc: formState.desc,
      };
      await updateAlbum(rqbd, token.value)
        .then((res) => {
          console.log(res);
          message.success("Cập nhật album thành công!");
          formState.name = res.data.name;
          formState.desc = res.data.description;
          isUpdate.value = false;
        })
        .catch((err) => {
          console.log(err);
          message.error("Cập nhật album thất bại!");
          isUpdate.value = false;
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

    const setImage = (image) => {
      imageId.value = image.id;
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
      fileUpload,
      open,
      loading,
      setImage,
      handleRemoveAlbum,
      router,
      open2,
      isUpdate,
      formState,
      handleUpdateAlbum,
      showModal2,
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
  &:disabled {
    border-color: #d9d9d9;
    color: rgba(0, 0, 0, 0.25);
    background-color: rgba(0, 0, 0, 0.04);
    box-shadow: none;
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
.modal-footer {
  &:last-child:hover {
    background-color: #e05858;
    color: #ffffff;
    border-color: #e05858;
  }
}
</style>