import React, { useState, useEffect } from "react";
import PetList from "../components/PetList";

const EmployeeDashboard = () => {
  const [petList, setPetList] = useState([]);

  async function fetchData() {
    const res = await fetch("http://localhost:8080/pet"); // fetch("https://swapi.co/api/planets/4/");
    res.json().then(res => setPetList(res));
  }

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div data-testid="empdash">
      {console.log(petList)}
      <h1 align="center">This is the Employee Dashboard Page</h1>
      <PetList heading="Pet List" petList={petList} />
    </div>
  );
};

export default EmployeeDashboard;
