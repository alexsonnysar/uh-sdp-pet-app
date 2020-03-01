import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import styled from "styled-components";

const Styles = styled.div`
  .navbar {
  }

  .navbar-brand {
  }
`;

const Navigation = () => {
  return (
    <div>
      <Navbar bg="primary" variant="dark">
        <Navbar.Brand>UH Pet App</Navbar.Brand>
        <Nav className="mr-auto">
          <Nav.Link href="/">Home</Nav.Link>
          <Nav.Link href="/employee-dashboard">Employee Dashboard</Nav.Link>
        </Nav>
      </Navbar>
    </div>
  );
};

export default Navigation;
