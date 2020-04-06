import axios from 'axios';
import { getAllPets } from '../../api/petRequests';

jest.mock('axios');

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
    weight: 125.4,
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

describe('getAllPets()', () => {
  test('should return resolved mocked data', () => {
    const url = '/fetchURL';
    const resp = { data: pets };
    axios.get.mockResolvedValue(resp);

    return getAllPets(url).then((data) => expect(data).toEqual(pets));
  });

  test('should return rejected mocked data', () => {
    const url = 'fetchUrl';
    const mockError = {
      Error: 'Network Error',
    };

    axios.get.mockRejectedValue(mockError);

    return getAllPets(url).catch((error) => expect(error).toEqual(mockError));
  });
});
