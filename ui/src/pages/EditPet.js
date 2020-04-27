import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import EditPetForm from '../components/EditPetForm';
import { getSinglePet } from '../api/petRequests';

const useStyles = makeStyles({
  root: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const EditPet = () => {
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
  }, [url]);

  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="editPet">
      {loading ? (
        <div className={classes.progress} data-testid="loading">
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded">
          <div data-testid="editPet">
            <EditPetForm pet={pet} />
          </div>
        </div>
      )}
    </div>
  );
};

export default EditPet;
