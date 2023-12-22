const state = () => {
  return {
    albumList: [],
    imageAlbumList: [],
  };
};
const mutations = {
  setAlbumList(state, payload) {
    state.albumList = payload;
  },
};
const actions = {
  getAlbumList({ commit }, { data }) {
    commit("setAlbumList", data);
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
