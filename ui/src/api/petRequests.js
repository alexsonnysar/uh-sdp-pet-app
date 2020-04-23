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
  return axios.post(favUrl, favData, { headers: reqHeaders });
};

export const getAllRequestedPets = (requestedPetsUrl) => {
  return axios.get(requestedPetsUrl, { headers: reqHeaders });
};
