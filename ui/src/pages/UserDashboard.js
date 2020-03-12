import React from "react";
import PetCardList from "../components/PetCardList";
import { Grid } from "@material-ui/core";
import { FetchData } from "../api/FetchData";
import { makeStyles } from "@material-ui/core/styles";

const UserDashboard = () => {
  const url = "http://localhost:8080/pet";
  const petList = FetchData(url);

  const classes = useStyles();

  return (
    <div className={classes.root}>
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
      <div>
        <PetCardList petList={petList} />
      </div>
      <div>
        <PetCardList petList={petList} />
      </div>
      <div>
        <PetCardList petList={petList} />
      </div>
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    overflowX: "auto"
  }
});

export default UserDashboard;
