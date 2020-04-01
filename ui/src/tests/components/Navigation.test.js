import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import Navigation from "../../components/Navigation";

test("should render navigation", () => {
  const { getByTestId } = render(
    <Router>
      <Navigation />
    </Router>
  );
  const navigation = getByTestId("navbar");
  expect(navigation).toBeInTheDocument();
});
