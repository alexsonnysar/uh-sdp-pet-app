import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import DeleteIcon from "@material-ui/icons/Delete";
import UpdateRoundedIcon from "@material-ui/icons/UpdateRounded";
import Button from "@material-ui/core/Button";
import axios from "axios";

const PetListItem = ({ pet, deletePet }) => {
  const { name, type, id } = pet;
  // console.log(id)
  const [loading, setLoading] = useState(false);
  const classes = useStyles();
  const CallDeletePet = () => {
    console.log("pet was deleted");
    //   axios({
    //     method: 'delete',
    //     url: `http://localhost:8080/pet/${id}`,
    //     headers: {'Content-Type':'application/json'}
    //   }).then(response => console.log(response))
    //   .catch(error => console.log(error));
  };
  const RemoveThisPet = () => {
    console.log(id)
  }
  const handleDelete = () => {
    setLoading(true);
    RemoveThisPet(id);
    CallDeletePet(id);
  };

  return (
    <ListItemLink href="pet-profile" data-testid="petlistitem">
      <ListItemText primary={name} secondary={type} />
      <ListItemSecondaryAction>
        <Button
          onClick={id => handleDelete()}
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

const useStyles = makeStyles(theme => ({
  button: {
    margin: theme.spacing(1)
  }
}));

const ListItemLink = props => {
  return <ListItem button component="a" {...props} />;
};

export default PetListItem;
