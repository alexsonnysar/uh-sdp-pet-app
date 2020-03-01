import React, { useState } from "react";
import { Card, Button } from "react-bootstrap";

const PetCard = ({ pet }) => {
  const { name, type } = pet;
  console.log(name);
  return (
    <div>
      <Card className="text-center" style={{ width: "12rem" }}>
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
