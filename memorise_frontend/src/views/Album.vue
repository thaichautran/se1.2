<template>
  <div id="album">
    <div class="album-header">
      <h1 class="text-title" style="margin-bottom: 2rem">Album ảnh</h1>
      <a-button
        class="image-modal-button"
        style="border-radius: 18px; font-size: 16px"
        @click="
          () => {
            router.push('/albums/create_album');
          }
        "
      >
        <img
          style="margin-right: 0.25rem; margin-bottom: 0.25rem"
          src="../assets/image/Photo album.svg"
          class="image-modal-icon"
          alt=""
        />
        Tạo album ảnh mới
      </a-button>
    </div>
    <div class="album-list" v-if="albumList.length > 0">
      <AlbumList :albumList="albumList"></AlbumList>
    </div>
    <EmptyView v-else></EmptyView>
  </div>
</template>

<script>
import { getAlbums } from "@/apis/albums";
import { computed, ref, watchEffect } from "vue";
import EmptyView from "./EmptyView.vue";
import AlbumList from "../components/Album/AlbumList.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  components: {
    AlbumList,
    EmptyView,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const token = computed(() => store.state.user.userLogin.token);
    const albumList = ref([...store.state.album.albumList]);
    watchEffect(() => {
      albumList.value = [...store.state.album.albumList];
    });

    const getAlbumList = async () => {
      await getAlbums(token.value)
        .then((res) => {
          albumList.value = [...res.data];
          store.dispatch("album/getAlbumList", { data: res.data });
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return {
      albumList,
      getAlbumList,
      router,
    };
  },
  created() {
    this.getAlbumList();
  },
};
</script>

<style lang="scss" scoped>
.album-header {
  display: flex;
  justify-content: space-between;
}
</style>