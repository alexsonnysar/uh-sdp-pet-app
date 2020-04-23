import React from 'react';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import PetCardList from '../../components/PetCardList';
import { petList } from '../mocks/pets';

test('should render multiple pet cards', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetCardList petList={petList} />
    </Router>
  );
  const manyPetCards = getByTestId('manypetcards');
  expect(manyPetCards).toBeInTheDocument();
});
