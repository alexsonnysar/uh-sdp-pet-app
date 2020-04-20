import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
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
import PropTypes from 'prop-types';
import axios from 'axios';
import { favoritePet } from '../api/petRequests';

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
  text: {
    alignItems: 'ceneter',
    justifyContent: 'center',
  },
});

const PetCard = ({ pet }) => {
  const { name, type, id } = pet;

  const history = useHistory();
  const petLink = `pet-profile/${id}`;

  const [loading, setLoading] = useState(false);
  const [favorited, setFavorited] = useState(false);

  const userData = {
    id: window.localStorage.getItem('userId'),
  };

  const handleClick = () => {
    history.push(petLink);
  };

  const PostFavoritePet = (favData) => {
    const postUrl = `http://localhost:8080/user/fav/${id}`;
    setLoading(true);
    favoritePet(postUrl, favData)
      .then(() => {
        setFavorited(true);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleFavorite = () => {
    PostFavoritePet(userData);
  };

  const classes = useStyles();

  return (
    <Card className={classes.root} data-testid="petcard">
      <CardActionArea onClick={() => handleClick()}>
        <CardMedia
          className={classes.media}
          component="img"
          alt="Pet Image"
          image="/images/garfield.jpg"
          title="Pet Image"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {name}
          </Typography>
          <Typography variant="subtitle1" color="textSecondary" component="p">
            {type}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        {localStorage.getItem('jwt') !== null ? (
          <Button
            size="small"
            color="secondary"
            variant="contained"
            startIcon={<FavoriteRoundedIcon />}
            onClick={() => handleFavorite()}
            disabled={loading}
          >
            {favorited ? 'Favorited' : 'Favorite'}
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
    name: PropTypes.string,
    type: PropTypes.string,
    id: PropTypes.string,
  }).isRequired,
};

export default PetCard;
