import React from "react";
import PetCardList from "../components/PetCardList";
import { FetchData } from "../api/FetchData";

const Home = () => {
  const url = "http://localhost:8080/pet";

  const petList = FetchData(url);
  return (
    <div>
      {petList.length > 0 ? (
        <div data-testid="loaded">
          <PetCardList petList={petList} />
        </div>
      ) : (
        <h3 data-testid="loading">No pets to show :(</h3>
      )}
    </div>
  );
};

export default Home;
