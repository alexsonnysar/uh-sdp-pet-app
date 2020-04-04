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

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1)
  }
}));

const PetListItem = ({ pet, removePet }) => {
  pet.propTypes = {
    name: PropTypes.string,
    type: PropTypes.string,
    sex: PropTypes.string,
    age: PropTypes.string,
    size: PropTypes.string,
    weight: PropTypes.number,
    dateAdded: PropTypes.date,
    description: PropTypes.string,
    imageNames: [PropTypes.string],
    adopted: PropTypes.bool,
    active: PropTypes.bool
  };

  const { name, type, id } = pet;
  const [loading, setLoading] = useState(false);
  const classes = useStyles();

  const headers = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`
  };

  const CallDeletePet = petData => {
    axios({
      method: 'put',
      url: `http://localhost:8080/pet/${id}`,
      headers,
      data: petData
    })
      .then(
        () => {
          alert(
            `${petData.name} is gone now...\n Please tell johnny he went to live with uncle Ben on the farm.`
          );
        }
        // (response) => console.log(response)
      )
      .catch(() => {
        alert(
          'Hey 🧐! Who let you in here? We are not deleting any pets till we get this sorted out.😤'
        );
      });
  };

  const handleDelete = () => {
    const petData = {
      ...pet,
      isActive: false
    };
    setLoading(true);
    removePet(id);
    CallDeletePet(petData);
  };

  return (
    <ListItemLink href="pet-profile" data-testid="petlistitem">
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

const ListItemLink = props => <ListItem button component="a" {...props} />;

export default PetListItem;
