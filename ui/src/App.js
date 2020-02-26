import React from "react";
import "./App.css";
import Home from "./pages/Home";
import PetProfile from "./pages/PetProfile";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
          <Route path="/petprofile">
            <PetProfile />
          </Route>
          <Route path="/">
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
