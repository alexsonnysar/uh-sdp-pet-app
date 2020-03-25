import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import RegisterPetForm from "../components/RegisterPetForm";

const Register = () => {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <RegisterPetForm />
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
