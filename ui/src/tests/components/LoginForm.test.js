import React from "react";
import { render } from "@testing-library/react";
import LoginForm from "../../components/LoginForm";

test("should render login page", () => {
  const { getByTestId } = render(<LoginForm />);
  const loginForm = getByTestId("loginForm");
  expect(loginForm).toBeInTheDocument();
});
