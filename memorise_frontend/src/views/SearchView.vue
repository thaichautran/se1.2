<template>
  <div class="search-view" v-if="allList.length > 0">
    <p>
      Tổng cộng <span class="title">{{ allList.length }}</span> kết quả tìm kiếm
      cho từ khóa
      <span class="title">"{{ route.query.key }}"</span>
    </p>
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="1" tab="Tất cả">
        <ImageView :list="allList" @getNewList="findImageList" />
      </a-tab-pane>
      <a-tab-pane key="2" tab="Ảnh" force-render>
        <ImageView :list="imageList" @getNewList="findImageList" />
      </a-tab-pane>
      <a-tab-pane key="3" tab="Video">
        <ImageView :list="videoList" @getNewList="findImageList"
      /></a-tab-pane>
    </a-tabs>
  </div>
  <EmptyView v-else />
</template>

<script>
import { computed, ref, watch } from "vue";
import { findImages } from "@/apis/images";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import ImageView from "../components/Image/ImageView.vue";
import EmptyView from "./EmptyView.vue";
export default {
  components: {
    ImageView,
    EmptyView,
  },
  setup() {
    const route = useRoute();
    const store = useStore();
    const activeKey = ref("1");
    const allList = ref([...store.state.image.imageSearchList]);
    const imageList = ref([...store.state.image.imageSearchList]);
    const videoList = ref([...store.state.image.imageSearchList]);
    const token = computed(() => store.state.user.userLogin.token);

    watch(route, (newValue) => {
      if (newValue) {
        findImageList();
      }
    });
    const findImageList = async () => {
      await findImages(route.query.key, token.value)
        .then((res) => {
          allList.value = [...res.data];

          imageList.value = [
            ...res.data.filter(
              (item) =>
                item.url.includes(".jpg") ||
                item.url.includes(".png") ||
                item.url.includes(".jpeg")
            ),
          ];
          videoList.value = [
            ...res.data.filter((item) => item.url.includes(".mp4")),
          ];
        })
        .catch((err) => console.log(err));
    };

    return {
      activeKey,
      allList,
      imageList,
      videoList,
      findImageList,
      route,
    };
  },
  created() {
    this.findImageList();
  },
};
</script>

<style lang="scss" scoped>
.title {
  font-weight: bold;
}
</style>