import { createStore } from "vuex";
import user from "./user";
import image from "./image";
import album from "./album";
export default createStore({
  modules: {
    user,
    image,
    album,
  },
});
