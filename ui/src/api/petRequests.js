import fetchData from "./fetchData";

const getAllPets = url => {
  console.log(url);
  console.log(fetchData(url));
  return fetchData(url).then(res => res.data);
};

export default getAllPets;
