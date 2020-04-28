import React, { useState } from 'react';
import {
  TextField,
  Button,
  makeStyles,
  MenuItem,
  InputAdornment,
} from '@material-ui/core';
import axios from 'axios';
import SuccessRequestMsg from './SuccessRequestMsg';
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

const RegisterPetForm = () => {
  const initialState = {
    name: '',
    type: '',
    sex: '',
    age: '',
    size: '',
    weight: '',
    description: '',
    imageNames: [''],
    adopted: false,
    active: true,
  };
  const [formData, setFormData] = useState(initialState);
  const [loading, setLoading] = useState(false);
  const [open, setOpen] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value,
    });
  };
  const handleSelect = (peram, event) => {
    setFormData({
      ...formData,
      [peram]: event.target.value,
    });
  };
  const handleError = () => {};

  const reqHeaders = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };

  const CreatePet = (petData) => {
    axios
      .post('http://localhost:8080/pet', petData, { headers: reqHeaders })
      .then(() => {
        setFormData(initialState);
        setOpen(true);
      })
      .catch(handleError)
      .finally(setLoading(false));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    CreatePet(formData);
  };

  const classes = useStyles();

  return (
    <div data-testid="registerPetForm">
      <form className={classes.container}>
        <h1 align="center">Register Pet</h1>
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
          onChange={(e) => handleSelect('type', e)}
          value={formData.type}
          select
          SelectProps={{
            SelectDisplayProps: {
              'data-testid': 'type-select',
            },
          }}
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
          onChange={(e) => handleSelect('sex', e)}
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
          onChange={(e) => handleSelect('age', e)}
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
          onChange={(e) => handleSelect('size', e)}
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
          label="Weight"
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
          onChange={(e) => handleSelect('adopted', e)}
          value={formData.adopted}
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
          Create
        </Button>
        <SuccessRequestMsg
          handleClose={() => handleClose()}
          open={open}
          successMsg="Successfully Registered Pet!"
        />
      </form>
    </div>
  );
};

export default RegisterPetForm;
