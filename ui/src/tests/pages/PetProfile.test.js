import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetProfile from '../../pages/PetProfile';

test('should render pet profile page', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetProfile />
    </Router>
  );
  const petProfile = getByTestId('petprofile');
  expect(petProfile).toBeInTheDocument();
});
