<template>
  <a-layout style="min-height: 100vh; padding: 0.5rem" class="app-bar">
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      style="
        background-color: #fff;
        border-radius: 15px;
        box-shadow: rgba(0, 21, 41, 0.08) 0px 1px 4px 0px;
      "
    >
      <div
        class="logo"
        @click="
          () => {
            router.push('/');
          }
        "
      >
        <img
          src="../../assets/image/memorise_logo.svg"
          alt="logo"
          style="width: 50px; height: 50px; object-fit: cover"
        />
        <span v-if="!collapsed" class="logo-text">Memorise</span>
      </div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="light" mode="inline">
        <a-menu-item
          key="1"
          @click="
            () => {
              router.push('/');
            }
          "
        >
          <span class="anticon">
            <img src="../../assets/image/image_icon.svg" class="icon" alt="" />
          </span>
          <span class="menu-item-text">Ảnh</span>
        </a-menu-item>
        <a-menu-item
          key="2"
          @click="
            () => {
              router.push('/sharings');
            }
          "
        >
          <span class="anticon">
            <img src="../../assets/image/share_icon.svg" class="icon" alt="" />
          </span>
          <span class="menu-item-text">Chia sẻ</span>
        </a-menu-item>
        <p class="app-bar-text-space text-sub-3-title">Thư viện</p>
        <a-menu-item
          key="3"
          @click="
            () => {
              router.push('/albums');
            }
          "
        >
          <span class="anticon">
            <img src="../../assets/image/album_icon.png" class="icon" alt="" />
          </span>
          <span class="menu-item-text">Album</span>
        </a-menu-item>
        <a-menu-item
          key="4"
          @click="
            () => {
              router.push('/favorites');
            }
          "
        >
          <span class="anticon"
            ><img src="../../assets/image/star_icon.svg" class="icon" alt=""
          /></span>
          <span class="menu-item-text">Ảnh yêu thích</span>
        </a-menu-item>
        <a-menu-item
          key="5"
          @click="
            () => {
              router.push('/trash');
            }
          "
        >
          <span class="anticon"
            ><img src="../../assets/image/trash_icon.svg" class="icon" alt=""
          /></span>
          <span class="menu-item-text">Thùng rác</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <the-header
        @setCollapsed="
          (flag) => {
            collapsed = flag;
          }
        "
        :collapsedToChild="collapsed"
      ></the-header>
      <a-layout-content
        :style="{
          margin: '1.5rem 1rem',
          padding: '1.5rem',
          background: '#fff',
          minHeight: '280px',
        }"
      >
        <router-view></router-view>
      </a-layout-content>
      <the-footer></the-footer>
    </a-layout>
  </a-layout>
</template>
  <script>
import { defineComponent, ref } from "vue";
import TheHeader from "./TheHeader.vue";
import TheFooter from "./TheFooter.vue";
import { useRouter } from "vue-router";
export default defineComponent({
  components: {
    TheHeader,
    TheFooter,
  },
  setup() {
    const router = useRouter();
    return {
      selectedKeys: ref(["1"]),
      collapsed: ref(false),
      router,
    };
  },
});
</script>
<style lang="scss" scoped >
.logo {
  height: 50px;
  display: flex;
  align-items: center;
  margin: 16px;
  cursor: pointer;
}

.site-layout {
  .site-layout-background {
    background: #fff;
  }
}

.icon {
  height: 25px;
  width: 25px;
}
.menu-item-text {
  font-size: 1rem;
  margin-left: 0.5rem;
}
@font-face {
  font-family: fontLogo;
  src: url("../../assets/fonts/ArgentumSans-Light.otf");
}
.logo-text {
  font-family: fontLogo;
  font-size: 20px;
}
:deep(.ant-menu-item) {
  padding: 0 23px;
  align-items: center;
  position: relative;
}
:deep(.ant-menu-item) {
  &:last-child {
    position: absolute;
    left: 0;
    bottom: 1rem;
  }
}
:deep(.ant-menu-title-content) {
  display: flex;
  align-items: center;
}
.app-bar-text-space {
  padding: 3rem 1.7rem 1rem;
}
</style>