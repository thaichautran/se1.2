import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
//ant-design-vue
import Antd from "ant-design-vue";
//vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
const vuetify = createVuetify({
  components,
  directives,
});
//gg login
import vue3GoogleLogin from "vue3-google-login";
// style
import "ant-design-vue/dist/reset.css";
import "./assets/scss/main.scss";

createApp(App)
  .use(store)
  .use(router)
  .use(Antd)
  .use(vuetify)
  .use(vue3GoogleLogin, {
    clientId:
      "1083211549742-c6k1r9nma9dr2dr99kva7gsi1g6khbfk.apps.googleusercontent.com",
  })
  .mount("#app");
