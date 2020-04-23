import axios from 'axios';

import {
  getSinglePet,
  getAllRequestedPets,
  favoritePet,
  getAllPets,
} from '../../api/petRequests';

jest.mock('axios');
const url = 'http://localhost:8080/pet/5e66c0c332f0dc7bedaf501a';

describe('Just testing our request return promises', () => {
  afterEach(() => {
    jest.resetAllMocks();
  });

  test('Should return a promise when calling getAllPets', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllPets(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url);
  });

  test('Should return a promise for getSinglePet', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getSinglePet(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url);
  });

  test('Should return a promise for favoritePet', async () => {
    const data = {};

    axios.post.mockImplementationOnce(() => Promise.resolve(data));

    await expect(favoritePet(url)).resolves.toEqual(data);
  });

  test('Should return a promise for getAllRequestedPets', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllRequestedPets(url)).resolves.toEqual(data);
  });
});
