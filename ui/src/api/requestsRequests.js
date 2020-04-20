import fetchData from './fetchData';
import axios from 'axios';

const headersAxios = {
  'Content-Type': 'application/json',
  Authorization: `Bearer ${localStorage.getItem('jwt')}`,
};

export const getAllRequests = (requestUrl) => {
  axios.get(requestUrl, {
    headers: headersAxios,
  });
};
