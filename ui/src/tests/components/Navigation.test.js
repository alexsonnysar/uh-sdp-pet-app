import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import Navigation from '../../components/Navigation';

describe('<Navigation />', () => {
  test('should render not authenticated navbar', () => {
    const history = createMemoryHistory();
    const { getByTestId } = render(
      <Router history={history}>
        <Navigation
          auth={false}
          handleAuth={() => {}}
          handleRoles={() => {}}
          username="Alex"
          setUsername={() => {}}
        />
      </Router>
    );
    const navigation = getByTestId('navbar');
    expect(navigation).toBeInTheDocument();

    const notLoggedInHomeButton = getByTestId('notLoggedInHome');
    expect(notLoggedInHomeButton).toBeInTheDocument();

    const loginButton = getByTestId('login');
    expect(loginButton).toBeInTheDocument();

    const registerButton = getByTestId('register');
    expect(registerButton).toBeInTheDocument();
  });

  test('should render authenticated with role of User', () => {
    const history = createMemoryHistory();
    const { getByTestId } = render(
      <Router history={history}>
        <Navigation
          auth
          handleAuth={() => {}}
          roles="ROLE_User"
          handleRoles={() => {}}
          setUsername={() => {}}
        />
      </Router>
    );
    const navigation = getByTestId('navbar');
    expect(navigation).toBeInTheDocument();

    const loggedInHomeButton = getByTestId('loggedInHome');
    expect(loggedInHomeButton).toBeInTheDocument();

    const userButton = getByTestId('userDashButton');
    expect(userButton).toBeInTheDocument();

    const logoutButton = getByTestId('logout');
    expect(logoutButton).toBeInTheDocument();
  });

  test('should render with authentication role Employee', () => {
    const history = createMemoryHistory();
    const { getByTestId } = render(
      <Router history={history}>
        <Navigation
          auth
          handleAuth={() => {}}
          roles="ROLE_Employee"
          handleRoles={() => {}}
          setUsername={() => {}}
        />
      </Router>
    );

    const navigation = getByTestId('navbar');
    expect(navigation).toBeInTheDocument();

    const loggedInHomeButton = getByTestId('loggedInHome');
    expect(loggedInHomeButton).toBeInTheDocument();

    const empButton = getByTestId('empDashButton');
    expect(empButton).toBeInTheDocument();

    const logoutButton = getByTestId('logout');
    expect(logoutButton).toBeInTheDocument();
  });
});
