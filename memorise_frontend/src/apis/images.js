import UPLOAD_IMAGE from "./constants";

const uploadImage = (data) => {
  return axios.post(`${UPLOAD_IMAGE}`, data);
};
export { uploadImage };
