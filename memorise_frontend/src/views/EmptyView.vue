<template>
  <div class="empty">
    <div class="empty-container" v-if="route.path == '/trash'">
      <div class="empty-image">
        <img src="../assets/image/Images_empty.svg" alt="empty" />
      </div>
      <h1 class="empty-title">Không có ảnh nào</h1>

      <p class="empty-desc">Thùng rác trống!</p>
    </div>
    <div class="empty-container" v-else-if="route.path == '/favorites'">
      <div class="empty-image">
        <img src="../assets/image/Images_empty.svg" alt="empty" />
      </div>
      <h1 class="empty-title">Không có ảnh nào</h1>

      <p class="empty-desc">Hãy thêm những kỷ niệm yêu thích của bạn!</p>
    </div>
    <div class="empty-container" v-else-if="route.path == '/albums'">
      <div class="empty-image">
        <img src="../assets/image/Images_empty.svg" alt="empty" />
      </div>
      <h1 class="empty-title">Không có album nào</h1>

      <p class="empty-desc">
        Hãy tạo những album của bạn để trình chiếu đỉnh cao!
      </p>
    </div>
    <div class="empty-container" v-else-if="route.path == '/album'">
      <div class="empty-image">
        <img
          src="../assets/image/Images_empty.svg"
          style="width: 300px"
          alt="empty"
        />
      </div>
      <h1 class="empty-title">Không có ảnh nào trong album</h1>

      <p class="empty-desc">
        Hãy thêm ảnh vào album của bạn để trình chiếu đỉnh cao!
      </p>
    </div>
    <div class="empty-container" v-else-if="route.path == '/search'">
      <div class="empty-image">
        <img src="../assets/image/notsearch.png" alt="empty" />
      </div>
      <h1 class="empty-title">Không tìm thấy kết quả nào!</h1>

      <p class="empty-desc">Hãy thử tìm kiếm với từ khóa khác</p>
    </div>
    <div class="empty-container" v-else>
      <div class="empty-image">
        <img src="../assets/image/Images_empty.svg" alt="empty" />
      </div>
      <h1 class="empty-title">Không có ảnh nào</h1>

      <p class="empty-desc">
        Bạn chưa tải ảnh lên. Hãy tải ảnh lên để lưu giữ những khoảnh khắc trong
        cuộc sống!
      </p>

      <div class="empty-action">
        <UploadModal></UploadModal>
      </div>
    </div>
  </div>
</template>

<script>
import UploadModal from "@/components/Modal/UploadModal.vue";

import { useRoute } from "vue-router";
export default {
  components: {
    UploadModal,
  },
  setup() {
    const route = useRoute();
    return {
      route,
    };
  },
};
</script>

<style lang="scss" scoped>
.empty {
  position: relative;
  text-align: center;
  .empty-container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, 0%);
    .empty-title {
      font-size: 2rem;
      font-weight: 700;
      margin-top: 1rem;
    }
    .empty-desc {
      font-size: 1rem;
      margin-top: 1rem;
      color: #565e6c;
    }
    .empty-action {
      margin-top: 2rem;
    }
  }
}
</style>