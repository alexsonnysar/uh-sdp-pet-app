import React from "react";
import PetList from "../components/PetList";
import Grid from "@material-ui/core/Grid";
import { FetchData } from "../api/FetchData";
import { Button } from "@material-ui/core";
import AddRoundedIcon from "@material-ui/icons/AddRounded";

const EmployeeDashboard = () => {
  const url = "http://localhost:8080/pet";
  const petList = FetchData(url);

  return (
    <div data-testid="empdash">
      <h1 align="center">Employee Dashboard</h1>
      {petList.length > 0 ? (
        <div data-testid="loadedList">
          <Grid container spacing={3}>
            <Grid item xs={12} sm></Grid>
            <Grid item xs={12} sm>
              <Button
                href="pet-register"
                variant="contained"
                color="primary"
                startIcon={<AddRoundedIcon />}
              >
                Add Pet
              </Button>
            </Grid>
          </Grid>
          <Grid container spacing={3}>
            <Grid item xs={12} sm>
              <PetList heading="Requested for Adoption" petList={petList} />
            </Grid>
            <Grid item xs={12} sm>
              <PetList heading="Adoptable Animals" petList={petList} />
            </Grid>
          </Grid>
        </div>
      ) : (
        <div data-testid="loading">No List of Pets to Show :(</div>
      )}
    </div>
  );
};

export default EmployeeDashboard;
