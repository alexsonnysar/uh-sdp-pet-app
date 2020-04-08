import React from 'react';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import { Router } from 'react-router-dom';
import PetCardList from '../../components/PetCardList';

const petList = [
  {
    id: '1',
    name: 'Garfield',
    type: 'Cat',
  },
  {
    id: '2',
    name: 'Shawn',
    type: 'Bird',
  },
  {
    id: '3',
    name: 'Alex',
    type: 'Dog',
  },
];

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
