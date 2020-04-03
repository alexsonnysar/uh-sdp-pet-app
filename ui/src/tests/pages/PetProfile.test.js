import React from 'react';
import { render } from '@testing-library/react';
import PetProfile from '../../pages/PetProfile';

test('should render pet profile page', () => {
  const { getByTestId } = render(<PetProfile />);
  const petProfile = getByTestId('petprofile');
  expect(petProfile).toBeInTheDocument();
});
