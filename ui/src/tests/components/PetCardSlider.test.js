import React from 'react';
import { render } from '@testing-library/react';
import PetCardSlider from '../../components/PetCardSlider';

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

test('should render a list of pet cards in horizontal slider', () => {
  const { getByTestId } = render(<PetCardSlider petList={petList} heading="" />);
  const petLists = getByTestId('petCardSlider');
  expect(petLists).toBeInTheDocument();
});
