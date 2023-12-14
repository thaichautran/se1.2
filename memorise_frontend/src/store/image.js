const state = () => {
  return {
    imageList: [],
  };
};
const mutations = {
  setImageList(state, payload) {
    state.imageList = payload;
  },
};
const actions = {
  getAllImagesAction({ commit }, { data }) {
    commit("setImageList", data);
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
