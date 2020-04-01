import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import { AppBar, Toolbar, Typography, Button } from "@material-ui/core";
import { Link } from "react-router-dom";

const Logout = () => {
  localStorage.clear();
  window.location.replace("http://localhost:3000/");
};

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
            <React.Fragment>
              <Link to="/login" className={classes.button}>
                <Button className={classes.button}>Login</Button>
              </Link>
              <Link to="/register" className={classes.button}>
                <Button className={classes.button}>Register</Button>
              </Link>
            </React.Fragment>
          ) : (
            <Link to="/employee-dashboard" className={classes.button}>
              <Button onClick={Logout} className={classes.button}>
                Logout
              </Button>
            </Link>
          )}

          {/* {!isAuthenticated && (
            <Button color="inherit" onClick={() => loginWithRedirect({})}>
              Log in
            </Button>
          )}

          {isAuthenticated && (
            <span>
              <Button color="inherit" href="/profile">
                Profile
              </Button>
            </span>
          )}

          {isAuthenticated && (
            <Button color="inherit" onClick={() => logout()}>
              Log out
            </Button>
          )} */}
        </Toolbar>
      </AppBar>
    </div>
  );
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

export default Navigation;
