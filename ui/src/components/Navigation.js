import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import Link from "@material-ui/core/Link";
import ScopedCssBaseline from "@material-ui/core/ScopedCssBaseline";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    flexGrow: 1
  }
}));

const Navigation = () => {
  const classes = useStyles();
  return (
    <div>
      {/* Old Bootstrap Navbar */}
      {/* <Navbar bg="primary" variant="dark">
        <Navbar.Brand>UH Pet App</Navbar.Brand>
        <Nav className="mr-auto">
          <Nav.Link href="/">Home</Nav.Link>
          <Nav.Link href="/employee-dashboard">Employee Dashboard</Nav.Link>
        </Nav>
      </Navbar> */}
      <ScopedCssBaseline>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" className={classes.title}>
              UH Pet App
              <Button color="inherit" href="/">
                Home
              </Button>
              <Button color="inherit" href="/employee-dashboard">
                Employee Dashboard
              </Button>
            </Typography>
            <Button color="inherit">Login</Button>
            <Button color="inherit">Sign Up</Button>
          </Toolbar>
        </AppBar>
      </ScopedCssBaseline>
    </div>
  );
};

export default Navigation;
