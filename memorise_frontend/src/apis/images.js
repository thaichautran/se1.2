import axios from "../config/axiosServices";
import { UPLOAD_IMAGE, UPLOAD_VIDEO } from "./constants";

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
export { uploadImage, uploadVideo };
