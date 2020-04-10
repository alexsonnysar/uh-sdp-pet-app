import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetInfo from '../../components/PetInfo';

const pet = {
  name: 'Roger',
  type: 'Dog',
  age: 'Young',
  sex: 'M',
  description: 'Test description',
  size: 'Small',
};

describe('<PetInfo />', () => {
  test('should render PetInfo Component', () => {
    const history = createMemoryHistory();

    const { getByTestId } = render(
      <Router history={history}>
        <PetInfo pet={pet} />
      </Router>
    );

    const petInfo = getByTestId('petInfo');
    expect(petInfo).toBeInTheDocument();
  });
});
