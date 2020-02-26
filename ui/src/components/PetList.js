import React from "react";
import PetListCard from "./PetListCard";
import CardColumns from "react-bootstrap/CardColumns";

const PetList = ({ heading, petList }) => {
  return (
    <div data-testid="petlist">
      <h2>{heading}</h2>
      {petList.map((pet, index) => (
        <CardColumns key={index}>
          <PetListCard pet={pet} key={index} />
        </CardColumns>
      ))}
    </div>
  );
};

export default PetList;
