import React, { useEffect, useState } from "react";
import PetCard from "../components/PetCard";
import axios from "axios";

const Home = () => {
  const [petList, setPetList] = useState([]);

  async function fetchData() {
    const res = await fetch("http://localhost:8080/pet"); // fetch("https://swapi.co/api/planets/4/");
    res
      .json()
      .then(res => setPetList(res))
  }

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      {petList.map(p => (
        <PetCard key={p.id} pet={p} />
      ))}
    </div>
  );
};

export default Home;
