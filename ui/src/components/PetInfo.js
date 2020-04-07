import React from 'react';
import PropTypes from 'prop-types';
import { Typography } from '@material-ui/core';

const PetInfo = ({ pet }) => {
  const { name, type, sex, description } = pet;

  return (
    <div>
      <Typography variant="h3">{name}</Typography>
      <Typography variant="h3">{type}</Typography>
      <Typography variant="h3">{sex}</Typography>
      <Typography variant="h3">{description}</Typography>
    </div>
  );
};

PetInfo.propTypes = {
  pet: PropTypes.shape({
    name: PropTypes.string,
    type: PropTypes.string,
    sex: PropTypes.string,
    description: PropTypes.string,
  }).isRequired,
};

export default PetInfo;
