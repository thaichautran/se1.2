import { register, login } from "@/apis/user";

const state = () => {
  return {
    userRegister: {},
    userLogin: {},
  };
};
const mutations = {
  setUserRegister(state, payload) {
    state.userRegister = payload;
  },
  setUserLogin(state, payload) {
    state.userLogin = payload;
    localStorage.setItem("userLogin", JSON.stringify(payload.data));
  },
  loadFromLocalStorageMutation(state, payload) {
    state.userLogin = payload;
  },
  removeLocalStorageMutation(state) {
    state.userLogin = {};
  },
};
const actions = {
  async registerAction(context, { data, router }) {
    const userRegister = await register(data);
    context.commit("setUserRegister", userRegister);
    router.push("/authentication/login");
  },

  async loginAction(context, { data, router }) {
    try {
      const userLogin = await login(data);
      router.push("/home");
      context.commit("setUserLogin", userLogin);
    } catch (error) {
      //   alert("Tài Khoản hoặc mật khẩu không đúng");
    }
  },
  loadFromLocalStorageAction({ commit }) {
    let userLogin = {};
    if (localStorage.getItem("userLogin")) {
      userLogin = JSON.parse(localStorage.getItem("userLogin"));
    }
    commit("loadFromLocalStorageMutation", userLogin);
  },
  removeLocalStorageAction({ commit }) {
    if (localStorage.getItem("userLogin")) {
      localStorage.removeItem("userLogin");
    }
    commit("removeLocalStorageMutations");
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
