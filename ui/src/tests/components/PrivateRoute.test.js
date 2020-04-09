import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { mount } from 'enzyme';
import PrivateRoute from '../../components/PrivateRoute';
import UserDashboard from '../../pages/UserDashboard';

describe('PrivateRoute auth checks', () => {
  test('should redirect user-dashboard if auth true', () => {
    const auth = true;
    const privateRoute = mount(
      <MemoryRouter initialEntries={['/user-dashboard']}>
        <PrivateRoute auth={auth} exact path="/user-dashboard">
          <UserDashboard />
        </PrivateRoute>
      </MemoryRouter>
    );
    expect(privateRoute.find('Router').prop('history').location.pathname).toEqual(
      '/user-dashboard'
    );
  });

  test('should redirect to homepage if auth false', () => {
    const auth = false;
    const privateRoute = mount(
      <MemoryRouter initialEntries={['/user-dashboard']}>
        <PrivateRoute auth={auth} exact path="/user-dashboard">
          <UserDashboard />
        </PrivateRoute>
      </MemoryRouter>
    );
    expect(privateRoute.find('Router').prop('history').location.pathname).toEqual('/');
  });
});
