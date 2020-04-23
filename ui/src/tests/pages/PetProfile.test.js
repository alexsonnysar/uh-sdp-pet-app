import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render, cleanup, waitForElement } from '@testing-library/react';
import PetProfile from '../../pages/PetProfile';
import { getSinglePet } from '../../api/petRequests';
import { pet } from '../mocks/pets';

jest.mock('../../api/petRequests', () => ({
  getSinglePet: jest.fn(),
}));

describe('<PetProfile /> Tests', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render pet profile page with mocked resolved API', async () => {
    const history = createMemoryHistory();

    getSinglePet.mockImplementation(() => Promise.resolve({ data: pet }));

    const { getByTestId } = render(
      <Router history={history}>
        <PetProfile />
      </Router>
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPet = await waitForElement(() => getByTestId('loaded'));
    expect(loadedPet).toBeInTheDocument();
    expect(getSinglePet).toHaveBeenCalledTimes(1);
  });
});
