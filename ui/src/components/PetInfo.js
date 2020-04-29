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
import axios from 'axios';
import SuccessRequestMsg from './SuccessRequestMsg';
import RegisterMsg from './RegisterMsg';
import { favoritePet, unfavoritePet } from '../api/petRequests';

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

const favIDsCheck = (favList, petId) => {
  if (favList === null) {
    return false;
  }
  return favList.includes(petId);
};

const PetInfo = ({ pet, roles }) => {
  const matches = useMediaQuery('(min-width:450px)');
  const { id, name, age, size, type, weight, sex, description } = pet;
  const numWeight = Number(weight).toFixed(2);

  const favIDs = JSON.parse(localStorage.getItem('favIDs'));
  const [favorited, setFavorited] = useState(favIDsCheck(favIDs, id));

  const [loading, setLoading] = useState(false);
  const [requested, setRequest] = useState(false);
  const [successMsgOpen, setSuccessMsgOpen] = useState(false);
  const [open, setOpen] = useState(false);
  const [registerOnFav, setRegisterOnFav] = useState(false);

  const reqData = {
    petid: id,
  };

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const userData = {
    id: window.localStorage.getItem('userId'),
  };

  const userRoleConfirm = (userRole) => {
    if (userRole !== 'ROLE_User') {
      return true;
    }
    return false;
  };

  const PostCreateRequest = (requestData) => {
    setLoading(true);
    axios({
      method: 'post',
      url: `http://localhost:8080/request/adopt/${id}`,
      headers: reqHeaders,
      data: requestData,
    })
      .then(() => {
        setRequest(true);
        setSuccessMsgOpen(true);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleSubmit = () => {
    if (userRoleConfirm(roles)) {
      setOpen(true);
    } else {
      PostCreateRequest(reqData);
    }
  };

  const successHandleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSuccessMsgOpen(false);
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
    if (userRoleConfirm(roles)) {
      setRegisterOnFav(true);
    } else {
      FavoritingPet(userData);
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
                  <Button
                    size="large"
                    color="primary"
                    variant="contained"
                    startIcon={<PetsRoundedIcon />}
                    onClick={() => handleSubmit()}
                    disabled={loading}
                  >
                    {requested ? 'Requested' : 'Adopt Me'}
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
                    handleClose={() => successHandleClose()}
                    open={successMsgOpen}
                    successMsg={`Successfully Requested ${name}!`}
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
    weight: PropTypes.string,
    description: PropTypes.string,
  }).isRequired,
  roles: PropTypes.string.isRequired,
};

export default PetInfo;
