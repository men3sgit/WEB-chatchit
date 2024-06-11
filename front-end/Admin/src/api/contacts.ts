import { APIClient } from "./apiCore";
import * as url from "./urls";
import {INVITE_CONTACT_2} from "./urls";

const api = new APIClient();

const getContacts = (filters?: object) => {
  const s = api.get(url.GET_CONTACTS_2, filters);
  console.log(s)
  return s;
};

const inviteContact = (data: object) => {
  // return api.create(url.INVITE_CONTACT, data);
  const rs = api.create(url.INVITE_CONTACT_2, data);
  console.log(rs)
  return rs;
};
export { getContacts, inviteContact };
