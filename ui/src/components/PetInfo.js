import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Typography, Grid, Box, Divider, Paper, Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import FavoriteRoundedIcon from '@material-ui/icons/FavoriteRounded';
import PetsRoundedIcon from '@material-ui/icons/PetsRounded';
import axios from 'axios';
import SuccessRequestMsg from './SuccessRequestMsg';
import { favoritePet } from '../api/petRequests';

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

const PetInfo = ({ pet }) => {
  const { id, name, age, size, type, sex, description } = pet;

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

  const [loading, setLoading] = useState(false);
  const [requested, setRequest] = useState(false);
  const [successMsgOpen, setSuccessMsgOpen] = useState(false);

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
    PostCreateRequest(reqData);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSuccessMsgOpen(false);
  };

  const PostFavoritePet = (favData) => {
    const postUrl = `http://localhost:8080/user/fav/${id}`;
    setLoading(true);
    favoritePet(postUrl, favData)
      .then(() => {})
      .finally(() => {
        setLoading(false);
      });
  };

  const handleFavorite = () => {
    PostFavoritePet(userData);
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
                <Typography variant="subtitle1">{fullSexName}</Typography>
              </Grid>
              <Divider variant="middle" className={classes.divider} />
              <Typography variant="body1">{description}</Typography>
              <Divider variant="middle" className={classes.divider} />
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
                  variant="contained"
                  startIcon={<FavoriteRoundedIcon />}
                  onClick={() => handleFavorite()}
                  disabled={loading}
                >
                  Favorite
                </Button>
                <SuccessRequestMsg
                  handleClose={() => handleClose()}
                  open={successMsgOpen}
                  successMsg={`Successfully Requested ${name}!`}
                />
              </div>
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
    description: PropTypes.string,
  }).isRequired,
};

export default PetInfo;
