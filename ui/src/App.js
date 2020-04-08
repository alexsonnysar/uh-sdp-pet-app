import React, { useState, useEffect } from 'react';
import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect
} from 'react-router-dom';
import Home from './pages/Home';
import RegisterPet from './pages/RegisterPet';
import PetProfile from './pages/PetProfile';
import EmployeeDashboard from './pages/EmployeeDashboard';
import UserDashboard from './pages/UserDashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import Navigation from './components/Navigation';

function App() {
  const [auth, setAuth] = useState(false);

  useEffect(
    () => {
      //check local storage
      //setAuth if JWT in local
      if (localStorage.getItem('jwt') !== null) {
        setAuth(true);
      }
    },
    [
      //this empty array meands it will only run once
    ]
  );

  const PrivateRoute = ({ children, ...rest }) => {
    return (
      <Route
        {...rest}
        render={({ location }) =>
          auth ? (
            children
          ) : (
            <Redirect to={{ pathname: '/login', state: { from: location } }} />
          )
        }
      />
    );
  };

  return (
    <div>
      <Router>
        <Navigation />
        <div className="App" data-testid="App">
          <Switch>
            <PrivateRoute path="/user-dashboard">
              <UserDashboard />
            </PrivateRoute>
            <PrivateRoute path="/pet-profile">
              <PetProfile />
            </PrivateRoute>
            <PrivateRoute path="/pet-register">
              <RegisterPet />
            </PrivateRoute>
            <PrivateRoute path="/employee-dashboard">
              <EmployeeDashboard />
            </PrivateRoute>
            <Route path="/login">
              <Login handleAuth={setAuth} />
            </Route>
            <Route path="/register">
              <Register handleAuth={setAuth} />
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
