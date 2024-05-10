import React, { useEffect, useState } from "react";

// hooks
import { useRedux } from "../../../hooks/index";
import { createSelector } from "reselect";
// components
import Loader from "./Loader";
import AppSimpleBar from "./AppSimpleBar";
import LeftbarTitle from "./LeftbarTitle";
import Call from "./Call";

// actions
import { getCalls } from "./actions";

// interface
import { CallItem } from "./calls";

interface IndexProps {}
const Index = (props: IndexProps) => {
  // global store
  const { dispatch, useAppSelector } = useRedux();

  // const { calls, getCallsLoading } = useAppSelector((state: any) => ({
  //   calls: state.Calls.calls,
  //   getCallsLoading: state.Calls.getCallsLoading,
  // }));

  const errorData = createSelector(
    (state : any) => state.Calls,
    (state) => ({
      calls: state.calls,
      getCallsLoading: state.getCallsLoading,
    })
  );
  // Inside your component
  const { calls,getCallsLoading} = useAppSelector(errorData);



  // get user calls
  useEffect(() => {
    dispatch(getCalls());
  }, [dispatch]);

  const [callsList, setCallsList] = useState([]);

  useEffect(() => {
    setCallsList(calls);
  }, [calls]);

  return (
    <div className="position-relative">
      {getCallsLoading && <Loader />}
      <LeftbarTitle title="Calls" />
      {/* end p-4 */}

      {/* Start contact lists */}
      <AppSimpleBar className="chat-message-list chat-call-list">
        <ul className="list-unstyled chat-list">
          {(callsList || []).map((call: CallItem, key: number) => (
            <Call call={call} key={key} />
          ))}
        </ul>
      </AppSimpleBar>
      {/* end contact lists */}
    </div>
  );
};

export default Index;
