import React from 'react';
import { render, cleanup, waitForElement } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import UserDashboard from '../../pages/UserDashboard';
import { getAllRequestedPets, getAllFavs, getAllRecents } from '../../api/petRequests';
import { petList } from '../mocks/pets';
import { requestList } from '../mocks/requests';

jest.mock('../../api/petRequests', () => ({
  getAllRequestedPets: jest.fn(),
  getAllFavs: jest.fn(),
  getAllRecents: jest.fn(),
}));

describe('<UserDashboard />', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render user dashboard with mock resolved API', async () => {
    getAllRequestedPets.mockImplementation(() => Promise.resolve({ data: requestList }));
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
    expect(getAllRequestedPets).toHaveBeenCalledTimes(1);
    expect(getAllFavs).toHaveBeenCalledTimes(1);
    expect(getAllRecents).toHaveBeenCalledTimes(1);
  });
});
