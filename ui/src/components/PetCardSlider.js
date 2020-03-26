import React from "react";
import PetCard from "./PetCard";
import "./PetCardSlider.css";
import { Typography } from "@material-ui/core";

const PetCardSlider = ({ petList, heading }) => {
  return (
    <div data-testid="petCardSlider">
      <div>
        <Typography variant="h6">{heading}</Typography>
      </div>
      <div className="root">
        {petList.map(pet => (
          <PetCard key={pet.id} pet={pet} />
        ))}
      </div>
    </div>
  );
};

export default PetCardSlider;
