import React, { useState, useEffect } from "react";
import PetList from "../components/PetList";
import Grid from "@material-ui/core/Grid";
import { fetchData } from "../api/fetchData";
import { Button } from "@material-ui/core";
import AddRoundedIcon from "@material-ui/icons/AddRounded";

const EmployeeDashboard = () => {
  const url = "http://localhost:8080/pet";

  const [petList, setPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData(url)
      .then(petList => setPetList(petList))
      .catch(error => console.log(error))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div data-testid="empdash">
      <h1 align="center">Employee Dashboard</h1>
      {loading ? (
        <div data-testid="loading">No List of Pets to Show :(</div>
      ) : (
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
      )}
    </div>
  );
};

export default EmployeeDashboard;
