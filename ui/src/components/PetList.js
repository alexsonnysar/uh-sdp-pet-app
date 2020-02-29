import React from "react";
import PetListCard from "./PetListCard";
import CardColumns from "react-bootstrap/CardColumns";
import { Button, Container, Col, Row } from "react-bootstrap";

const PetList = ({ heading, petList }) => {
  return (
    <div data-testid="petlist">
      <Container>
        <Row>
          <Col md="auto">
            <h3>{heading}</h3>
          </Col>
          <Col>
            <Button variant="success">Add</Button>
          </Col>
        </Row>
      </Container>
      <Row>
        <Col>
          {petList.map((pet, index) => (
            <CardColumns key={index}>
              <PetListCard pet={pet} key={index} />
            </CardColumns>
          ))}
        </Col>
      </Row>
    </div>
  );
};

export default PetList;
