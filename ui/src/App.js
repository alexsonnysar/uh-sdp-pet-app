import React from "react";
import "./App.css";
import Home from "./pages/Home";
import PetForm from "./pages/PetForm";
import PetProfile from "./pages/PetProfile";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import UserDashboard from "./pages/UserDashboard";
import Login from "./pages/Login";
import Register from "./pages/Register";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Profile from "./components/Profile";
import history from "./utils/history";
import { useAuth0 } from "./react-auth0-spa";
import Navigation from "./components/Navigation";

function App() {
  const { loading } = useAuth0();

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Router history={history}>
        <Navigation />
        <div className="App" data-testid="App">
          <Switch>
            <Route path="/user-dashboard">
              <UserDashboard />
            </Route>
            <Route path="/pet-profile">
              <PetProfile />
            </Route>
            <Route path="/pet-register">
              <PetForm />
            </Route>
            <Route path="/employee-dashboard">
              <EmployeeDashboard />
            </Route>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/register">
              <Register />
            </Route>
            <Route path="/profile" component={Profile} />
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </div>
      </Router>
    </div>
  );
}

export default App;
