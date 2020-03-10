import React, { useState, useEffect } from "react";
import PetList from "../components/PetList";
import Grid from "@material-ui/core/Grid";
import axios from "axios";

const EmployeeDashboard = () => {
  const [petList, setPetList] = useState([]);
  const url = "http://localhost:8080/pet";

  async function fetchData() {
    await axios
      .get(url)
      .then(res => setPetList(res.data))
      .catch(err => {
        console.log(err);
      });
  }

  useEffect(() => {
    fetchData();
  }, [url]);

  return (
    <div data-testid="empdash">
      <h1 align="center">This is the Employee Dashboard Page</h1>
      {petList.length > 0 ? (
        <div data-testid="loadedList">
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
