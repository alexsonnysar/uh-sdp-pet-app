import axios from 'axios';

export const getAllPets = (url) => {
  return axios.get(url);
};

export const getAllFavs = (favUrl, reqHeaders) => {
  return axios.get(favUrl, { headers: reqHeaders });
};

export const getAllRecents = (recUrl, reqHeaders) => {
  return axios.get(recUrl, { headers: reqHeaders });
};

export const getAllRequestedPets = (requestedPetsUrl, reqHeaders) => {
  return axios.get(requestedPetsUrl, { headers: reqHeaders });
};

export const getSinglePet = (url) => {
  return axios.get(url);
};

export const favoritePet = (favUrl, favData, reqHeaders) => {
  return axios.post(favUrl, favData, { headers: reqHeaders });
};

export const unfavoritePet = (favUrl, favData, reqHeaders) => {
  return axios.put(favUrl, favData, { headers: reqHeaders });
};

export const requestAdoptPet = (reqUrl, reqData, reqHeaders) => {
  return axios.post(reqUrl, reqData, { headers: reqHeaders });
};

export const cancelAdoptRequest = (reqUrl, reqData, reqHeaders) => {
  return axios.post(reqUrl, reqData, { headers: reqHeaders });
};

export const addRecent = (recUrl, reqHeaders) => {
  return axios.post(recUrl, {}, { headers: reqHeaders });
};
