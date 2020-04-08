import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import LoginForm from '../components/LoginForm';

const useStyles = makeStyles({
  root: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  }
});

const Login = props => {
  const classes = useStyles();

  return (
    <div data-testid="login" className={classes.root}>
      <LoginForm handleAuth={props.handleAuth} />
    </div>
  );
};

export default Login;
