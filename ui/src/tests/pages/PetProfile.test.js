import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render, cleanup, waitForElement } from '@testing-library/react';
import PetProfile from '../../pages/PetProfile';
import { getSinglePet, addRecent } from '../../api/petRequests';
import { pet } from '../mocks/pets';

jest.mock('../../api/petRequests', () => ({
  addRecent: jest.fn(),
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
    addRecent.mockImplementation(() => Promise.resolve({ data: pet }));

    const { getByTestId } = render(
      <Router history={history}>
        <PetProfile roles="ROLE_User" />
      </Router>
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPet = await waitForElement(() => getByTestId('loaded'));
    expect(loadedPet).toBeInTheDocument();
    expect(getSinglePet).toHaveBeenCalledTimes(1);
  });
});
