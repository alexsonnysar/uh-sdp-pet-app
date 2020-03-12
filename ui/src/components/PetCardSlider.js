import React from "react";
import { Grid } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import PetCard from "./PetCard";
import "./PetCardSlider.css";

const PetCardSlider = ({ petList }) => {
  return (
    <div class="root">
      {petList.map(pet => (
        <PetCard key={pet.id} pet={pet} />
      ))}
    </div>
  );
};

export default PetCardSlider;
