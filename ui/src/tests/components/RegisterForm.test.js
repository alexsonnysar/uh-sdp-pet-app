import React from "react";
import { render } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import RegisterForm from "../../components/RegisterForm";

test("should render register page", () => {
  const { getByTestId } = render(
    <Router>
      <RegisterForm />
    </Router>
  );
  const registerForm = getByTestId("registerForm");
  expect(registerForm).toBeInTheDocument();
});
