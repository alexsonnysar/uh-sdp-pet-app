import React from 'react';
import { render } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Login from '../../pages/Login';

test('should render Login page', () => {
  const { getByTestId } = render(
    <Router>
      <Login handleAuth={() => {}} />
    </Router>
  );
  const petProfile = getByTestId('login');
  expect(petProfile).toBeInTheDocument();
});
