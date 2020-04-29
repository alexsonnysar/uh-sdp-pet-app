import React, { useState, useEffect } from 'react';
import Grid from '@material-ui/core/Grid';
import { Button, CircularProgress, makeStyles } from '@material-ui/core';
import AddRoundedIcon from '@material-ui/icons/AddRounded';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { getAllPets, getAllRequestedPets } from '../api/petRequests';
import PetList from '../components/PetList';
import RequestList from '../components/RequestList';

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
  const auth = `Bearer ${localStorage.getItem('jwt')}`;

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: auth,
  };
  const petsUrl = 'http://localhost:8080/pet';
  const requestedPetsUrl = 'http://localhost:8080/request/request-info';

  const [petList, setPetList] = useState([]);
  const [requestList, setRequestList] = useState([]);
  const [loading, setLoading] = useState(true);

  const classes = useStyles();

  const handleError = () => {};

  const axiosRequest = () => {
    axios
      .all([
        getAllRequestedPets(requestedPetsUrl, reqHeaders),
        getAllPets(petsUrl, reqHeaders),
      ])
      .then(
        axios.spread((allRequestedRes, allPetsRes) => {
          setRequestList(allRequestedRes.data.filter((r) => r.status === 'PENDING'));
          setPetList(allPetsRes.data.filter((r) => r.adopted === false));
        })
      )
      .catch(handleError)
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    axiosRequest();
  }, [loading]);
  const removePetFromList = (id) => {
    setPetList(petList.filter((el) => el.id !== id));
    setRequestList(requestList.filter((el) => el.petId !== id));
  };

  const updateRequestFromList = (id, petId, response) => {
    setRequestList(requestList.filter((el) => el.id !== id));
    if (response === 'APPROVED') {
      setRequestList(requestList.filter((el) => el.petId !== petId));
      setPetList(petList.filter((el) => el.id !== petId));
    }
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
              <Link to="/employee/pet-register" className={classes.addButton}>
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
              <RequestList
                putRequest={updateRequestFromList}
                heading="Requested for Adoption"
                requestList={requestList}
              />
            </Grid>
            <Grid item xs={12} sm>
              <PetList
                deletePet={removePetFromList}
                heading="Adoptable Animals"
                petList={petList}
              />
            </Grid>
          </Grid>
        </div>
      )}
    </div>
  );
};

export default EmployeeDashboard;
