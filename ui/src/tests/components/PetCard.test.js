import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetCard from '../../components/PetCard';
import { pet } from '../mocks/pets';

test('should render pet card', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetCard pet={pet} roles="ROLE_User" userFavorite />
    </Router>
  );

  const petCard = getByTestId('petcard');
  expect(petCard).toBeInTheDocument();
});

test('should not see button unless user role', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetCard pet={pet} roles="NOT_User" userFavorite />
    </Router>
  );

  const noFavCard = getByTestId('Not_User');
  expect(noFavCard).toBeInTheDocument();
});
