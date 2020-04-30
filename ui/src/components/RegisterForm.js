import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { Link, useHistory } from 'react-router-dom';
import axios from 'axios';
import ErrorReqMsg from './ErrorReqMsg';

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
  const [emailCheck, setEmailCheck] = useState(false);

  const PostLoginUser = (userData) => {
    const handleError = () => {};
    const signInUrl = 'http://localhost:8080/signin';
    const axiosHeaders = {
      headers: { 'Content-Type': 'application/json' },
    };
    setLoading(true);
    axios
      .post(signInUrl, userData, { headers: axiosHeaders })
      .then((response) => {
        window.localStorage.setItem('jwt', response.data.jwt);
        window.localStorage.setItem('roles', response.data.roles);
        window.localStorage.setItem('userId', response.data.id);
        window.localStorage.setItem('username', response.data.email);
        props.handleAuth(true);
        if (localStorage.getItem('roles') === 'ROLE_User') {
          history.replace('/user/dashboard');
        } else {
          history.replace('/employee/dashboard');
        }
      })
      .catch(handleError)
      .finally(() => {
        setLoading(false);
      });
  };

  const PostAddUser = (userData) => {
    const signupUrl = 'http://localhost:8080/signup';
    const axiosHeaders = {
      headers: { 'Content-Type': 'application/json' },
    };
    const handleError = () => {};
    setLoading(true);
    axios
      .post(signupUrl, userData, { headers: axiosHeaders })
      .then((res) => {
        if (res.data.message === 'Error: Email is already in use!') {
          setEmailCheck(true);
        } else {
          PostLoginUser(userData);
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

  const handleSubmit = (e) => {
    e.preventDefault();
    if (
      !formData.name ||
      !formData.email ||
      !formData.password ||
      !formData.passwordConfirm
    ) {
      setError(true);
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(formData.email)) {
      setError(true);
    } else if (formData.password !== formData.passwordConfirm) {
      setError(true);
    } else if (formData.password === formData.passwordConfirm) {
      PostAddUser({ ...formData, email: formData.email.toLowerCase() });
    }
  };
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setEmailCheck(false);
  };

  const classes = useStyles();
  return (
    <div data-testid="registerForm">
      <form className={classes.container} onSubmit={handleSubmit}>
        <h1 align="center">Register</h1>
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Name' : ''}
          id="name"
          data-testid="name"
          label="Name"
          variant="outlined"
          m={20}
          value={formData.name}
          onChange={(e) => handleChange(e)}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Correct Email' : ''}
          id="email"
          label="Email"
          variant="outlined"
          m={20}
          value={formData.email}
          onChange={(e) => handleChange(e)}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Please Enter a Correct Password' : ''}
          id="password"
          label="Password-Field"
          type="password"
          autoComplete="current-password"
          variant="outlined"
          value={formData.password}
          onChange={(e) => handleChange(e)}
          required
        />
        <TextField
          error={isError}
          helperText={isError ? 'Your Passwords Must Match' : ''}
          id="passwordConfirm"
          label="Confirm Password"
          type="password"
          autoComplete="confirm-password"
          variant="outlined"
          value={formData.passwordConfirm}
          onChange={(e) => handleChange(e)}
          required
        />
        <Button
          variant="outlined"
          className={classes.button}
          onClick={(e) => handleSubmit(e)}
          disabled={loading}
          data-testid="submit"
        >
          Complete Registration
        </Button>
        <ErrorReqMsg
          open={emailCheck}
          handleClose={() => handleClose()}
          errorMsg="Email is already in use! Please try again."
        />
        <small className={classes.text}>
          {/* eslint-disable-next-line react/jsx-one-expression-per-line */}
          Already have an account? Log in <Link to="/login">here</Link>
        </small>
      </form>
    </div>
  );
};

RegisterForm.propTypes = {
  handleAuth: PropTypes.func.isRequired,
};

export default RegisterForm;
