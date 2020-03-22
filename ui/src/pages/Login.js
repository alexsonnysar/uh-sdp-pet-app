import React from "react";
import LoginForm from "../components/LoginForm";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";

const Login = () => {
  return (
    <div>
      <Grid container spacing={3} justify="center">
        <Grid item xs>
          <Paper>xs</Paper>
        </Grid>
        <Grid item xs>
          <LoginForm />
          {/* <h1 align="center">Hello</h1> */}
        </Grid>
        <Grid item xs>
          <Paper>xs</Paper>
        </Grid>
      </Grid>
    </div>
  );
};

export default Login;
