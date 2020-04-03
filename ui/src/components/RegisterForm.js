import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { Link } from 'react-router-dom';
import axios from 'axios';

const useStyles = makeStyles(theme => ({
  container: {
    '& > *': {
      margin: theme.spacing(1)
    },
    width: '20rem',
    display: 'flex',
    flexDirection: 'column'
  },
  button: {
    color: 'primary'
  },
  text: {
    textAlign: 'center'
  }
}));

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    passwordConfirm: '',
    name: ''
  });

  const [loading, setLoading] = useState(false);

  const PostAddUser = userData => {
    setLoading(true);
    axios({
      method: 'post',
      url: 'http://localhost:8080/signup',
      data: userData,
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => {
        console.log(response);
        PostLoginUser(formData);
      })
      .catch(error => console.log(error))
      .finally(() => {
        setLoading(false);
      });
  };

  const PostLoginUser = userData => {
    setLoading(true);
    axios({
      method: 'post',
      url: 'http://localhost:8080/signin',
      data: userData,
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => {
        console.log(response.data);
        window.localStorage.setItem('jwt', response.data.jwt);
        window.localStorage.setItem('roles', response.data.roles);
        if (localStorage.getItem('roles') === 'ROLE_User') {
          window.location.replace('http://localhost:3000/user-dashboard');
        } else {
          window.location.replace('http://localhost:3000/employee-dashboard');
        }
      })
      .catch(error => {
        console.log(error);
        alert('Incorrect Username or Password');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleChange = e => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value
    });
  };

  const handleSubmit = () => {
    if (formData.password === formData.passwordConfirm) {
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
          id="name"
          label="Name"
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
          disabled={loading}
          data-testid="submit"
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

export default RegisterForm;
