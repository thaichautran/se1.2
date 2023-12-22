<template>
  <a-col :span="4">
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
    return {};
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
</style>