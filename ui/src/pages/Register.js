import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import RegisterForm from "../components/RegisterForm";

const Register = () => {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <RegisterForm />
    </div>
  );
};

const useStyles = makeStyles({
  root: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center"
  }
});

export default Register;
