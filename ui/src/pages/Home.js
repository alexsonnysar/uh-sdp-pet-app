import React, { useEffect, useState } from "react";
import ManyPetCards from "../components/ManyPetCards";
import Navigation from "../components/Navigation";
import CssBaseline from "@material-ui/core/CssBaseline/CssBaseline";

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
      <CssBaseline />
      <Navigation />
      <ManyPetCards petList={petList} />
    </div>
  );
};

export default Home;
