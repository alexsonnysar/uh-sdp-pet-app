import React from "react";
import { Grid } from "@material-ui/core";
import PetCard from "./PetCard";

const PetCardList = ({ petList }) => (
  <Grid container justify="flex-start" data-testid="manypetcards">
    {petList.map(pet => (
      <Grid key={pet.id} item>
        <PetCard key={pet.id} pet={pet} />
      </Grid>
    ))}
  </Grid>
);

export default PetCardList;
