import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useParams } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import { CircularProgress } from '@material-ui/core';
import PetInfo from '../components/PetInfo';
import { getSinglePet, addRecent } from '../api/petRequests';

const useStyles = makeStyles({
  progress: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const PetProfile = ({ roles }) => {
  const auth = `Bearer ${localStorage.getItem('jwt')}`;

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: auth,
  };

  const { id } = useParams();
  const [pet, setPet] = useState([]);
  const [loading, setLoading] = useState(true);

  const url = `http://localhost:8080/pet/${id}`;
  const recUrl = `http://localhost:8080/user/recent/${id}`;

  if (roles === 'ROLE_User') {
    addRecent(recUrl, reqHeaders);
  }

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
    <div data-testid="petprofile">
      {loading ? (
        <div className={classes.progress} data-testid="loading">
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <div data-testid="loaded">
          <PetInfo pet={pet} roles={roles} />
        </div>
      )}
    </div>
  );
};

PetProfile.propTypes = {
  roles: PropTypes.string,
};

PetProfile.defaultProps = {
  roles: '',
};

export default PetProfile;
