import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { Link, useHistory } from 'react-router-dom';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
  container: {
    '& > *': {
      margin: theme.spacing(1),
    },
    width: '20rem',
    display: 'flex',
    flexDirection: 'column',
  },
  button: {
    color: 'primary',
  },
  text: {
    textAlign: 'center',
  },
}));

const RegisterForm = (props) => {
  const history = useHistory();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    passwordConfirm: '',
    name: '',
  });

  const [loading, setLoading] = useState(false);
  const [isError, setError] = useState(false);

  const PostLoginUser = (userData) => {
    setLoading(true);
    axios({
      method: 'post',
      url: 'http://localhost:8080/signin',
      data: userData,
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => {
        window.localStorage.setItem('jwt', response.data.jwt);
        window.localStorage.setItem('roles', response.data.roles);
        props.handleAuth(true);
        history.replace('/');
      })
      .catch((error) => {
        throw error;
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const PostAddUser = (userData) => {
    setLoading(true);
    axios({
      method: 'post',
      url: 'http://localhost:8080/signup',
      data: userData,
      headers: { 'Content-Type': 'application/json' },
    })
      .then(() => {
        PostLoginUser(formData);
      })
      .catch((error) => {
        throw error;
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value,
    });
  };

  const handleSubmit = () => {
    if (
      !formData.name ||
      !formData.email ||
      !formData.password ||
      !formData.passwordConfirm
    ) {
      setError(true);
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(formData.email)) {
      setError(true);
    } else if (
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&])$/i.test(formData.password)
    ) {
      setError(true);
    } else if (formData.password !== formData.passwordConfirm) {
      setError(true);
    } else if (formData.password === formData.passwordConfirm) {
      PostAddUser(formData);
    }
  };

  const classes = useStyles();
  return (
    <div data-testid="registerForm">
      <form className={classes.container}>
        <h1 align="center">Register</h1>
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Name' : ''}
          id="name"
          label="Name"
          variant="outlined"
          m={20}
          onChange={handleChange}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Correct Email' : ''}
          id="email"
          label="Email"
          variant="outlined"
          m={20}
          onChange={handleChange}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Correct Password' : ''}
          id="password"
          label="Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Your Passwords Must Match' : ''}
          id="passwordConfirm"
          label="Confirm Password"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
          required
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
          {/* eslint-disable-next-line react/jsx-one-expression-per-line */}
          Already have an account? Log in <Link to="/login">here</Link>
        </small>
      </form>
    </div>
  );
};

export default RegisterForm;
