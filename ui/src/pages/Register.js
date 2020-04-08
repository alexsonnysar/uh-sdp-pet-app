import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import RegisterForm from '../components/RegisterForm';

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
    <div className={classes.root} data-testid="register">
      <RegisterForm />
    </div>
  );
};

export default Register;
