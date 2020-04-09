import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import PetListItem from './PetListItem';

// svn test commit
const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    maxWidth: '95%',
    minWidth: 320,
    flexBasis: 'auto'
  },
  title: {
    margin: theme.spacing(4, 0, 2)
  },
  paper: {
    maxHeight: 555,
    overflow: 'auto'
  }
}));

const PetList = ({ heading, petList, deletePet }) => {
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="petlist">
      <Typography variant="h6" className={classes.title}>
        {heading}
      </Typography>
      <Paper className={classes.paper}>
        <List>
          {petList.map(pet => (
            <PetListItem removePet={deletePet} pet={pet} key={pet.id} />
          ))}
        </List>
      </Paper>
    </div>
  );
};

export default PetList;
