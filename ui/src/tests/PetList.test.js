import React from "react";
import { render } from "@testing-library/react";
import PetList from "../components/PetList";

const petList = [
  {
    Name: "Garfield",
    Type: "Cat"
  },
  {
    Name: "Shawn",
    Type: "Bird"
  },
  {
    Name: "Alex",
    Type: "Dog"
  }
];

test("should render pet list", () => {
  const { getByTestId } = render(
    <PetList heading="Pet List" petList={petList} />
  );
  const petCard = getByTestId("petlist");
  expect(petCard).toBeInTheDocument();
});
