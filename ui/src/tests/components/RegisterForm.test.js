import React from "react";
import { render, fireEvent } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import RegisterForm from "../../components/RegisterForm";

describe("<RegisterForm", () => {
  const setup = () => {
    const utils = render(
      <Router>
        <RegisterForm />
      </Router>
    );
    const input = utils.getByLabelText("Name");
    return {
      input,
      ...utils
    };
  };

  test("handleChange should change formData", () => {
    const { input } = setup();
    fireEvent.change(input, { target: { value: "Roger" } });

    expect(input.value).toBe("Roger");
  });

  test("should render register page", () => {
    const { getByTestId } = render(
      <Router>
        <RegisterForm />
      </Router>
    );
    const registerForm = getByTestId("registerForm");
    expect(registerForm).toBeInTheDocument();
  });
});

test("click on register button", () => {
  const { getByTestId } = render(
    <Router>
      <RegisterForm />
    </Router>
  );

  const submit = getByTestId("submit");
  fireEvent.click(submit);
  expect(localStorage.getItem("jwt")).toEqual(null);
});
