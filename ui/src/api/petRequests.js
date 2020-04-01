import fetchData from "./fetchData";

const getAllPets = url => {
  return fetchData(url).then(res => res.data);
};

export default getAllPets;
