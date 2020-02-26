import React from "react";
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

const EmployeeDashboard = () => {
  return (
    <div>
      <h1>This is the Employee Dashboard Page</h1>
      <PetList heading="Pet List" petList={petList} />
    </div>
  );
};

export default EmployeeDashboard;
