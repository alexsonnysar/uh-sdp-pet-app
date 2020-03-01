import React from "react";
import PetCard from "../components/PetCard";
import Navigation from "../components/Navigation";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const Home = () => {
  return (
    <div>
      <Navigation />
      <PetCard pet={petProfileInfo} />
    </div>
  );
};

export default Home;
