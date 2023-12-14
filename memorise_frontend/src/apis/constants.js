//user
const USER_REGISTER = "/authentication/signup";
const USER_LOGIN = "/authentication/check_login";
const SEND_OTP = "/authentication/send_otp";
const CHECK_OTP = "/authentication/check_otp";
const RESET_PASSWORD = "/authentication/reset_password";

//image
const UPLOAD_IMAGE = "/cloudinary/upload_image";
const UPLOAD_VIDEO = "/cloudinary/upload_video";
const GET_ALL_IMAGES_BY_USER = "/images/get_all";
export {
  USER_REGISTER,
  USER_LOGIN,
  SEND_OTP,
  CHECK_OTP,
  RESET_PASSWORD,
  UPLOAD_IMAGE,
  UPLOAD_VIDEO,
  GET_ALL_IMAGES_BY_USER,
};
