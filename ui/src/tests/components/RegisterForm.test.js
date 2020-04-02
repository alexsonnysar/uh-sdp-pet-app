import React from "react";
import { render, fireEvent } from "@testing-library/react";
import Adapter from "enzyme-adapter-react-16";
import Enzyme from "enzyme";
import { BrowserRouter as Router } from "react-router-dom";
import RegisterForm from "../../components/RegisterForm";

Enzyme.configure({ adapter: new Adapter() });

const formDataCorrect = {
  name: "mockName",
  password: "password",
  passwordConfirm: "password"
};

const formDataInvalid = {
  name: "mockName",
  password: "password",
  passwordConfirm: "paswrd"
};

describe("<RegisterForm", () => {
  let wrapper;

  const setState = jest.fn();
  const useStateSpy = jest.spyOn(React, "useState");
  useStateSpy.mockImplementation(init => [init, setState]);

  beforeEach(() => {
    wrapper = Enzyme.mount(
      Enzyme.shallow(
        <Router>
          <RegisterForm />
        </Router>
      ).get(0)
    );
  });

  afterEach(() => {
    jest.clearAllMocks();
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
