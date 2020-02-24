import React from "react";
import { render } from "@testing-library/react";
import PetCard from "../components/PetCard";

test("should render pet card", () => {
  const { getByTestId } = render(<PetCard />);
  const petCard = getByTestId("petcard");
  expect(petCard).toBeInTheDocument();
});
