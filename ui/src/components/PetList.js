import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import PropTypes from 'prop-types';
import PetListItem from './PetListItem';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    maxWidth: '95%',
    minWidth: 320,
    flexBasis: 'auto',
  },
  title: {
    margin: theme.spacing(4, 0, 2),
  },
  paper: {
    maxHeight: 555,
    overflow: 'auto',
  },
}));

const PetList = ({ heading, petList, deletePet }) => {
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="petlist">
      <Typography variant="h6" className={classes.title}>
        {heading}
      </Typography>
      {petList.length !== 0 ? (
        <Paper className={classes.paper}>
          <List>
            {petList.map((pet) => (
              <PetListItem removePet={deletePet} pet={pet} key={pet.id} />
            ))}
          </List>
        </Paper>
      ) : (
        <div data-testid="emptyList">
          <Typography variant="subtitle1">No more pets available</Typography>
        </div>
      )}
    </div>
  );
};

PetList.propTypes = {
  heading: PropTypes.string.isRequired,
  petList: PropTypes.arrayOf(PropTypes.object).isRequired,
  deletePet: PropTypes.func.isRequired,
};

export default PetList;
