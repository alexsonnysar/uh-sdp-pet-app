import React from "react";
import { Router } from "react-router-dom";
import { createMemoryHistory } from "history";
import { render, fireEvent } from "@testing-library/react";
import Navigation from "../../components/Navigation";

describe("<Navigation />", () => {
  test("should render navigation", () => {
  const history =createMemoryHistory();
    const { getByTestId } = render(
      <Router history={history}>
        <Navigation handleAuth={true}/>
      </Router>
    );
    const navigation = getByTestId("navbar");
    expect(navigation).toBeInTheDocument();
  });

  // test("logout should clear localStorage, redirect to homepage", () => {
  //   const history = createMemoryHistory();
  //   localStorage.setItem("jwt", "mockJwt");

  //   const { getByTestId } = render(
  //     <Router history={history}>
  //       <Navigation handleAuth={false} />
  //     </Router>
  //   );

  //   const logout = getByTestId("logout");
  //   fireEvent.click(logout);
  //   expect(localStorage.getItem("jwt")).toEqual(null);
  // });
});
