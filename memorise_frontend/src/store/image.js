const state = () => {
  return {
    imageList: [],
    imageTrashList: [],
    imageFavouriteList: [],
    imageSearchList: [],
  };
};
const mutations = {
  setImageList(state, payload) {
    state.imageList = payload;
  },
  setTrashImages(state, payload) {
    state.imageTrashList = payload;
  },
  setSearchList(state, payload) {
    state.imageSearchList = payload;
  },
};
const actions = {
  getAllImagesAction({ commit }, { data }) {
    commit("setImageList", data);
  },
  getTrashImagesAction({ commit }, { data }) {
    commit("setTrashImages", data);
  },
  getSearchListAction({ commit }, { data }) {
    commit("setSearchList", data);
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
