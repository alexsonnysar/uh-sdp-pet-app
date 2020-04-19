import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Button } from '@material-ui/core';
import PropTypes from 'prop-types';

const useStyles = makeStyles((theme) => ({
  button: {
    margin: theme.spacing(1),
  },
}));

const ListButton = ({ handleClick, label, color, icon }) => {
  const classes = useStyles();

  const [loading, setLoading] = useState(false);

  return (
    <Button
      onClick={() => {
        setLoading(true);
        handleClick();
        setLoading(false);
      }}
      variant="contained"
      className={classes.button}
      color={color}
      size="small"
      disabled={loading}
      startIcon={icon}
    >
      {label}
    </Button>
  );
};

ListButton.propTypes = {
  handleClick: PropTypes.func.isRequired,
  label: PropTypes.string.isRequired,
  color: PropTypes.string.isRequired,
  icon: PropTypes.node.isRequired,
};
export default ListButton;
