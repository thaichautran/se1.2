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
  registerAction(context, { data, router }) {
    context.commit("setUserRegister", data);
    router.push("/authentication/login");
  },

  loginAction(context, { data, router }) {
    router.push("/home");
    context.commit("setUserLogin", data);
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
