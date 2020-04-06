import React from 'react';
import { render } from '@testing-library/react';
import PetList from '../../components/PetList';

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

test('should render a list of pets', () => {
  const { getByTestId } = render(
    <PetList heading="Pet List" petList={petList} />,
  );
  const petLists = getByTestId('petlist');
  expect(petLists).toBeInTheDocument();
});
