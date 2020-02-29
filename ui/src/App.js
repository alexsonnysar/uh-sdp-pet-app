import React from "react";
import "./App.css";
import Home from "./pages/Home";
import Test from "./pages/Test";
import PetProfile from "./pages/PetProfile";
import EmployeeDashboard from "./pages/EmployeeDashboard";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <div className="App" data-testid="App">
        <Switch>
          <Route path="/petprofile">
            <PetProfile />
          </Route>
          <Route path="/empdash">
            <EmployeeDashboard />
          </Route>
          <Route path="/">
            <Home />
            {/* <Test/> */}
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
