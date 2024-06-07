import { APIClient } from "./apiCore";
import * as url from "./urls";
import {INVITE_CONTACT_2,GET_CONTACTS_2, SEARCH_CONTACTS_2} from "./urls";

const api = new APIClient();

const getContacts = (filters?: object) => {
 const rs =  api.get(url.GET_CONTACTS_2, filters)
  console.log(rs)
  return rs;
};

const searchContacts = (filters?: object) => {
  return api.get(url.SEARCH_CONTACTS_2, filters);
};

const inviteContact = (data: object) => {
  // return api.create(url.INVITE_CONTACT, data);
  const rs = api.create(url.INVITE_CONTACT_2, data);
  console.log(rs)
  return rs;
};
export { getContacts,searchContacts, inviteContact };
