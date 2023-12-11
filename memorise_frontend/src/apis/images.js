import axios from "../config/axiosServices";
import { UPLOAD_IMAGE } from "./constants";

const uploadImage = (data, token) => {
  return axios.post(`${UPLOAD_IMAGE}`, data, {
    headers: { Authorization: `Bearer ${token}` },
  });
};
export { uploadImage };
