import React, { useState } from 'react';
import {
  TextField,
  Button,
  makeStyles,
  MenuItem,
  InputAdornment,
} from '@material-ui/core';
import axios from 'axios';
import PropTypes from 'prop-types';
import { useHistory } from 'react-router-dom';
import { animalType, sexType, ageType, sizeType } from '../utils/MenuItems';

const useStyles = makeStyles((theme) => ({
  container: {
    '& > *': {
      margin: theme.spacing(1),
    },
    width: '20rem',
    display: 'flex',
    flexDirection: 'column',
  },
  button: {
    color: 'primary',
  },
  text: {
    textAlign: 'center',
  },
}));

const EditPetForm = ({ pet }) => {
  const { id, name } = pet;
  const [formData, setFormData] = useState(pet);
  const [loading, setLoading] = useState(false);

  const history = useHistory();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value,
    });
  };
  const handleSelect = (prop) => (event) => {
    setFormData({
      ...formData,
      [prop]: event.target.value,
    });
  };
  const handleError = () => {};

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };
  const UpdatePet = (petData) => {
    axios({
      method: 'put',
      url: `http://localhost:8080/pet/${id}`,
      headers: reqHeaders,
      data: petData,
    })
      .then(() => {})
      .catch(handleError)
      .finally(setLoading(false));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    UpdatePet(formData);
    history.replace('/employee/dashboard');
  };

  const classes = useStyles();

  return (
    <div data-testid="editPetForm">
      <form className={classes.container}>
        <h1 align="center">Update {name}</h1>
        <TextField
          id="name"
          label="Name"
          variant="outlined"
          value={formData.name}
          onChange={(e) => handleChange(e)}
        />
        <TextField
          id="type"
          label="Type"
          variant="outlined"
          onChange={handleSelect('type')}
          value={formData.type}
          select
        >
          {animalType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          id="sex"
          label="Sex"
          variant="outlined"
          onChange={handleSelect('sex')}
          value={formData.sex}
          select
        >
          {sexType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {' '}
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          id="age"
          label="Age"
          variant="outlined"
          onChange={handleSelect('age')}
          value={formData.age}
          select
        >
          {ageType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {' '}
              {option.value}
            </MenuItem>
          ))}
        </TextField>

        <TextField
          id="size"
          label="Size"
          variant="outlined"
          onChange={handleSelect('size')}
          value={formData.size}
          select
        >
          {sizeType.map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.value}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          id="weight"
          label="Weight in lbs."
          variant="outlined"
          value={formData.weight}
          onChange={(e) => handleChange(e)}
          InputProps={{
            endAdornment: <InputAdornment position="end">lbs</InputAdornment>,
          }}
        />
        <TextField
          id="description"
          multiline
          label="Description"
          variant="outlined"
          value={formData.description}
          onChange={(e) => handleChange(e)}
        />
        <TextField
          id="adopted"
          label="Adopted"
          variant="outlined"
          onChange={handleSelect('adopted')}
          value={formData.adopted}
          select
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>
        <TextField
          id="active"
          label="Active"
          variant="outlined"
          onChange={handleSelect('active')}
          value={formData.active}
          select
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>
        <Button
          variant="outlined"
          className={classes.button}
          onClick={(e) => handleSubmit(e)}
          type="submit"
          disabled={loading}
        >
          Update Pet
        </Button>
      </form>
    </div>
  );
};

EditPetForm.propTypes = {
  pet: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
  }).isRequired,
};

export default EditPetForm;
