import React from 'react';
import PropTypes from 'prop-types';
import { Snackbar } from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';

const SuccessRequestMsg = ({ handleClose, open, successMsg }) => {
  return (
    <div>
      <Snackbar
        open={open}
        autoHideDuration={6000}
        onClose={() => handleClose()}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <MuiAlert
          elevation={6}
          variant="filled"
          severity="success"
          onClose={() => handleClose()}
        >
          {successMsg}
        </MuiAlert>
      </Snackbar>
    </div>
  );
};

SuccessRequestMsg.propTypes = {
  handleClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  successMsg: PropTypes.string.isRequired,
};

export default SuccessRequestMsg;
