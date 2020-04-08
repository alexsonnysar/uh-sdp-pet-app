import React from 'react';
import { render, cleanup, waitForElement } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import EmployeeDashboard from '../../pages/EmployeeDashboard';
import { getAllPets } from '../../api/petRequests';

jest.mock('../../api/petRequests', () => ({ getAllPets: jest.fn() }));

const pets = [
  {
    id: '5e669d801dbdd96036ec3b8f',
    name: 'Josie',
    type: 'dog',
    sex: 'F',
    age: 'young',
    size: 'medium',
    weight: 43.4,
    dateAdded: '2020-03-09T19:48:16.418+0000',
    description: 'She is full of energy.',
    imageNames: [
      'runningOnTheBeach',
      'biting_the_neighbors_kid',
      'SleepingOnTheCouch',
    ],
    active: false,
    adopted: false,
  },
  {
    id: '5e66b0523c5d425f75ded9ec',
    name: 'Buddy',
    type: 'dog',
    sex: 'M',
    age: 'Child',
    size: 'large',
    weight: 123.4,
    dateAdded: '2020-03-09T21:08:34.446+0000',
    description: 'He is very wet. Just like all the time',
    imageNames: [
      'walking in the park',
      'biting the neighbors kid...',
      'catching a frisbee',
    ],
    active: false,
    adopted: false,
  },
];

describe('<EmployeeDashboard />', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render employee dashboard with mock resolved API', async () => {
    getAllPets.mockImplementation(() => Promise.resolve(pets));

    const { getByTestId } = render(
      <Router>
        <EmployeeDashboard />
      </Router>,
    );

    expect(getByTestId('loading')).toBeInTheDocument();

    const loadedPetList = await waitForElement(() => getByTestId('loadedList'));
    expect(loadedPetList).toBeInTheDocument();
    expect(getAllPets).toHaveBeenCalledTimes(1);
  });

  test('should render employee dashboard with mock rejected API', () => {
    const NetworkError = {
      Error: 'Network Error',
    };

    getAllPets.mockImplementationOnce(() => Promise.reject(NetworkError));

    expect(() => {getAllPet}).toThrow()

  });
});
