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
import DeleteIcon from '@material-ui/icons/Delete';
import UpdateRoundedIcon from '@material-ui/icons/UpdateRounded';
import axios from 'axios';
import PropTypes from 'prop-types';

const useStyles = makeStyles((theme) => ({
  button: {
    margin: theme.spacing(1),
  },
}));

const PetListItem = ({ pet, removePet }) => {
  const { name, type, id } = pet;
  const [loading, setLoading] = useState(false);
  const classes = useStyles();

  const headersAxios = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const CallDeletePet = (petData) => {
    const handleError = () => {};
    axios({
      method: 'put',
      url: `http://localhost:8080/pet/${id}`,
      headers: headersAxios,
      data: petData,
    })
      .then((response) => response.data)
      .catch(handleError);
  };

  const handleDelete = () => {
    const petData = {
      ...pet,
      active: false,
    };
    setLoading(true);
    removePet(id);
    CallDeletePet(petData);
  };

  return (
    <ListItemLink href={`/pet-profile/${id}`} data-testid="petlistitem">
      <ListItemAvatar>
        <Avatar alt="Pet" src="/images/garfield.jpg" />
      </ListItemAvatar>
      <ListItemText primary={name} secondary={type} />
      <ListItemSecondaryAction>
        <Button
          onClick={() => handleDelete()}
          disabled={loading}
          variant="contained"
          className={classes.button}
          color="secondary"
          size="small"
          startIcon={<DeleteIcon />}
        >
          Delete
        </Button>
        <Button
          href={`edit-pet/${id}`}
          variant="contained"
          className={classes.button}
          color="primary"
          size="small"
          startIcon={<UpdateRoundedIcon />}
        >
          Update
        </Button>
      </ListItemSecondaryAction>
    </ListItemLink>
  );
};

const ListItemLink = (props) => <ListItem button component="a" {...props} />;

PetListItem.propTypes = {
  pet: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
    type: PropTypes.string,
  }).isRequired,
  removePet: PropTypes.func,
};

PetListItem.defaultProps = {
  removePet: () => null,
};

export default PetListItem;
