import React from "react";
import PetCardList from "../components/PetCardList";
import { Grid, Typography } from "@material-ui/core";
import { FetchData } from "../api/FetchData";
import { makeStyles } from "@material-ui/core/styles";
import PetCardSlider from "../components/PetCardSlider";

const UserDashboard = () => {
  const url = "http://localhost:8080/pet";
  const petList = FetchData(url);

  const classes = useStyles();

  return (
    <div>
      {/* <Grid container >
        <Grid item xs={12} md={12}>
          <PetCardList petList={petList} />
        </Grid>
      </Grid>
      <Grid container >
        <Grid item xs={12} md={12}>
          <PetCardList petList={petList} />
        </Grid>
      </Grid>
      <Grid container >
        <Grid item xs={12} md={12}>
          <PetCardList petList={petList} />
        </Grid>
      </Grid> */}
      <Typography gutterBottom variant="h5" component="h2">
        Favorites
      </Typography>
      <PetCardSlider petList={petList} />
      <Typography gutterBottom variant="h5" component="h2">
        Recently Viewed
      </Typography>
      <PetCardSlider petList={petList} />
      <Typography gutterBottom variant="h5" component="h2">
        Adopted
      </Typography>
      <PetCardSlider petList={petList} />
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    overflowX: "scroll"
  }
});

export default UserDashboard;
