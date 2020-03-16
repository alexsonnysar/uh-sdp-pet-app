import React from "react";
import "./App.css";
import Home from "./pages/Home";
import PetForm from "./pages/PetForm";
import PetProfile from "./pages/PetProfile";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import UserDashboard from "./pages/UserDashboard";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Navigation from "./components/Navigation";

function App() {
  return (
    <div>
      <Navigation />
      <Router>
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
