import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import PetCardList from '../components/PetCardList';
import { getAllPets } from '../api/petRequests';

const useStyles = makeStyles({
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  }
});

const Home = () => {
  const url = 'http://localhost:8080/pet';

  const [petList, setPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getAllPets(url)
      .then(petList => setPetList(petList))
      .catch(error => console.log(error))
      .finally(() => setLoading(false));
  }, []);

  const classes = useStyles();
  return (
    <div>
      {loading ? (
        <div className={classes.progress} data-testid="loading">
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded">
          <PetCardList petList={petList} />
        </div>
      )}
    </div>
  );
};

export default Home;
