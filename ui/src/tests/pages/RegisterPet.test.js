import React from "react";
import { render } from "@testing-library/react";
import RegisterPet from "../../pages/RegisterPet";

test("should render register pet page", () => {
  const { getByTestId } = render(<RegisterPet />);
  const registerPet = getByTestId("registerPet");
  expect(registerPet).toBeInTheDocument();
});
