<template>
  <a-row :gutter="[8, 8]">
    <a-col
      :span="3"
      v-for="item in albumList"
      :key="item.id"
      style="cursor: pointer"
      @click="
        () => {
          $emit('setImage', item);
          $emit('closeModal');
        }
      "
    >
      <img
        style="width: 100%; aspect-ratio: 1/1; object-fit: cover"
        v-lazy="item.url"
        :src="item.url"
      />
      <h3 style="font-weight: bold">{{ item.name }}</h3>
    </a-col>
  </a-row>
</template>
  
  <script>
import { ref, watchEffect, computed } from "vue";
import { useStore } from "vuex";
import { getAlbums } from "@/apis/albums";
export default {
  setup() {
    const store = useStore();
    const albumList = ref([...store.state.album.albumList]);
    const token = computed(() => store.state.user.userLogin.token);
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
    };
  },
  created() {
    this.getAlbumList();
  },
};
</script>
  
  <style>
</style>