import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
  Button,
  ListItem,
  ListItemText,
  ListItemAvatar,
  Avatar,
  ListItemSecondaryAction,
} from '@material-ui/core';
import CheckIcon from '@material-ui/icons/Check';
import CloseIcon from '@material-ui/icons/Close';
import axios from 'axios';
import PropTypes from 'prop-types';

const useStyles = makeStyles((theme) => ({
  button: {
    margin: theme.spacing(1),
  },
}));

const RequestListItem = ({ requests, requestUpdated }) => {
  const { id, petId, userEmail, petName, status } = requests;
  const [loading, setLoading] = useState(false);
  const classes = useStyles();

  const headersAxios = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const handleError = () => {};

  const CallPutRequest = (requestData) => {
    axios({
      method: 'put',
      url: `http://localhost:8080/request/${id}`,
      headers: headersAxios,
      data: requestData,
    })
      .then((response) => response.data)
      .catch(handleError);
  };

  const handleDenied = () => {
    setLoading(true);
    requestUpdated(id);
    CallPutRequest('CANCELED');
  };

  const handleApproved = () => {
    setLoading(true);
    requestUpdated(id);
    CallPutRequest('APPROVED');
  };

  return (
    <ListItemLink href={`pet-profile/${id}`} data-testid="requestlistitem">
      <ListItemAvatar>
        <Avatar alt="Pet" src="/images/garfield.jpg" />
      </ListItemAvatar>
      <ListItemText primary={petName} secondary={`Requested By: ${userEmail}`} />
      <ListItemSecondaryAction>
        <Button
          onClick={() => handleDenied()}
          disabled={loading}
          variant="contained"
          className={classes.button}
          color="secondary"
          size="small"
          startIcon={<CloseIcon />}
        >
          Deny
        </Button>
        <Button
          onClick={() => handleApproved()}
          disabled={loading}
          variant="contained"
          className={classes.button}
          color="primary"
          size="small"
          startIcon={<CheckIcon />}
        >
          Approve
        </Button>
      </ListItemSecondaryAction>
    </ListItemLink>
  );
};

const ListItemLink = (props) => <ListItem button component="a" {...props} />;

RequestListItem.propTypes = {
  requests: PropTypes.shape({
    id: PropTypes.string,
    petId: PropTypes.string,
    userEmail: PropTypes.string,
    petName: PropTypes.string,
    status: PropTypes.string,
  }).isRequired,
  requestUpdated: PropTypes.func,
};

RequestListItem.defaultProps = {
  requestUpdated: () => null,
};

export default RequestListItem;
