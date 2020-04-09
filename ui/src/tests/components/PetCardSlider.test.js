import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetCardSlider from '../../components/PetCardSlider';

const petList = [
  {
    id: '1',
    name: 'Garfield',
    type: 'Cat',
  },
  {
    id: '2',
    name: 'Shawn',
    type: 'Bird',
  },
  {
    id: '3',
    name: 'Alex',
    type: 'Dog',
  },
];

test('should render a list of pet cards in horizontal slider', () => {
  const history = createMemoryHistory();

  const { getByTestId } = render(
    <Router history={history}>
      <PetCardSlider petList={petList} heading="" />
    </Router>
  );
  const petLists = getByTestId('petCardSlider');
  expect(petLists).toBeInTheDocument();
});
