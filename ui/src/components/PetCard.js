import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

const PetCard = ({ pet, ifList }) => {
  // console.log(pet);
  const { Name, Type } = pet;
  return (
    <div data-testid="petcard">
      <Card
        className="text-center"
        style={{ width: "18rem" }}
        onClick={alertMessage}
      >
        {!ifList ? (
          <Card.Img
            variant="bottom"
            src="/images/garfield.jpg"
            alt="Pet Image"
          />
        ) : null}
        <Card.Body>
          <Card.Title>{Name}</Card.Title>
          <Card.Text>Type: {Type}</Card.Text>
          <Button variant="primary">Favorite</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

function alertMessage() {
  return alert("Redirect to Pet Profile Page!");
}

export default PetCard;
