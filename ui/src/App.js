import React from "react";
import "./App.css";
import Home from "./pages/Home";
import RegisterPet from "./pages/RegisterPet";
import PetProfile from "./pages/PetProfile";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import UserDashboard from "./pages/UserDashboard";
import Login from "./pages/Login";
import Register from "./pages/Register";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Navigation from "./components/Navigation";

function App() {
  return (
    <div>
      <Router>
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
              <RegisterPet />
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
