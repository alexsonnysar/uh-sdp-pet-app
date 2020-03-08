import React from "react";
import PetCard from "./PetCard";
import { Grid, Container } from "@material-ui/core";

const ManyPetCards = ({ petList }) => {
  return (
    <Container maxWidth="lg">
      <Grid
        container
        justify="flex-start"
        spacing={2}
        data-testid="manypetcards"
      >
        {petList.map(pet => (
          <Grid key={pet.id} item xs={2}>
            <PetCard key={pet.id} pet={pet} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default ManyPetCards;
