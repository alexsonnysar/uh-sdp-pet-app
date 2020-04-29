import React from 'react';
import { render, cleanup, waitForElement } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import EmployeeDashboard from '../../pages/EmployeeDashboard';
import { getAllPets, getAllRequestedPets } from '../../api/petRequests';
import { petList } from '../mocks/pets';
import { requestList } from '../mocks/requests';

jest.mock('../../api/petRequests', () => ({
  getAllPets: jest.fn(),
  getAllRequestedPets: jest.fn(),
}));

describe('<EmployeeDashboard /> Tests', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render employee dashboard with mock resolved API', async () => {
    getAllPets.mockImplementation(() => Promise.resolve({ data: petList }));
    getAllRequestedPets.mockImplementation(() => Promise.resolve({ data: requestList }));

    const { getByTestId } = render(
      <Router>
        <EmployeeDashboard />
      </Router>
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPetList = await waitForElement(() => getByTestId('loadedList'));
    expect(loadedPetList).toBeInTheDocument();
    expect(getAllRequestedPets).toHaveBeenCalledTimes(2);
    expect(getAllPets).toHaveBeenCalledTimes(2);
  });
});
