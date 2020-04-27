import React from 'react';
import PropTypes from 'prop-types';
import { Redirect, Route } from 'react-router-dom';

const PrivateRoute = ({ auth, children, ...rest }) => {
  console.log(auth);
  return (
    <Route
      {...rest}
      render={({ location }) =>
        auth ? children : <Redirect to={{ pathname: '/', state: { from: location } }} />
      }
    />
  );
};

PrivateRoute.propTypes = {
  auth: PropTypes.bool.isRequired,
  children: PropTypes.node.isRequired,
};

export default PrivateRoute;
