import React from 'react';
import { render } from '@testing-library/react';
import PetListItem from '../../components/PetListItem';

const pet = {
  Name: 'Garfield',
  Type: 'Cat',
};

test('should render pet list item', () => {
  const { getByTestId } = render(<PetListItem pet={pet} removePet={() => {}} />);
  const petListItem = getByTestId('petlistitem');
  expect(petListItem).toBeInTheDocument();
});
