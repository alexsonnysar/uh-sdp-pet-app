import React from 'react';
import PropTypes from 'prop-types';
import { Typography } from '@material-ui/core';
import RequestCard from './RequestCard';
import './Slider.css';

const RequestCardSlider = ({ requestList, heading }) => {
  return (
    <div data-testid="requestCardSlider">
      <div>
        <Typography variant="h6">{heading}</Typography>
      </div>
      {requestList.length !== 0 ? (
        <div>
          <div className="root">
            {requestList.map((request) => (
              <RequestCard key={request.id} request={request} />
            ))}
          </div>
        </div>
      ) : (
        <div className="media">
          <Typography variant="subtitle1">No Pets to show :(</Typography>
        </div>
      )}
    </div>
  );
};

RequestCardSlider.propTypes = {
  requestList: PropTypes.arrayOf(PropTypes.object).isRequired,
  heading: PropTypes.string.isRequired,
};

export default RequestCardSlider;
