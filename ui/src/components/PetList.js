import React from "react";
import PetCard from "./PetCard";
import CardColumns from "react-bootstrap/CardColumns";

const PetList = ({ heading, petList }) => {
  return (
    <div data-testid="petlist">
      <h2>{heading}</h2>
      {petList.map((pet, index) => (
        <CardColumns>
          <PetCard pet={pet} key={index} ifList={true} />
        </CardColumns>
      ))}
    </div>
  );
};

export default PetList;
