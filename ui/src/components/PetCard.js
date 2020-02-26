import React from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

const PetCard = ({ pet, ifList }) => {
  // console.log(pet);
  const { Name, Type } = pet;
  return (
    <div data-testid="petcard">
      <Card className="text-center" style={{ width: "18rem" }}>
        {!ifList ? (
          <a href="/petprofile">
            <Card.Img
              variant="top"
              src="/images/garfield.jpg"
              alt="Pet Image"
            />
          </a>
        ) : null}
        <Card.Body>
          <a href="/petprofile">
            <Card.Title>{Name}</Card.Title>
            <Card.Text>Type: {Type}</Card.Text>
          </a>
          <Button variant="primary">Favorite</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

// function alertMessage() {
//   return alert("Redirect to Pet Profile Page!");
// }

export default PetCard;
