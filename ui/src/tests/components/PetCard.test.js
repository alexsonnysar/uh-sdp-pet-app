import React from 'react';
import { render } from '@testing-library/react';
import PetCard from '../../components/PetCard';

const pet = {
  id: 1,
  name: 'Garfield',
  type: 'Cat',
};

test('should render pet card', () => {
  const { getByTestId } = render(<PetCard pet={pet} />);
  const petCard = getByTestId('petcard');
  expect(petCard).toBeInTheDocument();
});
