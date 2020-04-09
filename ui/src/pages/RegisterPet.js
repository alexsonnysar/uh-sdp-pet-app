import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import RegisterPetForm from '../components/RegisterPetForm';

const useStyles = makeStyles({
  root: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const Register = () => {
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="registerPet">
      <RegisterPetForm />
    </div>
  );
};

export default Register;
