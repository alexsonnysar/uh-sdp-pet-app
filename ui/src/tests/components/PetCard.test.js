import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetCard from '../../components/PetCard';

const pet = {
  id: '1',
  name: 'Garfield',
  type: 'Cat',
};
test('should render pet card', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetCard pet={pet} />
    </Router>
  );

  const petCard = getByTestId('petcard');
  expect(petCard).toBeInTheDocument();
});
