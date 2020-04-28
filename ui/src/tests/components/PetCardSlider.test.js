import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetCardSlider from '../../components/PetCardSlider';
import { petList } from '../mocks/pets';

test('should render a list of pet cards in horizontal slider', () => {
  const history = createMemoryHistory();
  const favID = [];

  const { getByTestId } = render(
    <Router history={history}>
      <PetCardSlider petList={petList} heading="" userFav={favID} roles="ROLE_User" />
    </Router>
  );
  const petLists = getByTestId('petCardSlider');
  expect(petLists).toBeInTheDocument();
});
