import React from "react";
import PetCard from "../components/PetCard";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const petList = [
  {
    Name: "Garfield",
    Type: "Cat"
  },
  {
    Name: "Garfield",
    Type: "Cat"
  },
  {
    Name: "Garfield",
    Type: "Cat"
  }
];

const petName = "Garfield";
const petType = "Cat";

const Home = () => {
  return (
    <div>
      <h1>Hello World!!</h1>
      {/* <PetCard petName={petName} petType={petType} /> */}
      <div>
        <h2>Pet List</h2>
        {petList.map((pet, index) => {
          console.log("PET: ", pet);
          return <PetCard pet={pet} key={index} />;
        })}
      </div>
    </div>
  );
};

export default Home;
