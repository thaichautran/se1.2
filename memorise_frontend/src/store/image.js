const state = () => {
  return {
    imageList: [],
    imageTrashList: [],
    imageFavouriteList: [],
  };
};
const mutations = {
  setImageList(state, payload) {
    state.imageList = payload;
  },
  setTrashImages(state, payload) {
    state.imageTrashList = payload;
  },
};
const actions = {
  getAllImagesAction({ commit }, { data }) {
    commit("setImageList", data);
  },
  getTrashImagesAction({ commit }, { data }) {
    commit("setTrashImages", data);
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
