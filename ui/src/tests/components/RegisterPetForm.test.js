import React from "react";
import { render } from "@testing-library/react";
import RegisterPetForm from "../../components/RegisterPetForm";

test("should render register pet page", () => {
  const { getByTestId } = render(<RegisterPetForm />);
  const registerPetForm = getByTestId("registerPetForm");
  expect(registerPetForm).toBeInTheDocument();
});
