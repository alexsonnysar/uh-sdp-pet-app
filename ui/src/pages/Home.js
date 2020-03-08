import React, { useEffect, useState } from "react";
import ManyPetCards from "../components/ManyPetCards";
import Navigation from "../components/Navigation";

const Home = () => {
  const [petList, setPetList] = useState([]);

  async function fetchData() {
    const res = await fetch("http://localhost:8080/pet"); // fetch("https://swapi.co/api/planets/4/");
    res
      .json()
      .then(res => setPetList(res))
      .catch(err => console.log(err));
  }

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      {petList.length > 0 ? (
        <ManyPetCards petList={petList} />
      ) : (
        <h3>No pets to show :(</h3>
      )}
    </div>
  );
};

export default Home;
