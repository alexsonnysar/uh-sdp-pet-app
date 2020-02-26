import React from "react";
import PetCard from "../components/PetCard";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const Home = () => {
  return (
    <div>
      <h1 align="center">This is the User Home Page</h1>
      <PetCard pet={petProfileInfo} />
    </div>
  );
};

export default Home;
