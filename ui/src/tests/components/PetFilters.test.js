import React from 'react';
import { render, cleanup } from '@testing-library/react';
import PetFilters from '../../components/PetFilters';
import { petList } from '../mocks/pets';

describe('Test for PetFilters', () => {
  let mockCallBack;
  beforeEach(() => {
    mockCallBack = jest.fn();
  });
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('Should render the PetFilters', () => {
    const { getByTestId } = render(
      <PetFilters setFilteredPetList={mockCallBack} originalPetList={petList} />
    );
    expect(getByTestId('testingFilter')).toBeInTheDocument();
    // expect(getByLabelText('Type')).toBeInTheDocument();
  });
});
