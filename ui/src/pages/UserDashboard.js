import React, { useState, useEffect } from 'react';
import { CircularProgress } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import PetCardSlider from '../components/PetCardSlider';
import { getAllPets, getAllFavs, getAllRecents } from '../api/petRequests';

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
  const favUrl = 'http://localhost:8080/user/fav';
  const recUrl = 'http://localhost:8080/user/recent';

  const [petList, setPetList] = useState([]);
  const [favList, setFavList] = useState([]);
  const [recList, setRecList] = useState([]);
  const [loading, setLoading] = useState(true);

  const handleError = () => {};
  useEffect(() => {
    axios
      .all([getAllPets(url), getAllFavs(favUrl), getAllRecents(recUrl)])
      .then(
        axios.spread((allPetRes, allFavRes, allRecRes) => {
          setPetList(allPetRes.data);
          setFavList(allFavRes.data);
          setRecList(allRecRes.data);
        })
      )
      .catch(handleError)
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
          <PetCardSlider petList={favList} heading="Favorites" />
          <PetCardSlider petList={recList} heading="Recently Viewed" />
          <PetCardSlider petList={petList} heading="Adopted" />
        </div>
      )}
    </div>
  );
};

export default UserDashboard;
