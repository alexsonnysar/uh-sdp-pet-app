import fetchData from "./fetchData";

const getAllPets = url => fetchData(url).then(res => res.data);

export default getAllPets;
