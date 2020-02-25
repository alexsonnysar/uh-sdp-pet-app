import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

const PetCard = ({ pet }) => {
  console.log(pet);
  const { Name, Type } = pet;
  return (
    <div data-testid="petcard">
      <Card className="text-center" style={{ width: "18rem" }}>
        <Card.Img variant="top" src="/images/garfield.jpg" alt="Pet Image" />
        <Card.Body>
          <Card.Title>{Name}</Card.Title>
          <Card.Text>Type: {Type}</Card.Text>
          <Button variant="primary">Favorite</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

export default PetCard;
