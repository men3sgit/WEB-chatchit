import { combineReducers } from "redux";

import ForgetPassword from "./reducer";
import Login from "./reducer";
import Register from "./reducer";
import Layout from "./reducer";
import Profile from "./reducer";
import Contacts from "./reducer";
import Calls from "./reducer";
import Bookmarks from "./reducer";
import Settings from "./reducer";
import Chats from "./reducer";

export default combineReducers({
  ForgetPassword,
  Login,
  Register,
  Layout,
  Profile,
  Contacts,
  Calls,
  Bookmarks,
  Settings,
  Chats,
});
