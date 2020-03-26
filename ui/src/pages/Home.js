import React, { useState, useEffect } from "react";
import PetCardList from "../components/PetCardList";
import { fetchData } from "../api/fetchData";

const Home = () => {
  const url = "http://localhost:8080/pet";

  const [petList, setPetList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchData(url)
      .then(petList => setPetList(petList))
      .catch(error => console.log(error))
      .finally(() => setLoading(false));
  }, []);
  return (
    <div>
      {loading ? (
        <h3 data-testid="loading">Loading ...</h3>
      ) : (
        <div data-testid="loaded">
          <PetCardList petList={petList} />
        </div>
      )}
    </div>
  );
};

export default Home;
