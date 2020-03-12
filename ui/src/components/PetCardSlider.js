import React from "react";
import { Grid } from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import PetCard from "./PetCard";

const PetCardSlider = ({ petList }) => {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      {petList.map(pet => (
        <PetCard key={pet.id} pet={pet} />
      ))}
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    display: "flex",
    overflowX: "auto"
  }
});

export default PetCardSlider;
