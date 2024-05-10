import { all } from "redux-saga/effects";

//auth
import registerSaga from "./saga";
import loginSaga from "./saga";
import forgetPasswordSaga from "./saga";
import profileSaga from "./saga";
import LayoutSaga from "./saga";
import contactsSaga from "./saga";
import callsSaga from "./saga";
import bookmarksSaga from "./saga";
import settingsSaga from "./saga";
import chatsSaga from "./saga";

export default function* rootSaga() {
  yield all([
    registerSaga(),
    loginSaga(),
    forgetPasswordSaga(),
    profileSaga(),
    LayoutSaga(),
    contactsSaga(),
    callsSaga(),
    bookmarksSaga(),
    settingsSaga(),
    chatsSaga(),
  ]);
}
