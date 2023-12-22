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
  setImageAlbumList(state, payload) {
    state.imageAlbumList = payload;
  },
};
const actions = {
  getAlbumList({ commit }, { data }) {
    commit("setAlbumList", data);
  },
  getImageAlbumList({ commit }, { data }) {
    commit("setImageAlbumList", data);
  },
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
