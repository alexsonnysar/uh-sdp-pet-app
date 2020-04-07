import fetchData from './fetchData';

export const getAllPets = url => fetchData(url).then(res => res.data);
