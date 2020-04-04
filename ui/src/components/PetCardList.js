import React from 'react';
import PropTypes from 'prop-types';
import { Grid } from '@material-ui/core';
import PetCard from './PetCard';

const PetCardList = ({ petList }) => (
  <Grid container justify="flex-start" data-testid="manypetcards">
    {petList.map((pet) => (
      <Grid key={pet.id} item>
        <PetCard key={pet.id} pet={pet} />
      </Grid>
    ))}
  </Grid>
);


PetCardList.propTypes = {
  petList: PropTypes.arrayOf({
    pet: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
};

export default PetCardList;
