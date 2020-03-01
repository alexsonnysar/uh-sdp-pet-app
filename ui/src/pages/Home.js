import React, { useEffect, useState } from "react";
import PetCard from "../components/PetCard";
import { CardDeck } from "react-bootstrap";
import ManyPetCards from "../components/ManyPetCards";

const Home = () => {
  const [petList, setPetList] = useState([]);

  async function fetchData() {
    const res = await fetch("http://localhost:8080/pet"); // fetch("https://swapi.co/api/planets/4/");
    res.json().then(res => setPetList(res));
  }

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <ManyPetCards petList={petList} />
    </div>
  );
};

export default Home;
