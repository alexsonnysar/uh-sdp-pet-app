import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import PetCardList from '../components/PetCardList';
import { getAllPets } from '../api/petRequests';
import PetFilters from '../components/PetFilters';

const useStyles = makeStyles({
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const Home = () => {
  const url = 'http://localhost:8080/pet';

  const [originalPetList, setOriginalPetList] = useState([]);
  const [filteredPetList, setFilteredPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  const handleError = () => {};
  useEffect(() => {
    getAllPets(url)
      // eslint-disable-next-line no-shadow
      .then((res) => {
        setOriginalPetList(res.data);
        setFilteredPetList(res.data);
      })
      .catch(handleError)
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
          <PetFilters
            setFilteredPetList={setFilteredPetList}
            originalPetList={originalPetList}
          />
          <PetCardList petList={filteredPetList} />
        </div>
      )}
    </div>
  );
};

export default Home;
