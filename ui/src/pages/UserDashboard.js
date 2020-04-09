import React, { useState, useEffect } from 'react';
import { CircularProgress } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import PetCardSlider from '../components/PetCardSlider';
import { getAllPets } from '../api/petRequests';

const useStyles = makeStyles({
  root: {
    alignContent: 'left',
  },
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const UserDashboard = () => {
  const classes = useStyles();
  const url = 'http://localhost:8080/pet';

  const [petList, setPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllPets(url)
      .then((petList) => setPetList(petList))
      .catch((error) => {
        throw error;
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      {loading ? (
        <div data-testid="loading" className={classes.progress}>
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded" className={classes.root}>
          <PetCardSlider petList={petList} heading="Favorites" />
          <PetCardSlider petList={petList} heading="Recently Viewed" />
          <PetCardSlider petList={petList} heading="Adopted" />
        </div>
      )}
    </div>
  );
};

export default UserDashboard;
