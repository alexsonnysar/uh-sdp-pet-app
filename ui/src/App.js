import React, { useState, useEffect } from 'react';
import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import RegisterPet from './pages/RegisterPet';
import EditPet from './pages/EditPet';
import PetProfile from './pages/PetProfile';
import EmployeeDashboard from './pages/EmployeeDashboard';
import UserDashboard from './pages/UserDashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import Navigation from './components/Navigation';
import PrivateRoute from './components/PrivateRoute';

const App = () => {
  const [auth, setAuth] = useState(localStorage.getItem('jwt') !== null);
  const [roles, setRoles] = useState(localStorage.getItem('roles'));
  const [username, setUsername] = useState(localStorage.getItem('username'));

  useEffect(() => {
    if (localStorage.getItem('jwt') !== null) {
      setAuth(true);
      setRoles(localStorage.getItem('roles'));
      setUsername(localStorage.getItem('username'));
    }
  }, [auth, roles, username]);

  return (
    <div>
      <Router>
        <Navigation
          auth={auth}
          handleAuth={setAuth}
          roles={roles}
          handleRoles={setRoles}
          username={username}
          setUsername={setUsername}
        />
        <div className="App" data-testid="App">
          <Switch>
            <Route exact path="/">
              <Home roles={roles} />
            </Route>
            <Route exact path="/login">
              <Login handleAuth={setAuth} />
            </Route>
            <Route exact path="/register">
              <Register handleAuth={setAuth} />
            </Route>
            <Route path="/pet-profile/:id">
              <PetProfile roles={roles} />
            </Route>
            <PrivateRoute auth={auth} roles={roles} exact path="/user/dashboard">
              <UserDashboard username={username} />
            </PrivateRoute>
            <PrivateRoute auth={auth} roles={roles} exact path="/employee/dashboard">
              <EmployeeDashboard />
            </PrivateRoute>
            <PrivateRoute auth={auth} roles={roles} exact path="/employee/pet-register">
              <RegisterPet />
            </PrivateRoute>
            <PrivateRoute auth roles={roles} path="/employee/edit-pet/:id">
              <EditPet />
            </PrivateRoute>
          </Switch>
        </div>
      </Router>
    </div>
  );
};
export default App;
