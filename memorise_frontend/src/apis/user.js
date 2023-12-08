import axios from "../config/axiosServices";
import {
  USER_REGISTER,
  USER_LOGIN,
  SEND_OTP,
  CHECK_OTP,
  RESET_PASSWORD,
} from "./constants";

const register = (requestBody) => {
  return axios.post(`${USER_REGISTER}`, requestBody);
};
const login = (requestBody) => {
  return axios.post(`${USER_LOGIN}`, requestBody);
};
const sendOTP = (username) => {
  return axios.post(`${SEND_OTP}?username=${username}`);
};
const checkOTP = (otp) => {
  return axios.get(`${CHECK_OTP}?otp=${otp}`);
};

const resetPassword = (otp, newpassword) => {
  return axios.post(`${RESET_PASSWORD}?otp=${otp}&newPassword=${newpassword}`);
};
export { register, login, sendOTP, checkOTP, resetPassword };
