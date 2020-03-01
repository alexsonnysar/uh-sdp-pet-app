import React from "react";
import PetList from "../components/PetList";
import Navigation from "../components/Navigation";

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

const EmployeeDashboard = () => {
  return (
    <div data-testid="empdash">
      <Navigation />
      <PetList heading="Pet List" petList={petList} />
    </div>
  );
};

export default EmployeeDashboard;
