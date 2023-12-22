<template>
  <a-col
    :span="3"
    @click.prevent="showModal"
    style="cursor: pointer"
    class="image-item-hover"
  >
    <a-modal v-model:open="open" style="width: 1100px">
      <template #footer>
        <div style="text-align: -webkit-right">
          <div class="ant-footer-custom">
            <a-button
              v-if="isFavourite"
              key="favourite0"
              style="
                border-radius: 18px;
                color: rgb(202, 202, 3);
                border-color: rgb(202, 202, 3);
              "
              @click="handleFavourite"
              ><StarOutlined /> Yêu thích</a-button
            >
            <a-button
              v-else
              key="favourite2"
              style="border-radius: 18px"
              @click="handleFavourite"
              ><StarOutlined /> Yêu thích</a-button
            >
            <a-button
              key="share"
              style="border-radius: 18px"
              @click="handleShare"
              ><ShareAltOutlined /> Chia sẻ</a-button
            >
          </div>
        </div>
      </template>
      <ImageModal
        :image="image"
        @closeModal="closeModal"
        @getNewList="getNewList"
      ></ImageModal>
    </a-modal>
    <img
      v-if="
        image.url.includes('.jpg') ||
        image.url.includes('.png') ||
        image.url.includes('.jpeg')
      "
      style="
        aspect-ratio: 1 / 1;
        width: 100%;
        object-fit: cover;
        object-position: center;
      "
      v-lazy="image.url"
      :src="image.url"
    />
    <div v-else-if="image.url.includes('.mp4')">
      <video
        :src="image.url"
        style="aspect-ratio: 1 / 1; width: 100%"
        controls
      ></video>
    </div>
    <div v-else></div>
  </a-col>
</template>

<script>
import { computed, onMounted, ref } from "vue";
import ImageModal from "../Modal/ImageModal.vue";
import { StarOutlined, ShareAltOutlined } from "@ant-design/icons-vue";
import { favouriteImage } from "@/apis/images";
import { useStore } from "vuex";
import useClipboard from "vue-clipboard3";
import { message } from "ant-design-vue";
export default {
  props: {
    image: {
      type: Object,
    },
  },
  components: {
    ImageModal,
    StarOutlined,
    ShareAltOutlined,
  },
  setup(props, { emit }) {
    const store = useStore();
    const token = computed(() => store.state.user.userLogin.token);
    const open = ref(false);
    const { toClipboard } = useClipboard();
    const showModal = () => {
      open.value = true;
    };
    const closeModal = () => {
      open.value = false;
    };
    const getNewList = () => {
      emit("getNewList");
    };
    const isFavourite = ref(props.image.favourite);
    const handleFavourite = async () => {
      if (isFavourite.value) {
        await favouriteImage(props.image.id, false, token.value)
          .then((res) => {
            isFavourite.value = res.data.favourite;
            emit("getNewList");
          })
          .catch((err) => console.log(err));
      } else {
        await favouriteImage(props.image.id, true, token.value)
          .then((res) => {
            isFavourite.value = res.data.favourite;
            emit("getNewList");
          })
          .catch((err) => console.log(err));
      }
    };
    onMounted(() => {
      if (!open.value) {
        document.body.style.overflow = "unset";
      }
    });

    const handleShare = async () => {
      try {
        await toClipboard(props.image.url);
        message.success("Đã sao chép đường dẫn vào bộ nhớ tạm!");
      } catch (e) {
        console.error(e);
        message.error("Sao chép đường dẫn thất bại!");
      }
    };
    return {
      showModal,
      open,
      handleFavourite,
      favouriteImage,
      isFavourite,
      closeModal,
      getNewList,
      handleShare,
    };
  },
};
</script>

<style lang="scss" scoped>
.ant-modal-content {
  .ant-footer-custom {
    display: flex;
    justify-content: space-between;
    width: 50%;
  }
}
.image-item-hover {
  transition: all 0.5s;
  &:hover {
    opacity: 0.7;
  }
}
</style>
