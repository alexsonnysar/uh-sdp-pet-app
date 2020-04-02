import React from "react";
import { render, fireEvent } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import LoginForm from "../../components/LoginForm";

describe("<LoginForm />", () => {
  test("should render login page", () => {
    const { getByTestId } = render(
      <Router>
        <LoginForm />
      </Router>
    );
    const loginForm = getByTestId("loginForm");
    expect(loginForm).toBeInTheDocument();
  });

  const setup = () => {
    const utils = render(
      <Router>
        <LoginForm />
      </Router>
    );
    const input = utils.getByLabelText("Email");
    return {
      input,
      ...utils
    };
  };

  test("handleChange should change formData", () => {
    const { input } = setup();
    fireEvent.change(input, { target: { value: "hello@123.com" } });

    expect(input.value).toBe("hello@123.com");
  });
});
