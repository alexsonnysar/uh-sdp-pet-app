import React, { useState } from "react";
import { TextField, Button, makeStyles, MenuItem } from "@material-ui/core";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
//import { PostAddPet } from "../api/PostAddPet";
import axios from "axios";

const RegisterPetForm = () => {
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    sex: "",
    age: "",
    size: "",
    weight: "",
    dateAdded: "",
    description: "",
    imageNames: "",
    adopted: "",
    active: ""
  });
  const date = {
    someDate: new Date().toISOString().substring(0, 10)
  };

  const handleChange = e => {
    console.log(e.target.id);
    setFormData({
      ...formData,
      [e.target.id]: e.target.value
    });
    console.log("FormData: ", formData);
  };

  //Currently mapping info one at a time
  //Maybe there's a way to send whole object formatted correctly?
  const PostAddPet = petData => {
    alert(petData["name"]);
    axios
      .post("http://localhost:8080/pet", {
        name: petData.name,
        weight: petData.weight,
        description: petData.description
      })
      .then(response => console.log(response))
      .catch(error => console.log(error));
  };

  //Have not handled validation at this part yet
  const handleSubmit = () => {
    // if(state is valid){
    //   make api call
    // }

    PostAddPet(formData);
  };

  const classes = useStyles();

  //handleChange does not correctly map for selector fields
  //Maybe new handle change function is needed for them?
  return (
    <div data-testid="registerPetForm">
      <form className={classes.container}>
        <h1 align="center">Register Pet</h1>
        <TextField
          id="name"
          label="Name"
          variant="outlined"
          m={20}
          onChange={e => handleChange(e)}
        />
        <TextField
          id="type"
          label="Type"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="Dog">Dog</MenuItem>
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
          label="Sex"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="M">Male</MenuItem>
          <MenuItem value="F">Female</MenuItem>
        </TextField>
        <TextField
          id="age"
          label="Age"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="Baby">Baby</MenuItem>
          <MenuItem value="Young">Young</MenuItem>
          <MenuItem value="Adult">Adult</MenuItem>
          <MenuItem value="Jurassic">Jurassic</MenuItem>
        </TextField>

        <TextField
          id="size"
          label="Size"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="Small">Small</MenuItem>
          <MenuItem value="Medium">Medium</MenuItem>
          <MenuItem value="Large">Large</MenuItem>
        </TextField>
        <TextField
          id="weight"
          label="Weight"
          variant="outlined"
          onChange={e => handleChange(e)}
        />
        <TextField
          id="dateAdded"
          label="Arrival Date"
          type="date"
          defaultValue={date.someDate}
          InputLabelProps={{
            shrink: true
          }}
          variant="outlined"
          onChange={e => handleChange(e)}
        />
        <TextField
          id="description"
          multiline
          rowMax="4"
          label="Description"
          variant="outlined"
          onChange={e => handleChange(e)}
        />
        <TextField
          id="active"
          label="Active"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>

        <TextField
          id="adopted"
          label="Adopted"
          variant="outlined"
          select
          onChange={e => handleChange(e)}
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>

        <Button
          variant="outlined"
          className={classes.button}
          onClick={() => handleSubmit()}
        >
          Pet Registration
        </Button>
      </form>
    </div>
  );
};

const useStyles = makeStyles(theme => ({
  container: {
    "& > *": {
      margin: theme.spacing(1)
    },
    width: "20rem",
    display: "flex",
    flexDirection: "column"
  },
  button: {
    color: "primary"
  },
  text: {
    textAlign: "center"
  }
}));

export default RegisterPetForm;
