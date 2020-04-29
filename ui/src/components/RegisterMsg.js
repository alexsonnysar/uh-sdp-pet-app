import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import MuiAlert from '@material-ui/lab/Alert';
import { Button, Snackbar, makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
  regButton: {
    color: 'inherit',
    textDecorationLine: 'none',
  },
});

const RegisterMsg = ({ open, handleClose, msg }) => {
  const classes = useStyles();
  return (
    <Snackbar
      open={open}
      autoHideDuration={6000}
      onClose={handleClose}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
    >
      <MuiAlert
        elevation={6}
        variant="filled"
        onClose={handleClose}
        severity="warning"
        action={
          <Link to="/register" className={classes.regButton}>
            <Button className={classes.regButton}>Register</Button>
          </Link>
        }
      >
        {msg}
      </MuiAlert>
    </Snackbar>
  );
};

RegisterMsg.propTypes = {
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  msg: PropTypes.string.isRequired,
};

export default RegisterMsg;
