import axios from "../config/axiosServices";
import {
  UPLOAD_IMAGE,
  UPLOAD_VIDEO,
  GET_ALL_IMAGES_BY_USER,
  FAVOURITE_IMAGE,
  GET_FAVORITE_IMAGE,
  DOWNLOAD_IMAGE,
  REMOVE_TO_TRASH,
  GET_TRASH_IMAGE,
  REMOVE_ALL,
  RESTORE_ALL,
  UPDATE_IMAGE,
  FIND_IMAGES,
  DELETE_IMAGE,
} from "./constants";

const uploadImage = (data, token) => {
  return axios.post(`${UPLOAD_IMAGE}`, data, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const uploadVideo = (data, token) => {
  return axios.post(`${UPLOAD_VIDEO}`, data, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

const getAllImageByUser = (token) => {
  return axios.get(`${GET_ALL_IMAGES_BY_USER}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const getFavouriteImage = (token) => {
  return axios.get(`${GET_FAVORITE_IMAGE}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

const favouriteImage = (imageId, status, token) => {
  return axios.put(`${FAVOURITE_IMAGE}?id=${imageId}&status=${status}`, null, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const removeImageToTrash = (imageId, status, token) => {
  return axios.put(`${REMOVE_TO_TRASH}?id=${imageId}&status=${status}`, null, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

const getTrashImage = (token) => {
  return axios.get(`${GET_TRASH_IMAGE}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const downloadImage = (url, token) => {
  return axios.get(`${DOWNLOAD_IMAGE}?url=${url}`, {
    headers: { Authorization: `Bearer ${token}` },
    responseType: "blob",
  });
};

const removeAllImageFromTrash = (token) => {
  return axios.delete(`${REMOVE_ALL}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};

const restoreAllImageFromTrash = (token) => {
  return axios.put(`${RESTORE_ALL}`, null, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const updateImage = (requestBody, token) => {
  return axios.put(`${UPDATE_IMAGE}`, requestBody, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const findImages = (information, token) => {
  return axios.get(`${FIND_IMAGES}?information=${information}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
const deleteImage = (id, token) => {
  return axios.delete(`${DELETE_IMAGE}?id=${id}`, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
export {
  deleteImage,
  updateImage,
  uploadImage,
  uploadVideo,
  getAllImageByUser,
  favouriteImage,
  getFavouriteImage,
  downloadImage,
  removeImageToTrash,
  getTrashImage,
  removeAllImageFromTrash,
  restoreAllImageFromTrash,
  findImages,
};
