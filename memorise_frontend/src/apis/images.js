import axios from "../config/axiosServices";
import {
  UPLOAD_IMAGE,
  UPLOAD_VIDEO,
  GET_ALL_IMAGES_BY_USER,
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
export { uploadImage, uploadVideo, getAllImageByUser };
