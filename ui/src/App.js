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
            <Route path="/" component={Home} />
            <Route path="/user-dashboard" component={UserDashboard} />
            <Route path="/pet-profile" component={PetProfile} />
            <Route path="/pet-register" component={RegisterPet} />
            <Route path="/employee-dashboard" component={EmployeeDashboard} />
            <Route path="/login" component={Login} />
            <Route path="/register" component={Register} />
          </Switch>
        </div>
      </Router>
    </div>
  );
}

export default App;
