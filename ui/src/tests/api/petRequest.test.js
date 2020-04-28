import axios from 'axios';

import {
  getAllPets,
  getAllFavs,
  getAllRecents,
  getAllRequestedPets,
  getSinglePet,
  favoritePet,
  unfavoritePet,
  addRecent,
} from '../../api/petRequests';

jest.mock('axios');
const url = 'http://localhost:8080/pet/5e66c0c332f0dc7bedaf501a';

const mockheaders = {
  headers: {
    Authorization: 'Bearer null',
    'Content-Type': 'application/json',
  },
};

describe('Testing our request return promises for petRequest.js', () => {
  afterEach(() => {
    jest.resetAllMocks();
  });

  test('Should return a promise when calling getAllPets', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllPets(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url);
  });

  test('Should return a promise when calling getAllFavs', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllFavs(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url, mockheaders);
  });

  test('Should return a promise when calling getAllRecents', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllRecents(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url, mockheaders);
  });

  test('Should return a promise for getAllRequestedPets', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getAllRequestedPets(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url, mockheaders);
  });

  test('Should return a promise for getSinglePet', async () => {
    const data = { name: 'Simba' };

    axios.get.mockImplementationOnce(() => Promise.resolve(data));

    await expect(getSinglePet(url)).resolves.toEqual(data);

    expect(axios.get).toHaveBeenCalledWith(url);
  });

  test('Should return a promise for favoritePet', async () => {
    const data = { mock: 'mock favorite' };

    axios.post.mockImplementationOnce(() => Promise.resolve(data));

    await expect(favoritePet(url)).resolves.toEqual(data);

    expect(axios.post).toHaveBeenCalledWith(url, undefined, mockheaders);
  });

  test('Should return a promise for unfovoritePet', async () => {
    const data = { mock: 'mock favorite' };

    axios.put.mockImplementationOnce(() => Promise.resolve(data));

    await expect(unfavoritePet(url)).resolves.toEqual(data);

    expect(axios.put).toHaveBeenCalledWith(url, undefined, mockheaders);
  });

  test('Should return a promise for addRecent', async () => {
    const data = { mock: 'recently visited' };

    axios.post.mockImplementationOnce(() => Promise.resolve(data));

    await expect(addRecent(url)).resolves.toEqual(data);

    expect(axios.post).toHaveBeenCalledWith(url, {}, mockheaders);
  });
});
