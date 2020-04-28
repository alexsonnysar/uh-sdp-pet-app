import React from 'react';
import PropTypes from 'prop-types';
import { Typography } from '@material-ui/core';
import PetCard from './PetCard';
import './Slider.css';

const PetCardSlider = ({ petList, heading, userFav }) => {
  return (
    <div data-testid="petCardSlider">
      <div>
        <Typography variant="h6">{heading}</Typography>
      </div>
      {petList.length !== 0 ? (
        <div>
          <div className="root">
            {petList.map((pet) => (
              <PetCard
                key={pet.id}
                pet={pet}
                userFavorite={userFav.includes(pet.id)}
                roles="ROLE_User"
              />
            ))}
          </div>
        </div>
      ) : (
        <div className="media">
          <Typography variant="subtitle1"> No Pets to Show :(</Typography>
        </div>
      )}
    </div>
  );
};

PetCardSlider.propTypes = {
  petList: PropTypes.arrayOf(PropTypes.object).isRequired,
  heading: PropTypes.string.isRequired,
  userFav: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default PetCardSlider;
