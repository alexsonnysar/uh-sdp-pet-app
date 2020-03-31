import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";

const Navigation = () => {
  const classes = useStyles();

  return (
    <div data-testid="navbar">
      <AppBar position="sticky">
        <Toolbar>
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
          <Link to="/login" className={classes.button}>
            <Button className={classes.button}>Login</Button>
          </Link>
          <Link to="/register" className={classes.button}>
            <Button className={classes.button}>Register</Button>
          </Link>

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
