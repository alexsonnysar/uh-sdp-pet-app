import React from "react";
import PetCard from "./PetCard";
import { Grid } from "@material-ui/core";

const ManyPetCards = ({ petList }) => {
  return (
    <Grid container justify="flex-start" spacing={2}>
      {petList.map(pet => (
        <Grid key={pet.id} item>
          <PetCard key={pet.id} pet={pet} />
        </Grid>
      ))}
    </Grid>
  );
};

export default ManyPetCards;
