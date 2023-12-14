import { createStore } from "vuex";
import user from "./user";
import image from "./image";
export default createStore({
  modules: {
    user,
    image,
  },
});
