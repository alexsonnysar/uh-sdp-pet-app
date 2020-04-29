import React from 'react';
import PropTypes from 'prop-types';
import { Grid } from '@material-ui/core';
import PetCard from './PetCard';

const PetCardList = ({ petList, userFavorite, roles }) => (
  <Grid container justify="flex-start" data-testid="manypetcards">
    {petList.map((pet) => (
      <Grid key={pet.id} item>
        <PetCard
          roles={roles}
          key={pet.id}
          pet={pet}
          userFavorite={userFavorite.includes(pet.id)}
        />
      </Grid>
    ))}
  </Grid>
);

PetCardList.propTypes = {
  petList: PropTypes.arrayOf(PropTypes.object).isRequired,
  userFavorite: PropTypes.arrayOf(PropTypes.string).isRequired,
  roles: PropTypes.string.isRequired,
};

export default PetCardList;
