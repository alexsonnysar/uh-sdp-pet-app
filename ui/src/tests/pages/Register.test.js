import React from 'react';
import { render } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Register from '../../pages/Register';

test('should render register pet page', () => {
  const { getByTestId } = render(
    <Router>
      <Register handleAuth={() => {}} />
    </Router>
  );
  const register = getByTestId('register');
  expect(register).toBeInTheDocument();
});
