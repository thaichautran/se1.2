import axios from "../config/axiosServices";
import { USER_REGISTER, USER_LOGIN } from "./constants";

const register = (requestBody) => {
  return axios.post(`${USER_REGISTER}`, requestBody);
};
const login = (requestBody) => {
  return axios.post(`${USER_LOGIN}`, requestBody);
};

export { register, login };
