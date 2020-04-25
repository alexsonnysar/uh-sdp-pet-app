import axios from 'axios';

const reqHeaders = {
  'Content-Type': 'application/json',
  Authorization: `Bearer ${localStorage.getItem('jwt')}`,
};

export const getAllPets = (url) => {
  return axios.get(url);
};

export const getAllFavs = (favUrl) => {
  return axios.get(favUrl, { headers: reqHeaders });
};

export const getAllRecents = (recUrl) => {
  return axios.get(recUrl, { headers: reqHeaders });
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

export const addRecent = (recUrl) => {
  return axios.post(recUrl, {}, { headers: reqHeaders });
};
