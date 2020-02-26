import React from "react";
import PetCard from "../components/PetCard";
import PetList from "../components/PetList";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const Home = () => {
  return (
    <div>
      <h1>This is the User Home Page</h1>
      <PetCard pet={petProfileInfo} />
      {/* <PetCard petName={petName} petType={petType} /> */}
    </div>
  );
};

export default Home;
