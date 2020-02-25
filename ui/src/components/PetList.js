import React from "react";
import PetCard from "./PetCard";

const PetList = ({ heading, petList }) => {
  return (
    <div data-testid="petlist">
      <h2>{heading}</h2>
      {petList.map((pet, index) => (
        <PetCard pet={pet} key={index} />
      ))}
      ;
    </div>
  );
};

export default PetList;
