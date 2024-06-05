import { APIClient, setAuthorization,getLoggedinUser } from "./apiCore";
import * as url from "./urls";

const api = new APIClient();

const getProfileDetails = () => {
  const accessToken = getLoggedinUser().accessToken;
  console.log(accessToken) // jwt
  setAuthorization(accessToken)
  return api.get(url.GET_MY_PROFILE);
};

const getSettings = () => {
  return api.get(url.GET_USER_SETTINGS);
};
const updateSettings = (field: string, value: any) => {
  return api.update(url.UPDATE_ETTINGS, {
    field: field,
    value: value,
  });
};

export { getProfileDetails, getSettings, updateSettings };
