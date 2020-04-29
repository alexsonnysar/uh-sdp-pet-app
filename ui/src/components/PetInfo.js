import React, { useState } from 'react';
import PropTypes from 'prop-types';
import {
  Typography,
  Grid,
  Box,
  Divider,
  Paper,
  Button,
  useMediaQuery,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import FavoriteRoundedIcon from '@material-ui/icons/FavoriteRounded';
import PetsRoundedIcon from '@material-ui/icons/PetsRounded';
import SuccessRequestMsg from './SuccessRequestMsg';
import RegisterMsg from './RegisterMsg';
import {
  favoritePet,
  unfavoritePet,
  cancelAdoptRequest,
  requestAdoptPet,
} from '../api/petRequests';

const useStyles = makeStyles((theme) => ({
  imageSmall: {
    height: '14.5rem',
    margin: 10,
  },
  imageLarge: {
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
  const auth = `Bearer ${localStorage.getItem('jwt')}`;

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: auth,
  };

  const matches = useMediaQuery('(min-width:450px)');
  const { id, name, age, size, type, weight, sex, description, adopted } = pet;
  const numWeight = Number(weight).toFixed(2);

  const favIDs = JSON.parse(localStorage.getItem('favIDs'));
  const reqIDs = JSON.parse(localStorage.getItem('reqIDs'));
  const [favorited, setFavorited] = useState(idCheck(favIDs, id));
  const [requested, setRequested] = useState(idCheck(reqIDs, id));

  const [loading, setLoading] = useState(false);
  const [successMsgOpen, setSuccessMsgOpen] = useState(false);
  const [open, setOpen] = useState(false);
  const [registerOnFav, setRegisterOnFav] = useState(false);

  const userData = {
    id: window.localStorage.getItem('userId'),
  };

  const userRoleConfirm = (userRole) => {
    if (userRole !== 'ROLE_User') {
      return true;
    }
    return false;
  };

  const successHandleClose = (event, reason) => {
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
      cancelAdoptRequest(cancelUrl, requestData, reqHeaders)
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
      requestAdoptPet(url, requestData, reqHeaders)
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

  const registerHandleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };

  const registerFavHandleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setRegisterOnFav(false);
  };

  const FavoritingPet = (favData) => {
    const url = `http://localhost:8080/user/fav/${id}`;
    setLoading(true);

    if (favorited) {
      unfavoritePet(url, favData, reqHeaders)
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
      favoritePet(url, favData, reqHeaders)
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
    if (userRoleConfirm(roles)) {
      setRegisterOnFav(true);
    } else {
      FavoritingPet(userData);
    }
  };

  const handleAdopt = () => {
    if (userRoleConfirm(roles)) {
      setOpen(true);
    } else {
      RequestingPet(userData);
    }
  };

  const fullSexName = sex === 'M' ? 'Male' : 'Female';

  const classes = useStyles();

  return (
    <div data-testid="petInfo">
      <Paper>
        <Grid container>
          <Grid item sm={12} md={6} lg={6}>
            <div className={classes.imageCenter}>
              <img
                className={matches ? classes.imageLarge : classes.imageSmall}
                src="/images/garfield.jpg"
                alt="pet"
              />
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
                  {adopted === false ? (
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
                  ) : (
                    []
                  )}
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
                    handleClose={() => successHandleClose()}
                    open={successMsgOpen}
                    successMsg={
                      requested
                        ? `Successfully requested ${name}`
                        : `Successfully canceled request for ${name}`
                    }
                  />
                  <RegisterMsg
                    open={open}
                    handleClose={() => registerHandleClose()}
                    msg={`Please register an account to request adoption for ${name}`}
                  />
                  <RegisterMsg
                    open={registerOnFav}
                    handleClose={() => registerFavHandleClose()}
                    msg={`Please register an account to favorite ${name}`}
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
    adopted: PropTypes.bool,
  }).isRequired,
  roles: PropTypes.string.isRequired,
};

export default PetInfo;
