import React, { useEffect } from "react";

// hooks
import { useRedux } from "../../../hooks/index";
import { createSelector } from "reselect";
// components
import Loader from "./Loader";
import AppSimpleBar from "./AppSimpleBar";
import MyProfile from "./MyProfile";
import UserDescription from "./UserDescription";
import Media from "./Media";
import AttachedFiles from "./AttachedFiles";

// actions
import { getProfileDetails } from "./actions";

interface IndexProps {}
const Index = (props: IndexProps) => {
  // global store
  const { dispatch, useAppSelector } = useRedux();


  const errorData = createSelector(
    (state : any) => state.Profile,
    (state) => ({
      profileDetails: state.profileDetails,
      getProfileLoading: state.getProfileLoading,
      isProfileFetched: state.isProfileFetched,
    })
  );
  // Inside your component
  const { profileDetails,getProfileLoading,isProfileFetched } = useAppSelector(errorData);

  // get user profile details
  useEffect(() => {
    dispatch(getProfileDetails());
  }, [dispatch]);

  return (
    <div className="position-relative">
      {getProfileLoading && !isProfileFetched && <Loader />}
      <MyProfile basicDetails={profileDetails.basicDetails} />

      <AppSimpleBar className="p-4 profile-desc">
        <UserDescription basicDetails={profileDetails.basicDetails} />
        <hr className="my-4" />

        <Media media={profileDetails.media} limit={2} />

        <hr className="my-4" />

        <AttachedFiles attachedFiles={profileDetails.attachedFiles} />
      </AppSimpleBar>
    </div>
  );
};

export default Index;
