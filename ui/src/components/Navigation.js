import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { AppBar, Toolbar, Typography, Button } from "@material-ui/core";
import { Link } from "react-router-dom";

const Logout = () => {
  localStorage.clear();
  window.location.replace("http://localhost:3000/");
};

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    flexGrow: 1
  },
  button: {
    color: "inherit",
    textDecorationLine: "none",
    "&:hover": {
      color: "white"
    }
  }
}));

const Navigation = () => {
  const classes = useStyles();

  return (
    <div data-testid="navbar">
      <AppBar position="sticky">
        <Toolbar>
          {localStorage.getItem("jwt") !== null ? (
            <Typography variant="h6" className={classes.title}>
              <Link to="/" className={classes.button}>
                <Button className={classes.button}>Home</Button>
              </Link>
              {localStorage.getItem("roles") === "ROLE_User" ? (
                <Link to="/user-dashboard" className={classes.button}>
                  <Button className={classes.button}>User Dashboard</Button>
                </Link>
              ) : (
                <Link to="/employee-dashboard" className={classes.button}>
                  <Button className={classes.button}>Employee Dashboard</Button>
                </Link>
              )}
            </Typography>
          ) : (
            <Typography variant="h6" className={classes.title}>
              <Link to="/" className={classes.button}>
                <Button className={classes.button}>Home</Button>
              </Link>
            </Typography>
          )}

          {localStorage.getItem("jwt") === null ? (
            <>
              <Link to="/login" className={classes.button}>
                <Button className={classes.button}>Login</Button>
              </Link>
              <Link to="/register" className={classes.button}>
                <Button className={classes.button}>Register</Button>
              </Link>
            </>
          ) : (
            <Button
              onClick={Logout}
              className={classes.button}
              data-testid="logout"
            >
              Logout
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default Navigation;
