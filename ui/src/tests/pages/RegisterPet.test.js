import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import RegisterPet from '../../pages/RegisterPet';

test('should render register pet page', () => {
  const history = createMemoryHistory();
  const { getByTestId } = render(
    <Router history={history}>
      <RegisterPet />
    </Router>
  );
  const registerPet = getByTestId('registerPet');
  expect(registerPet).toBeInTheDocument();
});
