import axios from 'axios';

const reqHeaders = {
  'Content-Type': 'application/json',
  Authorization: `Bearer ${localStorage.getItem('jwt')}`,
};

export const getAllPets = (url) => {
  return axios.get(url);
};

export const getSinglePet = (url) => {
  return axios.get(url);
};

export const favoritePet = (favUrl, favData) => {
  return axios({
    method: 'POST',
    url: favUrl,
    headers: reqHeaders,
    data: favData,
  });
};

export const getAllRequestedPets = (requestedPetsUrl) => {
  return axios({
    method: 'GET',
    url: requestedPetsUrl,
    headers: reqHeaders,
  });
};
