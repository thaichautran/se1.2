import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView";
import DefaultLayout from "../layout/DefaultLayout";

const routes = [
  {
    path: "/",
    redirect: "/authentication/login",
    component: DefaultLayout,
    children: [
      {
        path: "/home",
        name: "home",
        component: HomeView,
      },
      {
        path: "/gallery",
        name: "gallery",
        component: () => import("../views/Gallery"),
      },
      {
        path: "/sharings",
        name: "sharings",
        component: () => import("../views/Sharing"),
      },
      {
        path: "/albums",
        name: "albums",
        component: () => import("../views/Album"),
      },
      {
        path: "/favorites",
        name: "favorites",
        component: () => import("../views/Favorite"),
      },
      {
        path: "/trash",
        name: "trash",
        component: () => import("../views/Trash"),
      },
    ],
  },
  {
    path: "/authentication/:name",
    name: "auth",
    component: () => import("../views/Auth"),
  },
  {
    path: "/image/:id",
    name: "image",
    component: () => import("../views/ImageDetails"),
  },
  {
    path: "/introduction/",
    name: "intro",
    component: () => import("../views/Introduction"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
