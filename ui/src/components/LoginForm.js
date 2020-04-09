import React, { useState } from 'react';
import PropTypes from 'prop-types';
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
  link: {
    textDecorationLine: 'none',
  },
}));

const LoginForm = (props) => {
  const history = useHistory();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [loading, setLoading] = useState(false);
  const [isError, setError] = useState(false);

  const handleError = () => {
    setError(true);
  };
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
        if (localStorage.getItem('roles') === 'ROLE_User') {
          window.location.replace('http://localhost:3000/user-dashboard');
        } else {
          window.location.replace('http://localhost:3000/employee-dashboard');
        }
      })
      .catch(handleError)
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
    if (formData.email.length === 0 || formData.password.length === 0) {
      setError(true);
    }
    if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(formData.email)) {
      setError(true);
    } else {
      PostLoginUser(formData);
      setError(false);
      if (localStorage.getItem('jwt') !== null) {
        props.handleAuth(true);
        history.replace('/');
      }
    }
  };

  const classes = useStyles();
  return (
    <div data-testid="loginForm">
      <form className={classes.container} onSubmit={handleSubmit}>
        <h1 align="center">Log In</h1>
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Correct Email' : ''}
          data-testid="email"
          id="email"
          label="Email"
          variant="outlined"
          m={20}
          onChange={handleChange}
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
        />
        <Button
          variant="outlined"
          className={classes.button}
          onClick={() => handleSubmit()}
          disabled={loading}
        >
          Log In
        </Button>
        <small className={classes.text}>
          {/* eslint-disable-next-line react/jsx-one-expression-per-line */}
          Dont have an account? Register <Link to="/register">here</Link>
        </small>
      </form>
    </div>
  );
};

LoginForm.propTypes = {
  handleAuth: PropTypes.func.isRequired,
};

export default LoginForm;
