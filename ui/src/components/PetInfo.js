import React from 'react';
import PropTypes from 'prop-types';
import { Typography, Grid, Box, Divider, Paper, Button } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import FavoriteRoundedIcon from '@material-ui/icons/FavoriteRounded';
import PetsRoundedIcon from '@material-ui/icons/PetsRounded';

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
  const { name, age, size, type, sex, description } = pet;

  const classes = useStyles();

  return (
    <div>
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
                <Typography variant="subtitle1">{sex}</Typography>
              </Grid>
              <Divider variant="middle" className={classes.divider} />
              <Typography variant="body1">{description}</Typography>
              <Divider variant="middle" className={classes.divider} />
              {localStorage.getItem('jwt') !== null ? (
                <div className={classes.button}>
                  <Button
                    size="large"
                    color="primary"
                    variant="contained"
                    startIcon={<PetsRoundedIcon />}
                  >
                    Adopt Me
                  </Button>
                  <Button
                    size="large"
                    color="secondary"
                    variant="contained"
                    startIcon={<FavoriteRoundedIcon />}
                  >
                    Favorite
                  </Button>
                </div>
              ) : (
                []
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
    name: PropTypes.string,
    age: PropTypes.string,
    size: PropTypes.string,
    type: PropTypes.string,
    sex: PropTypes.string,
    description: PropTypes.string,
  }).isRequired,
};

export default PetInfo;
