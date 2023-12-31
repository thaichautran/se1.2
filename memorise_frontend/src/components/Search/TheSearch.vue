<template>
  <div class="search">
    <a-input
      v-model:value="keySearch"
      @blur="handleBlur()"
      @focus="
        () => {
          isBlur = false;
        }
      "
      @keyup.enter="
        () => {
          router.push({
            path: `/search`,
            query: {
              key: keySearch,
            },
          });
        }
      "
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
    <div class="search-list">
      <ul
        class="search-ul"
        v-if="imageSearchList.length > 0 && keySearch && !isBlur"
      >
        <li
          v-for="(key, index) in imageSearchList"
          :key="index"
          @click="handleClickSearchItem(key)"
        >
          <span class="search-list-title">{{ key }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { SearchOutlined } from "@ant-design/icons-vue";
import { ref, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, watch } from "vue";
import { findImages } from "@/apis/images";

export default {
  components: {
    SearchOutlined,
  },
  setup() {
    const keySearch = ref("");
    const store = useStore();
    const router = useRouter();
    const token = computed(() => store.state.user.userLogin.token);
    const imageSearchList = ref([...store.state.image.imageSearchList]);
    const isBlur = ref(false);

    watchEffect(() => {
      imageSearchList.value = [...store.state.image.imageSearchList];
    });
    watch(keySearch, async (newValue) => {
      await findImages(newValue, token.value)
        .then((res) => {
          imageSearchList.value = res.data;
          imageSearchList.value = [
            ...imageSearchList.value.map((item) => {
              if (item.name.includes(keySearch.value)) {
                return item.name.split("\n")[0].trim();
              } else if (item.location.includes(keySearch.value)) {
                return item.location.split("\n")[0].trim();
              } else {
                return (
                  item.name.split("\n")[0].trim(),
                  item.location.split("\n")[0].trim()
                );
              }
            }),
          ];
          imageSearchList.value = new Set(imageSearchList.value);
          store.dispatch("image/getSearchListAction", {
            data: imageSearchList.value,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    });

    const handleBlur = () => {
      setTimeout(() => {
        isBlur.value = true;
      }, 200);
    };
    const handleClickSearchItem = (key) => {
      router.push({
        path: `/search`,
        query: {
          key,
        },
      });
    };

    return {
      keySearch,
      imageSearchList,
      handleClickSearchItem,
      handleBlur,
      isBlur,
      router,
    };
  },
};
</script>

<style lang="scss" scoped>
.search-list {
  position: relative;
  .search-ul {
    position: absolute;
    border-radius: 8px;
    background: #fff;
    box-shadow: 0px 4px 9px 0px rgba(23, 26, 31, 0.11),
      0px 0px 2px 0px rgba(23, 26, 31, 0.12);
    z-index: 2;
    li {
      list-style-type: none;
      padding: 1rem 1rem;
      line-height: 1rem;
      &:hover {
        cursor: pointer;
        background: #f5f5f5;
      }
    }
  }
}
</style>