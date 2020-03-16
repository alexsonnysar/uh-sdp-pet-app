import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import ListItem from "@material-ui/core/ListItem";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import ListItemText from "@material-ui/core/ListItemText";
import DeleteIcon from "@material-ui/icons/Delete";
import UpdateRoundedIcon from "@material-ui/icons/UpdateRounded";
import Button from "@material-ui/core/Button";

const PetListItem = ({ pet }) => {
  const { name, type } = pet;
  const classes = useStyles();

  return (
    <ListItemLink href="pet-profile" data-testid="petlistitem">
      <ListItemText primary={name} secondary={type} />
      <ListItemSecondaryAction>
        <Button
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

function ListItemLink(props) {
  return <ListItem button component="a" {...props} />;
}

export default PetListItem;
