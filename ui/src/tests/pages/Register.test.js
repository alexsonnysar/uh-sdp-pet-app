import React from "react";
import { render } from "@testing-library/react";
import Register from "../../pages/Register";

test("should render register pet page", () => {
  const { getByTestId } = render(<Register />);
  const register = getByTestId("register");
  expect(register).toBeInTheDocument();
});
