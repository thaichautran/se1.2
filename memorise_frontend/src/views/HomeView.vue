<template>
  <section id="home">
    <div v-if="token">
      <div class="home-slider">
        <SwipSlider />
      </div>
      <div class="home-gallery">
        <a-row>
          <a-col
            :span="3"
            v-for="item in imageList"
            :key="item?.id"
            @click="
              () => {
                router.push({ name: 'image', params: { image: item } });
              }
            "
          >
            <a-image style="height: 100px" :src="item?.url" />
          </a-col>
        </a-row>
      </div>
    </div>
  </section>
</template>
<script>
import SwipSlider from "../components/Slider/SwipSlider.vue";
import { useStore } from "vuex";
import { computed, ref, onMounted } from "vue";
import { getAllImageByUser } from "@/apis/images";
import ImageInfo from "../components/Image/ImageInfo.vue";
import { useRouter } from "vue-router";
export default {
  components: {
    SwipSlider,
    ImageInfo,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const token = computed(() => store.state.user.userLogin.token);
    const imageList = ref([]);

    const getImageList = async () => {
      await getAllImageByUser(token.value)
        .then((res) => {
          imageList.value = [...res.data];
          store.dispatch("image/getAllImagesAction", { data: imageList });
        })
        .catch((err) => {
          console.log(err);
        });
    };

    onMounted(() => {
      getImageList();
    });

    return {
      token,
      imageList,
      router,
    };
  },
};
</script>
<style lang="scss" scoped>
</style>
