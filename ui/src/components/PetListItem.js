import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import DeleteIcon from '@material-ui/icons/Delete';
import UpdateRoundedIcon from '@material-ui/icons/UpdateRounded';
import Button from '@material-ui/core/Button';
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
    axios({
      method: 'put',
      url: `http://localhost:8080/pet/${id}`,
      headers: headersAxios,
      data: petData,
    })
      .then((response) => response.data)
      .catch((error) => {
        throw error;
      });
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
    <ListItemLink href={`pet-profile/${id}`} data-testid="petlistitem">
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
          href="pet-profile"
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
