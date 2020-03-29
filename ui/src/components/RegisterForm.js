import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import axios from "axios";

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    passwordConfirm: "",
    firstName: "",
    lastName: ""
  });

  const PostAddUser = userData => {
    axios({
      method: "post",
      url: "http://localhost:8080/user",
      data: userData,
      headers: { "Content-Type": "application/json" }
    })
      .then(response => console.log(response))
      .catch(error => console.log(error));
  };

  const handleChange = e => {
    console.log(e.target.id);
    setFormData({
      ...formData,
      [e.target.id]: e.target.value
    });
  };

  const handleSubmit = () => {
    if (formData.password == formData.passwordConfirm) {
      PostAddUser(formData);
    } else {
      alert("Your passwords don't match!");
    }
  };

  const classes = useStyles();
  return (
    <div data-testid="registerForm">
      <form className={classes.container}>
        <h1 align="center">Register</h1>
        <TextField
          id="firstName"
          label="First Name"
          variant="outlined"
          m={20}
          onChange={handleChange}
        />
        <TextField
          id="lastName"
          label="Last Name"
          variant="outlined"
          m={20}
          onChange={handleChange}
        />
        <TextField
          id="email"
          label="Email"
          variant="outlined"
          m={20}
          onChange={handleChange}
        />
        <TextField
          id="password"
          label="Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
        />
        <TextField
          id="passwordConfirm"
          label="Confirm Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
        />
        <Button
          variant="outlined"
          className={classes.button}
          onClick={() => handleSubmit()}
        >
          Complete Registration
        </Button>
        <small className={classes.text}>
          Already have an account? Log in <Link to="/login">here</Link>
        </small>
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

export default RegisterForm;
