import axios from "axios";

const fetchData = url => {
  return axios.get(url);
};
export default fetchData;
