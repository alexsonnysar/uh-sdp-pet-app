import React from "react";
import "./App.css";
import Home from "./pages/Home";
import PetProfile from "./pages/PetProfile";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import TestPage from "./pages/TestPage";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Navigation from "./components/Navigation";

function App() {
  return (
    <div>
      <Navigation />
      <Router>
        <div className="App" data-testid="App">
          <Switch>
            <Route path="/test-page">
              <TestPage />
            </Route>
            <Route path="/pet-profile">
              <PetProfile />
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
