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
import { favoritePet, unfavoritePet } from '../api/petRequests';

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
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const PetCard = ({ pet, userFavorite, roles }) => {
  const auth = `Bearer ${localStorage.getItem('jwt')}`;

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: auth,
  };

  const { name, type, sex, age, size, id } = pet;

  const history = useHistory();
  const petLink = `/pet-profile/${id}`;

  const [loading, setLoading] = useState(false);
  const [favorited, setFavorited] = useState(userFavorite);

  const userData = {
    id: window.localStorage.getItem('userId'),
  };

  const handleClick = () => {
    history.push(petLink);
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
    FavoritingPet(userData);
  };

  const classes = useStyles();
  const fullSexName = sex === 'M' ? 'Male' : 'Female';

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
          <Typography gutterBottom variant="h6" component="h2">
            {name}
          </Typography>
          <Typography variant="subtitle2" color="textSecondary" component="p">
            {fullSexName} | {age}
          </Typography>
          <Typography variant="subtitle2" color="textSecondary" component="p">
            {type} | {size}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        {roles === 'ROLE_User' ? (
          <Button
            fullWidth
            className={classes.actions}
            size="small"
            color="secondary"
            variant={favorited ? 'contained' : 'outlined'}
            startIcon={<FavoriteRoundedIcon />}
            onClick={() => handleFavorite()}
            disabled={loading}
          >
            {favorited ? 'Unfavorite' : 'Favorite'}
          </Button>
        ) : (
          <div data-testid="Not_User" />
        )}
      </CardActions>
    </Card>
  );
};

PetCard.propTypes = {
  pet: PropTypes.shape({
    name: PropTypes.string,
    type: PropTypes.string,
    sex: PropTypes.string,
    age: PropTypes.string,
    size: PropTypes.string,
    id: PropTypes.string,
  }).isRequired,
  userFavorite: PropTypes.bool.isRequired,
  roles: PropTypes.string.isRequired,
};

export default PetCard;
