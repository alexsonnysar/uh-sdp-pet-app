import React from "react";
import { render } from "@testing-library/react";
import Profile from "../../components/Profile";

test("should render profile page", () => {
  const { getByTestId } = render(<Profile />);
  const profile = getByTestId("profile");
  expect(profile).toBeInTheDocument();
});
