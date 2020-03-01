import React from "react";
import PetCard from "./PetCard";
import { CardDeck, CardColumns } from "react-bootstrap";
import { Grid } from "@material-ui/core";

const ManyPetCards = ({ petList }) => {
  return (
    <div>
      <Grid container justify="flex-start" alignItems="flex-start" spacing={2}>
        {petList.map(pet => (
          <PetCard key={pet.id} pet={pet} />
        ))}
      </Grid>
    </div>
  );
};

export default ManyPetCards;
