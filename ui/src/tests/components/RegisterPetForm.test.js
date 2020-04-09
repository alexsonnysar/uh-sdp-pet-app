import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import RegisterPetForm from '../../components/RegisterPetForm';

test('should render register pet page', () => {
  const history = createMemoryHistory();
  const { getByTestId } = render(
    <Router history={history}>
      <RegisterPetForm />
    </Router>
  );
  const registerPetForm = getByTestId('registerPetForm');
  expect(registerPetForm).toBeInTheDocument();
});
