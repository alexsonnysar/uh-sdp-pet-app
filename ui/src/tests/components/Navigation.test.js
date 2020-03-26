import React from "react";
import { render } from "@testing-library/react";
import Navigation from "../../components/Navigation";

test("should render navigation", () => {
  const { getByTestId } = render(<Navigation />);
  const navigation = getByTestId("navbar");
  expect(navigation).toBeInTheDocument();
});
