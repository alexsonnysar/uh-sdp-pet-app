import React from 'react';
import { render } from '@testing-library/react';
import PetCardList from '../../components/PetCardList';

const petList = [
  {
    id: 1,
    name: 'Garfield',
    type: 'Cat',
  },
  {
    id: 2,
    name: 'Shawn',
    type: 'Bird',
  },
  {
    id: 3,
    name: 'Alex',
    type: 'Dog',
  },
];

test('should render multiple pet cards', () => {
  const { getByTestId } = render(<PetCardList petList={petList} />);
  const manyPetCards = getByTestId('manypetcards');
  expect(manyPetCards).toBeInTheDocument();
});
