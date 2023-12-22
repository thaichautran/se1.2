import axios from "../config/axiosServices";
import {
  UPLOAD_IMAGE_ALBUM_FROM_DEVICE,
  UPLOAD_IMAGE_ALBUM_FROM_HOME,
  CREATE_ALBUM,
  GET_IMAGES_BY_ALBUM,
  GET_ALBUMS,
  DELETE_ALBUM,
} from "./constants";

const uploadImageToAlbumFromDevice = (albumId, data, token) => {
  return axios.put(
    `${UPLOAD_IMAGE_ALBUM_FROM_DEVICE}?albumId=${albumId}`,
    data,
    {
      headers: { Authorization: `Bearer ${token}` },
    }
  );
};
const uploadImageToAlbumFromHome = (albumId, imageId, token) => {
  return axios.post(
    `${UPLOAD_IMAGE_ALBUM_FROM_HOME}?albumId=${albumId}&imageId=${imageId}`,
    null,
    {
      headers: { Authorization: `Bearer ${token}` },
    }
  );
};
const createAlbum = (name, desc, token) => {
  return axios.post(`${CREATE_ALBUM}?name=${name}&desc=${desc}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

const getImagesFromAlbum = (albumId, token) => {
  return axios.get(`${GET_IMAGES_BY_ALBUM}?albumId=${albumId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const getAlbums = (token) => {
  return axios.get(`${GET_ALBUMS}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const deleteAlbum = (albumId, token) => {
  return axios.delete(`${DELETE_ALBUM}?albumId=${albumId}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
export {
  uploadImageToAlbumFromDevice,
  uploadImageToAlbumFromHome,
  createAlbum,
  getImagesFromAlbum,
  getAlbums,
  deleteAlbum,
};
