import React from 'react';
import PropTypes from 'prop-types';
import { Typography } from '@material-ui/core';
import PetCard from './PetCard';
import './PetCardSlider.css';

const PetCardSlider = ({ petList, heading }) => (
  <div data-testid="petCardSlider">
    <div>
      <Typography variant="h6">{heading}</Typography>
    </div>
    <div className="root">
      {petList.map((pet) => (
        <PetCard key={pet.id} pet={pet} />
      ))}
    </div>
  </div>
);

PetCardSlider.propTypes = {
  petList: PropTypes.arrayOf(PropTypes.object).isRequired,
  heading: PropTypes.string.isRequired,
};

export default PetCardSlider;
