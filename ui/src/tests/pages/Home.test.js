import React from 'react';
import { render, cleanup, waitForElement } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Home from '../../pages/Home';
import { getAllPets } from '../../api/petRequests';
import { petList } from '../mocks/pets';

jest.mock('../../api/petRequests', () => ({ getAllPets: jest.fn() }));

describe('<Home />', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render home with mock resolved API', async () => {
    getAllPets.mockImplementation(() => Promise.resolve({ data: petList }));

    const { getByTestId } = render(
      <Router>
        <Home roles="ROLE_User" />
      </Router>
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPetList = await waitForElement(() => getByTestId('loaded'));
    expect(loadedPetList).toBeInTheDocument();
    expect(getAllPets).toHaveBeenCalledTimes(1);
  });
});
