import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Typography, Grid, Box, Divider, Paper, Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import FavoriteRoundedIcon from '@material-ui/icons/FavoriteRounded';
import PetsRoundedIcon from '@material-ui/icons/PetsRounded';
import SuccessRequestMsg from './SuccessRequestMsg';
import {
  favoritePet,
  unfavoritePet,
  requestAdoptPet,
  cancelAdoptRequest,
} from '../api/petRequests';

const useStyles = makeStyles((theme) => ({
  image: {
    height: '30rem',
    margin: 10,
  },
  imageCenter: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  content: {
    margin: theme.spacing(3, 2),
  },
  divider: {
    margin: theme.spacing(1),
  },
  verticalDivider: {
    width: 'fit-content',
    '& hr': {
      margin: theme.spacing(0, 1.5),
    },
  },
  button: {
    '& > *': {
      margin: theme.spacing(1),
    },
  },
}));

const idCheck = (list, id) => {
  if (list === null) {
    return false;
  }
  return list.includes(id);
};

const PetInfo = ({ pet, roles }) => {
  const { id, name, age, size, type, weight, sex, description } = pet;
  const numWeight = Number(weight).toFixed(2);

  const favIDs = JSON.parse(localStorage.getItem('favIDs'));
  const reqIDs = JSON.parse(localStorage.getItem('reqIDs'));
  const [favorited, setFavorited] = useState(idCheck(favIDs, id));
  const [requested, setRequested] = useState(idCheck(reqIDs, id));

  const [loading, setLoading] = useState(false);
  const [successMsgOpen, setSuccessMsgOpen] = useState(false);
  const userData = {
    id: window.localStorage.getItem('userId'),
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSuccessMsgOpen(false);
  };
  const RequestingPet = (requestData) => {
    const url = `http://localhost:8080/request/adopt/${id}`;
    const cancelUrl = `http://localhost:8080/request/cancel/${id}`;
    setLoading(true);

    if (requested) {
      cancelAdoptRequest(cancelUrl, requestData)
        .then(() => {
          setRequested(false);
          setSuccessMsgOpen(true);
          const oldReqs = JSON.parse(localStorage.getItem('reqIDs'));
          const newReqs = oldReqs.filter((pr) => pr !== id);

          localStorage.setItem('reqIDs', JSON.stringify(newReqs));
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      requestAdoptPet(url, requestData)
        .then(() => {
          setRequested(true);
          setSuccessMsgOpen(true);
          const reqs = JSON.parse(localStorage.getItem('reqIDs'));
          reqs.push(id);
          localStorage.setItem('reqIDs', JSON.stringify(reqs));
        })
        .finally(() => {
          setLoading(false);
        });
    }
  };

  const FavoritingPet = (favData) => {
    const url = `http://localhost:8080/user/fav/${id}`;
    setLoading(true);

    if (favorited) {
      unfavoritePet(url, favData)
        .then(() => {
          setFavorited(false);
          const oldFavs = JSON.parse(localStorage.getItem('favIDs'));
          const newFavs = oldFavs.filter((o) => o !== id);
          localStorage.setItem('favIDs', JSON.stringify(newFavs));
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      favoritePet(url, favData)
        .then(() => {
          setFavorited(true);
          const favs = JSON.parse(localStorage.getItem('favIDs'));
          favs.push(id);
          localStorage.setItem('favIDs', JSON.stringify(favs));
        })
        .finally(() => {
          setLoading(false);
        });
    }
  };

  const handleFavorite = () => {
    FavoritingPet(userData);
  };

  const handleAdopt = () => {
    RequestingPet(userData);
  };

  const fullSexName = sex === 'M' ? 'Male' : 'Female';

  const classes = useStyles();

  return (
    <div data-testid="petInfo">
      <Paper>
        <Grid container>
          <Grid item sm={12} md={6} lg={6}>
            <div className={classes.imageCenter}>
              <img className={classes.image} src="/images/garfield.jpg" alt="pet" />
            </div>
          </Grid>
          <Grid item sm={12} md={6} lg={6}>
            <div className={classes.content}>
              <Typography component="div">
                <Box fontWeight="fontWeightBold" fontSize="h4.fontSize">
                  {name}
                </Box>
              </Typography>
              <Divider variant="middle" className={classes.divider} />
              <Grid container className={classes.verticalDivider}>
                <Typography variant="subtitle1">{type}</Typography>
                <Divider orientation="vertical" flexItem />
                <Typography variant="subtitle1">{age}</Typography>
                <Divider orientation="vertical" flexItem />
                <Typography variant="subtitle1">{size}</Typography>
                <Divider orientation="vertical" flexItem />
                <Typography variant="subtitle1">{`${numWeight} lbs`}</Typography>
                <Divider orientation="vertical" flexItem />
                <Typography variant="subtitle1">{fullSexName}</Typography>
              </Grid>
              <Divider variant="middle" className={classes.divider} />
              <Typography variant="body1">{description}</Typography>
              <Divider variant="middle" className={classes.divider} />

              {roles !== 'ROLE_Employee' ? (
                <div className={classes.button}>
                  <Button
                    size="large"
                    color="primary"
                    variant={requested ? 'contained' : 'outlined'}
                    startIcon={<PetsRoundedIcon />}
                    onClick={() => handleAdopt()}
                    disabled={loading}
                  >
                    {requested ? 'Cancel Adoption' : 'Adopt Me'}
                  </Button>
                  <Button
                    size="large"
                    color="secondary"
                    variant={favorited ? 'contained' : 'outlined'}
                    startIcon={<FavoriteRoundedIcon />}
                    onClick={() => handleFavorite()}
                    disabled={loading}
                  >
                    {favorited ? 'Unfavorite' : 'Favorite'}
                  </Button>
                  <SuccessRequestMsg
                    handleClose={() => handleClose()}
                    open={successMsgOpen}
                    successMsg={
                      requested
                        ? `Successfully requested ${name}`
                        : `Successfully canceled request for ${name}`
                    }
                  />
                </div>
              ) : (
                <div data-testid="EmployeeOnly" />
              )}
            </div>
          </Grid>
        </Grid>
      </Paper>
    </div>
  );
};

PetInfo.propTypes = {
  pet: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    age: PropTypes.string,
    size: PropTypes.string,
    type: PropTypes.string,
    sex: PropTypes.string,
    weight: PropTypes.number,
    description: PropTypes.string,
  }).isRequired,
  roles: PropTypes.string.isRequired,
};

export default PetInfo;
