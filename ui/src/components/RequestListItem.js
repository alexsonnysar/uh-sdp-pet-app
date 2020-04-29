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
  const { id, userEmail, petName, petId } = requests;
  const [loading, setLoading] = useState(false);
  const classes = useStyles();

  const headersAxios = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const handleError = () => {};

  const handleRequest = (requestData) => {
    setLoading(true);
    requestUpdated(id, petId, requestData);
    axios
      .put(`http://localhost:8080/request/${id}`, requestData, { headers: headersAxios })
      .then((response) => response.data)
      .catch(handleError);
  };

  return (
    <ListItemLink href={`/pet-profile/${petId}`} data-testid="requestlistitem">
      <ListItemAvatar>
        <Avatar alt="Pet" src="/images/garfield.jpg" />
      </ListItemAvatar>
      <ListItemText primary={petName} secondary={`Requested By: ${userEmail}`} />
      <ListItemSecondaryAction>
        <Button
          onClick={() => handleRequest('CANCELED')}
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
          onClick={() => handleRequest('APPROVED')}
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
    userEmail: PropTypes.string,
    petName: PropTypes.string,
    petId: PropTypes.string,
  }).isRequired,
  requestUpdated: PropTypes.func,
};

RequestListItem.defaultProps = {
  requestUpdated: () => null,
};

export default RequestListItem;
