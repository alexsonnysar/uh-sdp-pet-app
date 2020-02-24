import React from "react";
import PetCard from "../components/PetCard";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const petName = "Garfield";
const petType = "Cat";

const Home = () => {
  return (
    <div>
      <h1>Hello World!!</h1>
      <PetCard petName={petName} petType={petType} />
    </div>
  );
};

export default Home;
