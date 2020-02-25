import React from "react";
import PetCard from "../components/PetCard";
import PetList from "../components/PetList";

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
    Name: "Shawn",
    Type: "Bird"
  },
  {
    Name: "Alex",
    Type: "Dog"
  }
];

const Home = () => {
  return (
    <div>
      <h1>Hello World!!</h1>
      {/* <PetCard petName={petName} petType={petType} /> */}
      <PetList heading="We did it" petList={petList} />
    </div>
  );
};

export default Home;
