import React from "react";
import { Card, Button, Container, Col, Row } from "react-bootstrap";

const PetListCard = ({ pet }) => {
  const { Name, Type } = pet;
  return (
    <div data-testid="petlistcard">
      <Card className="text-center" style={{ width: "25rem" }}>
        <Card.Body>
          <Container>
            <Row>
              <Col>
                <a href="/petprofile" style={{ textDecorationLine: "none" }}>
                  <Card.Title>{Name}</Card.Title>
                  <Card.Subtitle>{Type}</Card.Subtitle>
                </a>
              </Col>
              <Col></Col>
              <Col>
                <Button variant="primary">Favorite</Button>
              </Col>
            </Row>
          </Container>
        </Card.Body>
      </Card>
    </div>
  );
};

export default PetListCard;
