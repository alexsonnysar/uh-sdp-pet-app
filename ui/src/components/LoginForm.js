import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

const LoginForm = () => {
  const classes = useStyles();
  return (
    <div>
      <form className={classes.container}>
        <h1 align="center">Log In</h1>
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
        <Button variant="outlined" className={classes.button}>
          Log In
        </Button>
        <small className={classes.text}>
          Don't have an account? Register <a href="/register">here</a>
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    color: "primary"
  },
  text: {
    textAlign: "center"
  }
}));

export default LoginForm;
