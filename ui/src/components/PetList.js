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

const PetList = ({
  heading,
  petList,
  action,
  deleteButton,
  updateButton,
  approveButton,
  rejectButton,
}) => {
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="petlist">
      <Typography variant="h6" className={classes.title}>
        {heading}
      </Typography>
      <Paper className={classes.paper}>
        <List>
          {petList.map((pet) => (
            <PetListItem
              actionButton={action}
              pet={pet}
              key={pet.id}
              deleteButton={deleteButton}
              updateButton={updateButton}
              approveButton={approveButton}
              rejectButton={rejectButton}
            />
          ))}
        </List>
      </Paper>
    </div>
  );
};

PetList.propTypes = {
  heading: PropTypes.string.isRequired,
  petList: PropTypes.arrayOf(PropTypes.object).isRequired,
  action: PropTypes.func.isRequired,
  deleteButton: PropTypes.bool,
  updateButton: PropTypes.bool,
  approveButton: PropTypes.bool,
  rejectButton: PropTypes.bool,
};

PetList.defaultProps = {
  deleteButton: false,
  updateButton: false,
  approveButton: false,
  rejectButton: false,
};

export default PetList;
