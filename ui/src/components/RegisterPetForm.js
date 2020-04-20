import React, { useState } from 'react';
import { TextField, Button, makeStyles, MenuItem } from '@material-ui/core';
import axios from 'axios';
import SuccessRequestMsg from './SuccessRequestMsg';

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

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
  };

  const CreatePet = (petData) => {
    axios({
      method: 'post',
      url: 'http://localhost:8080/pet',
      headers: reqHeaders,
      data: petData,
    })
      .then(() => {
        setFormData(initialState);
        setOpen(true);
      })
      .catch(handleError)
      .finally(setLoading(false));
  };

  const handleSubmit = (e) => {
    // if(state is valid){
    //   make api call
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
          label="type"
          variant="outlined"
          onChange={handleSelect('type')}
          value={formData.type}
          select
        >
          <MenuItem value="Dog"> Dog </MenuItem>
          <MenuItem value="Cat">Cat</MenuItem>
          <MenuItem value="Bird">Bird</MenuItem>
          <MenuItem value="Fish">Fish</MenuItem>
          <MenuItem value="Lizard">Lizard</MenuItem>
          <MenuItem value="Furry">Furry</MenuItem>
          <MenuItem value="Farm Animal">Farm Animal</MenuItem>
          <MenuItem value="Bat">Bat</MenuItem>
        </TextField>
        <TextField
          id="sex"
          label="sex"
          variant="outlined"
          onChange={handleSelect('sex')}
          value={formData.sex}
          select
        >
          <MenuItem value="M">Male</MenuItem>
          <MenuItem value="F">Female</MenuItem>
        </TextField>
        <TextField
          id="age"
          label="age"
          variant="outlined"
          onChange={handleSelect('age')}
          value={formData.age}
          select
        >
          <MenuItem value="Baby">Baby</MenuItem>
          <MenuItem value="Young">Young</MenuItem>
          <MenuItem value="Adult">Adult</MenuItem>
          <MenuItem value="Jurassic">Jurassic</MenuItem>
        </TextField>

        <TextField
          id="size"
          label="size"
          variant="outlined"
          onChange={handleSelect('size')}
          value={formData.size}
          select
        >
          <MenuItem value="Small">Small</MenuItem>
          <MenuItem value="Medium">Medium</MenuItem>
          <MenuItem value="Large">Large</MenuItem>
        </TextField>
        <TextField
          id="weight"
          label="Weight"
          variant="outlined"
          value={formData.weight}
          onChange={(e) => handleChange(e)}
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
          label="adopted"
          variant="outlined"
          onChange={handleSelect('adopted')}
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
