import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import PropTypes from 'prop-types';
import RequestListItem from './RequestListItem';

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

const RequestList = ({ heading, requestList, putRequest }) => {
  const classes = useStyles();
  return (
    <div className={classes.root} data-testid="requestlist">
      <Typography variant="h6" className={classes.title}>
        {heading}
      </Typography>
      <Paper className={classes.paper}>
        <List>
          {requestList
            .filter((r) => r.status === 'PENDING')
            .map((requests) => (
              <RequestListItem
                requestUpdated={putRequest}
                requests={requests}
                key={requests.id}
              />
            ))}
        </List>
      </Paper>
    </div>
  );
};

RequestList.propTypes = {
  heading: PropTypes.string.isRequired,
  requestList: PropTypes.arrayOf(PropTypes.object).isRequired,
  putRequest: PropTypes.func,
};

RequestList.defaultProps = {
  putRequest: () => null,
};

export default RequestList;
