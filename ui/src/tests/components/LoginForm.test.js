import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import LoginForm from "../../components/LoginForm";

test("should render login page", () => {
  const { getByTestId } = render(
    <Router>
      <LoginForm />
    </Router>
  );
  const loginForm = getByTestId("loginForm");
  expect(loginForm).toBeInTheDocument();
});
