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
    imageNames: [""],
    adopted: false,
    active: true
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
  const handleSelect = prop => event => {
    console.log(event);
    setFormData({
      ...formData,
      [prop]: event.target.value
    });
  };

  //Currently mapping info one at a time
  //Maybe there's a way to send whole object formatted correctly?
  const PostAddPet = petData => {
    axios({
      method: "post",
      url: "http://localhost:8080/pet",
      data: petData,
      headers: { "Content-Type": "application/json" }
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

  return (
    <div data-testid="registerPetForm">
      <form className={classes.container}>
        <h1 align="center">Register Pet</h1>
        <TextField
          id="name"
          label="Name"
          variant="outlined"
          onChange={handleChange}
        />
        <TextField
          id="type"
          label="type"
          variant="outlined"
          onChange={handleSelect("type")}
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
          onChange={handleSelect("sex")}
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
          onChange={handleSelect("age")}
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
          onChange={handleSelect("size")}
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
          onChange={handleChange}
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
          onChange={handleChange}
        />
        <TextField
          id="description"
          multiline
          rowMax="4"
          label="Description"
          variant="outlined"
          onChange={handleChange}
        />
        <TextField
          id="adopted"
          label="adopted"
          variant="outlined"
          onChange={handleSelect("adopted")}
          value={formData.adopted}
          select
        >
          <MenuItem value="true">True</MenuItem>
          <MenuItem value="false">False</MenuItem>
        </TextField>

        <Button
          variant="outlined"
          className={classes.button}
          onClick={() => handleSubmit()}
        >
          Create
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
