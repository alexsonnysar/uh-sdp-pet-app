import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import RegisterPetForm from '../components/RegisterPetForm';

const useStyles = makeStyles({
  root: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  }
});

const Register = () => {
  const classes = useStyles();
  if (localStorage.getItem('roles') === 'ROLE_Employee') {
    return (
      <div className={classes.root} data-testid="registerPet">
        <RegisterPetForm />
      </div>
    );
  } else if (localStorage.getItem('roles') === 'ROLE_User') {
    window.location.replace('http://localhost:3000/user-dashboard');
  } else {
    window.location.replace('http://localhost:3000/login');
  }
};

export default Register;
