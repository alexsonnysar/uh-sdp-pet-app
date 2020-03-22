import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";

const RegisterForm = () => {
  const classes = useStyles();
  return (
    <div>
      <form className={classes.container}>
        <h1 align="center">Register</h1>
        <TextField
          id="outlined-basic"
          label="First Name"
          variant="outlined"
          m={20}
        />
        <TextField
          id="outlined-basic"
          label="Last Name"
          variant="outlined"
          m={20}
        />
        <TextField
          id="outlined-basic"
          label="Email"
          variant="outlined"
          m={20}
        />
        <TextField
          id="outlined-password-input"
          label="Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
        />
        <TextField
          id="outlined-password-input"
          label="Confirm Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
        />
        <Button variant="outlined" className={classes.button}>
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
