import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { Button, TextField, MenuItem, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { animalType, sexType, ageType, sizeType } from '../utils/MenuItems';

const useStyles = makeStyles((theme) => ({
  form: {
    margin: theme.spacing(1),
    width: '15ch',
  },
  button: {
    height: '7ch',
    margin: theme.spacing(1),
  },
}));
const PetFilters = ({ setFilteredPetList, originalPetList }) => {
  const initialState = {
    type: '',
    sex: '',
    age: '',
    size: '',
  };

  const [formData, setFormData] = useState(initialState);
  const handleFilters = (filterPeram, value) => {
    const filteredList = originalPetList.filter((pet) => pet[filterPeram] === value);
    return filteredList;
  };
  const classes = useStyles();

  const handleChange = (peram, event) => {
    setFormData({
      [peram]: event.target.value,
    });
    setFilteredPetList(handleFilters(peram, event.target.value));
  };
  const handleClear = () => {
    setFormData(initialState);
    setFilteredPetList(originalPetList);
  };
  return (
    // - weight range maybe???
    <div data-testid="testingFilter">
      <form>
        <Typography variant="h4" className={classes.form}>
          {' '}
          Filters{' '}
        </Typography>
        <TextField
          className={classes.form}
          id="type"
          data-testid="testingType"
          label="Type"
          variant="outlined"
          value={formData.type}
          onChange={(e) => handleChange('type', e)} // on click
          select
        >
          {animalType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          className={classes.form}
          id="sex"
          label="Sex"
          variant="outlined"
          value={formData.sex}
          onChange={(e) => handleChange('sex', e)} // on click
          select
        >
          {sexType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.label}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          className={classes.form}
          id="age"
          label="Age"
          variant="outlined"
          value={formData.age}
          onChange={(e) => handleChange('age', e)} // on click
          select
        >
          {ageType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          className={classes.form}
          id="size"
          label="Size"
          variant="outlined"
          value={formData.size}
          onChange={(e) => handleChange('size', e)}
          select
        >
          {sizeType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <Button
          className={classes.button}
          variant="contained"
          color="secondary"
          onClick={() => handleClear()}
        >
          Clear
        </Button>
      </form>
    </div>
  );
};

PetFilters.propTypes = {
  setFilteredPetList: PropTypes.func.isRequired,
  originalPetList: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default PetFilters;
