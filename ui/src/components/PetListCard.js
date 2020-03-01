import React from "react";
import { Card, Button, Container, Col, Row } from "react-bootstrap";

const PetListCard = ({ pet }) => {
  const { name, type } = pet;
  return (
    <div data-testid="petlistcard">
      <Card className="text-center" style={{ width: "25rem" }}>
        <Card.Body>
          <Container>
            <Row>
              <Col>
                <a href="/petprofile" style={{ textDecorationLine: "none" }}>
                  <Card.Title>{name}</Card.Title>
                  <Card.Subtitle>{type}</Card.Subtitle>
                </a>
              </Col>
              <Col></Col>
              <Col>
                <Button variant="primary">Update</Button>
              </Col>
              <Col>
                <Button variant="danger">Delete</Button>
              </Col>
            </Row>
          </Container>
        </Card.Body>
      </Card>
    </div>
  );
};

export default PetListCard;
