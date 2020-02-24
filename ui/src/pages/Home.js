import React from "react";
import PetCard from "../components/PetCard";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const petName = {};

const Home = () => {
  return (
    <div>
      <h1>Hello World!!</h1>
      <PetCard />
    </div>
  );
};

export default Home;
