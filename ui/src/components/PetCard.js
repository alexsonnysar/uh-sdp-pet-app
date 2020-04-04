import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CardMedia,
} from '@material-ui/core';
import FavoriteRoundedIcon from '@material-ui/icons/FavoriteRounded';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ButtonBase from '@material-ui/core/ButtonBase';
import PropTypes from 'prop-types';

const useStyles = makeStyles({
  root: {
    marginLeft: 10,
    marginRight: 10,
    marginTop: 5,
    marginBottom: 5,
    maxWidth: 170,
    minWidth: 170,
  },
  media: {
    height: 120,
  },
});

const PetCard = ({ pet }) => {
  const { name, type } = pet;
  const classes = useStyles();

  return (
    <Card className={classes.root} data-testid="petcard">
      <ButtonBase href="pet-profile" style={{ textDecorationLine: 'none' }}>
        <CardActionArea>
          <CardMedia
            className={classes.media}
            component="img"
            alt="Pet Image"
            image="/images/garfield.jpg"
            title="Pet Image"
          />
          <CardContent className={classes.text}>
            <Typography gutterBottom variant="h5" component="h2">
              {name}
            </Typography>
            <Typography variant="subtitle1" color="textSecondary" component="p">
              {type}
            </Typography>
          </CardContent>
        </CardActionArea>
      </ButtonBase>
      <CardActions>
        {localStorage.getItem('jwt') !== null ? (
          <Button
            size="small"
            color="secondary"
            startIcon={<FavoriteRoundedIcon />}
          >
            Favorite
          </Button>
        ) : (
          []
        )}
      </CardActions>
    </Card>
  );
};

PetCard.propTypes = {
  pet: PropTypes.shape({
    name: PropTypes.object,
    type: PropTypes.string,
  }).isRequired,
};

export default PetCard;
