import React from "react";
import { render } from "@testing-library/react";
import PetListCard from "../components/PetListCard";

const pet = {
  Name: "Garfield",
  Type: "Cat"
};

test("should render pet list card", () => {
  const { getByTestId } = render(<PetListCard pet={pet} />);
  const petListCard = getByTestId("petlistcard");
  expect(petListCard).toBeInTheDocument();
});
