import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView";
import DefaultLayout from "../layout/DefaultLayout";

const routes = [
  {
    path: "/",
    component: DefaultLayout,
    children: [
      {
        path: "/",
        name: "home",
        component: HomeView,
      },
      {
        path: "/gallery",
        name: "gallery",
        component: () => import("../views/Gallery"),
      },
    ],
  },
  {
    path: "/authentication/:name",
    name: "auth",
    component: () => import("../views/Auth"),
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
