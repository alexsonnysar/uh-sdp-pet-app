import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { mount } from 'enzyme';
import PrivateRoute from '../../components/PrivateRoute';
import UserDashboard from '../../pages/UserDashboard';

const roles = {
  user: 'ROLE_User',
  employee: 'ROLE_Employee',
};

describe('PrivateRoute auth checks', () => {
  test('should redirect  to user dashboard if auth true and role Employee', () => {
    const auth = true;
    const privateRoute = mount(
      <MemoryRouter initialEntries={['/user/dashboard']}>
        <PrivateRoute auth={auth} roles={roles.user} exact path="/user/dashboard">
          <UserDashboard />
        </PrivateRoute>
      </MemoryRouter>
    );
    expect(privateRoute.find('Router').prop('history').location.pathname).toEqual(
      '/user/dashboard'
    );
  });

  test('should redirect  to employee dashboard if auth true and role is Employee', () => {
    const auth = true;
    const privateRoute = mount(
      <MemoryRouter initialEntries={['/employee/dashboard']}>
        <PrivateRoute auth={auth} roles={roles.employee} exact path="/employee/dashboard">
          <UserDashboard />
        </PrivateRoute>
      </MemoryRouter>
    );
    expect(privateRoute.find('Router').prop('history').location.pathname).toEqual(
      '/employee/dashboard'
    );
  });

  test('should redirect to homepage if auth false and role is empty', () => {
    const auth = false;
    const privateRoute = mount(
      <MemoryRouter initialEntries={['/user/dashboard']}>
        <PrivateRoute auth={auth} roles="" exact path="/user/dashboard">
          <UserDashboard />
        </PrivateRoute>
      </MemoryRouter>
    );
    expect(privateRoute.find('Router').prop('history').location.pathname).toEqual('/');
  });
});
