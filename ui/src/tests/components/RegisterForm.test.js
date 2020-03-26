import React from "react";
import { render } from "@testing-library/react";
import RegisterForm from "../../components/RegisterForm";

test("should render register page", () => {
  const { getByTestId } = render(<RegisterForm />);
  const registerForm = getByTestId("registerForm");
  expect(registerForm).toBeInTheDocument();
});
