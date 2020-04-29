import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
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

const Home = ({ roles }) => {
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
          <PetCardList
            roles={roles}
            petList={filteredPetList}
            userFavorite={
              localStorage.getItem('favIDs') !== null
                ? localStorage.getItem('favIDs')
                : []
            }
          />
        </div>
      )}
    </div>
  );
};

Home.propTypes = {
  roles: PropTypes.string,
};

Home.defaultProps = {
  roles: '',
};

export default Home;
