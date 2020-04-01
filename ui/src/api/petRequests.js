import fetchData from "./fetchData";

export const getAllPets = url => {
  return fetchData(url).then(res => res.data);
};
