import React from "react";
import { Card, Button } from "react-bootstrap";

const PetCard = ({ pet }) => {
  const { Name, Type } = pet;
  return (
    <div data-testid="petcard">
      <Card className="text-center" style={{ width: "18rem" }}>
        <a href="/petprofile">
          <Card.Img variant="top" src="/images/garfield.jpg" alt="Pet Image" />
        </a>
        <Card.Body>
          <a href="/petprofile" style={{ textDecorationLine: "none" }}>
            <Card.Title>{Name}</Card.Title>
            <Card.Text>Type: {Type}</Card.Text>
          </a>
          <Button variant="primary">Favorite</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

export default PetCard;
