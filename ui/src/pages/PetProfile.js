import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import PetInfo from '../components/PetInfo';
import { getSinglePet } from '../api/petRequests';

const useStyles = makeStyles({
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const PetProfile = () => {
  const { id } = useParams();

  const [pet, setPet] = useState([]);
  const [loading, setLoading] = useState(true);

  const url = `http://localhost:8080/pet/${id}`;

  const handleError = () => {};
  useEffect(() => {
    getSinglePet(url)
      .then((res) => {
        setPet(res.data);
      })
      .catch(handleError)
      .finally(() => setLoading(false));
  }, []);

  const classes = useStyles();

  return (
    <div data-testid="petprofile">
      {loading ? (
        <div className={classes.progress} data-testid="loading">
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded">
          <PetInfo pet={pet} />
        </div>
      )}
    </div>
  );
};

export default PetProfile;
