import React, { useState } from "react";
import { Card, Button } from "react-bootstrap";

const PetCard = ({ pet }) => {
  const { id, name, type, sex, age, size, weight, dateAdded, description, imageNames, adopted } = pet;
  console.log(name);
  return (
    <div data-testid="petcard">
      <Card className="text-center" style={{ width: "18rem" }}>
        <a href="/petprofile">
          <Card.Img variant="top" src="/images/garfield.jpg" alt="Pet Image" />
        </a>
        <Card.Body>
          <a href="/petprofile" style={{ textDecorationLine: "none" }}>
            <Card.Title>{name}</Card.Title>
            <Card.Text>Type:{type}</Card.Text>
          </a>
          <Button variant="primary">Favorite</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

export default PetCard;
