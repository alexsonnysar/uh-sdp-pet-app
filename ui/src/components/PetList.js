import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import PetListItem from "./PetListItem";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";

const PetList = ({ heading, petList, deletePet }) => {
  const RemovePet = id => {
    console.log(id);
    deletePet(id);
  };
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="petlist">
      <Typography variant="h6" className={classes.title}>
        {heading}
      </Typography>
      <Paper className={classes.paper}>
        <List>
          {petList.map(pet => (
            <PetListItem  pet={pet} key={pet.id} />
          ))}
        </List>
      </Paper>
    </div>
  );
};

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    maxWidth: 650,
    minWidth: 320,
    flexBasis: "auto"
  },
  title: {
    margin: theme.spacing(4, 0, 2)
  },
  paper: {
    maxHeight: 440,
    overflow: "auto"
  }
}));

export default PetList;
