<template>
  <a-input
    v-model:value="keySearch"
    placeholder="Tìm kiếm theo vị trí, theo kỷ niệm"
    style="
      width: 500px;
      box-shadow: rgba(0, 21, 41, 0.08) 0px 1px 4px 0px;
      border-radius: 18px;
    "
    size="large"
  >
    <template #prefix>
      <SearchOutlined />
    </template>
  </a-input>
</template>

<script>
import { SearchOutlined } from "@ant-design/icons-vue";
import { ref } from "vue";
export default {
  components: {
    SearchOutlined,
  },
  setup() {
    const keySearch = ref("");
    return { keySearch };
  },
};
</script>

<style>
</style>