import React, { useEffect, useState } from "react";
import PetCard from "../components/PetCard";

const petProfileInfo = {
  Name: "Garfield",
  Type: "Cat"
};

const Home = () => {
  const [petList, setPet] = useState(null);
  // const [searchQuery, setSearchQuery] = useState('Bret');

  useEffect(() => {
    const fetchFunc = async () => {
      const res = await fetch(`http://localhost:8080/pet`);
      res.then(res => res.json).then((res = setPet(res)));
    };

    fetchFunc();
    console.log(petList);
    // console.log(fetchFunc());
  }, [petList]);

  return (
    <div>
      <PetCard pet={petList} />
    </div>
    // <div>
    //   <h1 align="center">This is the User Home Page</h1>

    //   <PetCard pet={petProfileInfo} />
    // </div>
  );
};

export default Home;
