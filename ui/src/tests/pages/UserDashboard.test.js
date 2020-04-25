import React from 'react';
import { render, cleanup, waitForElement } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import UserDashboard from '../../pages/UserDashboard';
import { getAllPets, getAllFavs, getAllRecents } from '../../api/petRequests';
import { petList } from '../mocks/pets';

jest.mock('../../api/petRequests', () => ({
  getAllPets: jest.fn(),
  getAllFavs: jest.fn(),
  getAllRecents: jest.fn(),
}));

describe('<UserDashboard />', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render user dashboard with mock resolved API', async () => {
    getAllPets.mockImplementation(() => Promise.resolve({ data: petList }));
    getAllFavs.mockImplementation(() => Promise.resolve({ data: petList }));
    getAllRecents.mockImplementation(() => Promise.resolve({ data: petList }));

    const { getByTestId } = render(
      <Router>
        <UserDashboard />
      </Router>
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPetList = await waitForElement(() => getByTestId('loaded'));
    expect(loadedPetList).toBeInTheDocument();
    expect(getAllPets).toHaveBeenCalledTimes(1);
  });
});
