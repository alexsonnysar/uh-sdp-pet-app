import React, { useState, useEffect } from 'react';
import Grid from '@material-ui/core/Grid';
import { Button, CircularProgress, makeStyles } from '@material-ui/core';
import AddRoundedIcon from '@material-ui/icons/AddRounded';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { getAllPets, getAllRequestedPets } from '../api/petRequests';
import PetList from '../components/PetList';

const useStyles = makeStyles({
  addButton: {
    textDecorationLine: 'none',
  },
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const EmployeeDashboard = () => {
  const petsUrl = 'http://localhost:8080/pet';
  const requestedPetsUrl = 'http://localhost:8080/request/';

  const [petList, setPetList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [loading, setLoading] = useState(true);

  const classes = useStyles();

  const handleError = () => {};

  useEffect(() => {
    axios
      .all([getAllRequestedPets(requestedPetsUrl), getAllPets(petsUrl)])
      .then(
        axios.spread((allRequestedRes, allPetsRes) => {
          setRequestList(allRequestedRes.data);
          setPetList(allPetsRes.data);
        })
      )
      .catch(handleError)
      .finally(() => setLoading(false));
  }, []);

  const removePetFromList = (id) => {
    setPetList(petList.filter((el) => el.id !== id));
  };

  const approvePetFromList = (id) => {
    setPetList(petList.filter((el) => el.id !== id));
  };

  return (
    <div data-testid="empdash">
      <h1 align="center">Employee Dashboard</h1>
      {loading ? (
        <div data-testid="loading" className={classes.progress}>
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div align="center" data-testid="loadedList">
          <Grid container>
            <Grid item xs={12} sm>
              <Link to="/pet-register" className={classes.addButton}>
                <Button
                  variant="contained"
                  color="primary"
                  startIcon={<AddRoundedIcon />}
                  className={classes.addButton}
                >
                  Add Pet
                </Button>
              </Link>
            </Grid>
          </Grid>
          <Grid container>
            <Grid item xs={12} sm>
              <PetList
                action={approvePetFromList}
                heading="Requested for Adoption"
                petList={requestList}
                approveButton
                rejectButton
              />
            </Grid>
            <Grid item xs={12} sm>
              <PetList
                action={removePetFromList}
                heading="Adoptable Animals"
                petList={petList}
                deleteButton
                updateButton
              />
            </Grid>
          </Grid>
        </div>
      )}
    </div>
  );
};

export default EmployeeDashboard;
