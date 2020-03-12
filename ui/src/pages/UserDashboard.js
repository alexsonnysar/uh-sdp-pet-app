import React from "react";
import { Typography } from "@material-ui/core";
import { FetchData } from "../api/FetchData";
import PetCardSlider from "../components/PetCardSlider";
import { makeStyles } from "@material-ui/core/styles";

const UserDashboard = () => {
  const classes = useStyles();
  const url = "http://localhost:8080/pet";
  const petList = FetchData(url);

  return (
    <div className={classes.root}>
      <Typography variant="h4">Favorites</Typography>
      <PetCardSlider petList={petList} />
      <Typography variant="h4">Recently Viewed</Typography>
      <PetCardSlider petList={petList} />
      <Typography variant="h4">Adopted</Typography>
      <PetCardSlider petList={petList} />
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    alignContent: "left"
  }
});

export default UserDashboard;
