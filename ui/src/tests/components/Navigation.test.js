import React from "react";
import { render, fireEvent } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import Navigation from "../../components/Navigation";

describe("<Navigation />", () => {
  test("should render navigation", () => {
    const { getByTestId } = render(
      <Router>
        <Navigation />
      </Router>
    );
    const navigation = getByTestId("navbar");
    expect(navigation).toBeInTheDocument();
  });

  test("logout should clear localStorage, redirect to homepage", () => {
    localStorage.setItem("jwt", "mockJwt");

    const { getByTestId } = render(
      <Router>
        <Navigation />
      </Router>
    );

    const logout = getByTestId("logout");
    fireEvent.click(logout);
    expect(localStorage.getItem("jwt")).toEqual(null);
  });
});
