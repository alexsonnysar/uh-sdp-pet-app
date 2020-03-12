import React from "react";
import PetCard from "./PetCard";
import { Grid, Container } from "@material-ui/core";

const PetCardList = ({ petList }) => {
  return (
    <Grid
      container
      justify="flex-start"
      spacing={2}
      data-testid="manypetcards"
      xs={12}
    >
      {petList.map(pet => (
        <Grid key={pet.id} item>
          <PetCard key={pet.id} pet={pet} />
        </Grid>
      ))}
    </Grid>
  );
};

export default PetCardList;
