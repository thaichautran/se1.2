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
const FAVOURITE_IMAGE = "/images/favourite";
const GET_FAVORITE_IMAGE = "/images/get_favourite";
const DOWNLOAD_IMAGE = "/images/download";
const REMOVE_TO_TRASH = "/images/trash";
const GET_TRASH_IMAGE = "/images/get_trash";
const REMOVE_ALL = "/images/delete_all";
const RESTORE_ALL = "/images/restore_all";
const UPDATE_IMAGE = "/images/update_image";

//album
const UPLOAD_IMAGE_ALBUM_FROM_DEVICE = "/albums/upload/device";
const UPLOAD_IMAGE_ALBUM_FROM_HOME = "/albums/upload/home";
const CREATE_ALBUM = "/albums/create_album";
const GET_IMAGES_BY_ALBUM = "/albums/get_images";
const GET_ALBUMS = "/albums/get_albums";
const DELETE_ALBUM = "/albums/delete_album";

export {
  USER_REGISTER,
  USER_LOGIN,
  SEND_OTP,
  CHECK_OTP,
  RESET_PASSWORD,
  UPLOAD_IMAGE,
  UPLOAD_VIDEO,
  GET_ALL_IMAGES_BY_USER,
  FAVOURITE_IMAGE,
  GET_FAVORITE_IMAGE,
  DOWNLOAD_IMAGE,
  UPLOAD_IMAGE_ALBUM_FROM_DEVICE,
  UPLOAD_IMAGE_ALBUM_FROM_HOME,
  CREATE_ALBUM,
  GET_IMAGES_BY_ALBUM,
  GET_ALBUMS,
  DELETE_ALBUM,
  REMOVE_TO_TRASH,
  GET_TRASH_IMAGE,
  REMOVE_ALL,
  RESTORE_ALL,
  UPDATE_IMAGE,
};
