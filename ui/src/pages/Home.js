import React, { useEffect, useState } from "react";
import ManyPetCards from "../components/PetCardList";
import axios from "axios";

const Home = () => {
  const [petList, setPetList] = useState([]);
  const url = "http://localhost:8080/pet";

  async function fetchData() {
    await axios
      .get(url)
      .then(res => setPetList(res.data))
      .catch(err => {
        console.log(err);
      });
  }

  useEffect(() => {
    fetchData();
  }, [url]);

  return (
    <div>
      {petList.length > 0 ? (
        <div data-testid="loaded">
          <ManyPetCards petList={petList} />
        </div>
      ) : (
        <h3 data-testid="loading">No pets to show :(</h3>
      )}
    </div>
  );
};

export default Home;
