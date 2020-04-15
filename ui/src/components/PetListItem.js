import React from 'react';
import {
  ListItem,
  ListItemText,
  ListItemAvatar,
  Avatar,
  ListItemSecondaryAction,
} from '@material-ui/core';
import DeleteIcon from '@material-ui/icons/Delete';
import UpdateRoundedIcon from '@material-ui/icons/UpdateRounded';
import BlockIcon from '@material-ui/icons/Block';
import CheckIcon from '@material-ui/icons/Check';
import axios from 'axios';
import PropTypes from 'prop-types';
import ListButton from './ListButton';

const PetListItem = ({
  pet,
  actionButton,
  approveButton,
  rejectButton,
  deleteButton,
  updateButton,
}) => {
  const { name, type, id } = pet;

  const headersAxios = {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
  };

  const handleError = () => {};
  const CallDeletePet = (petData) => {
    axios({
      method: 'put',
      url: `http://localhost:8080/pet/${id}`,
      headers: headersAxios,
      data: petData,
    })
      .then((response) => response.data)
      .catch(handleError);
  };
  const handleUpdate = () => {
    axios.get('http://localhost:3000/pet');
    // open register pet and fill the form with current pet data
  };
  const handleDelete = () => {
    const petData = {
      ...pet,
      active: false,
    };
    actionButton(id);
    CallDeletePet(petData);
  };
  // const handleAction = () => {};
  return (
    <ListItemLink href={`pet-profile/${id}`} data-testid="petlistitem">
      <ListItemAvatar>
        <Avatar alt="Pet" src="/images/garfield.jpg" />
      </ListItemAvatar>
      <ListItemText primary={name} secondary={type} />
      <ListItemSecondaryAction>
        {deleteButton && (
          <ListButton
            handleClick={() => handleDelete()}
            label="Delete"
            color="secondary"
            icon={<DeleteIcon />}
          />
        )}
        {updateButton && (
          <ListButton
            handleClick={() => handleUpdate()}
            label="Update"
            color="primary"
            icon={<UpdateRoundedIcon />}
          />
        )}
        {approveButton && (
          <ListButton
            handleClick={() => handleDelete()}
            label="Approve"
            color="secondary"
            icon={<CheckIcon />}
          />
        )}
        {rejectButton && (
          <ListButton
            handleClick={() => handleUpdate()}
            label="Reject"
            color="primary"
            icon={<BlockIcon />}
          />
        )}
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
  actionButton: PropTypes.func,
  deleteButton: PropTypes.bool,
  updateButton: PropTypes.bool,
  approveButton: PropTypes.bool,
  rejectButton: PropTypes.bool,
};

PetListItem.defaultProps = {
  actionButton: () => null,
  deleteButton: false,
  updateButton: false,
  approveButton: false,
  rejectButton: false,
};
export default PetListItem;
