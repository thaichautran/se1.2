<template>
  <a-col
    class="album-item-hover"
    :span="4"
    style="cursor: pointer"
    @click="
      router.push({
        path: `/album`,
        query: {
          name: album.name,
          id: album.id,
          date: album.createDate,
          desc: album.description,
          url: album.url,
        },
      })
    "
  >
    <div class="album-item">
      <EllipsisOutlined
        style="
          position: absolute;
          top: 0.25rem;
          right: 0.75rem;
          z-index: 1;
          font-size: 1.5rem;
          font-weight: bold;
          color: #ffffff;
        "
      />
      <img class="album-cover" :src="album.url" alt="" />
      <h3 class="album-title">{{ album.name }}</h3>
      <span class="album-length">{{ album.length }}</span>
    </div>
  </a-col>
</template>

<script>
import { EllipsisOutlined } from "@ant-design/icons-vue";
import { useRouter } from "vue-router";
export default {
  components: {
    EllipsisOutlined,
  },
  props: {
    album: {
      type: Object,
    },
  },
  setup() {
    const router = useRouter();
    return { router };
  },
};
</script>

<style lang="scss" scoped>
.album-item {
  position: relative;
  cursor: pointer;
}

.album-cover {
  width: 100%;
  aspect-ratio: 1/1;
  filter: drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25));
  border-radius: 8px;
  object-fit: cover;
}
.album-title {
  margin-top: 0.5rem;
  font-weight: bold;
  text-align: center;
}
.album-length {
  margin-top: 0.5rem;
  text-align: center;
}

.album-item-hover {
  transition: all 0.5s;
  &:hover {
    opacity: 0.7;
  }
}
</style>