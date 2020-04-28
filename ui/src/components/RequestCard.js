import React from 'react';
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardContent, CardMedia } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
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
  text: {
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const PetCard = ({ request }) => {
  const { petName, petId, status } = request;

  const history = useHistory();
  const petLink = `/pet-profile/${petId}`;

  const handleClick = () => {
    history.push(petLink);
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
            {petName}
          </Typography>
          <Typography variant="subtitle1" color="textSecondary" component="p">
            {status}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

PetCard.propTypes = {
  request: PropTypes.shape({
    petName: PropTypes.string,
    petImg: PropTypes.string,
    petId: PropTypes.string,
    status: PropTypes.string,
  }).isRequired,
};

export default PetCard;
