<template>
  <a-layout-header
    id="header"
    style="
      background: #fff;
      margin: 0 1rem 0 0.75rem;
      border-radius: 15px;
      border-radius: 15px;
      padding: 0;
      box-shadow: rgba(0, 21, 41, 0.08) 0px 1px 4px 0px;
    "
  >
    <menu-unfold-outlined
      v-if="collapsedToChild"
      class="trigger"
      @click="$emit('setCollapsed', false)"
    />
    <menu-fold-outlined
      v-else
      class="trigger"
      @click="$emit('setCollapsed', true)"
    />

    <a-space direction="vertical" style="width: 90%; margin-left: 1rem">
      <div class="header-row">
        <div class="header-row-left">
          <a-input
            v-model:value="value"
            placeholder='Tìm kiếm "Thứ 7", "Thành phố Hồ Chí Minh"'
            style="
              width: 500px;
              box-shadow: rgba(0, 21, 41, 0.08) 0px 1px 4px 0px;
            "
            size="large"
          >
            <template #prefix>
              <SearchOutlined />
            </template>
          </a-input>
        </div>
        <div class="header-row-right">
          <span style="font-size: 16px" class="interactive">
            <UploadOutlined />
            Tải lên
          </span>
          <SettingOutlined style="font-size: 20px" class="interactive" />

          <a-avatar :size="48" class="interactive">
            <template #icon><UserOutlined /></template>
          </a-avatar>
        </div>
      </div>
    </a-space>
  </a-layout-header>
</template>

<script>
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  SearchOutlined,
  SettingOutlined,
  UploadOutlined,
  UserOutlined,
} from "@ant-design/icons-vue";
import { ref } from "vue";

export default {
  components: {
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    SearchOutlined,
    SettingOutlined,
    UserOutlined,
    UploadOutlined,
  },
  props: {
    collapsedToChild: {
      type: Boolean,
    },
  },
  setup() {
    let value = ref("");
    return {
      value,
    };
  },
};
</script>

<style lang="scss" scoped>
.trigger {
  font-size: 1.125rem;
  line-height: 4rem;
  padding: 0 1.5rem;
  cursor: pointer;
  transition: color 0.3s;
}

.trigger {
  &:hover {
    color: #1890ff;
  }
}
#header {
  .header-row {
    width: 100%;
    display: flex;
    justify-content: space-between;
    .header-row-right {
      width: 20%;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
  }
}

.interactive {
  cursor: pointer;
}
</style>