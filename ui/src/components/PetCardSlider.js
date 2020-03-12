import React from "react";
import PetCard from "./PetCard";
import "./PetCardSlider.css";

const PetCardSlider = ({ petList }) => {
  return (
    <div class="root">
      {petList.map(pet => (
        <PetCard key={pet.id} pet={pet} />
      ))}
    </div>
  );
};

export default PetCardSlider;
