import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import EditPetForm from '../../components/EditPetForm';
import { pet } from '../mocks/pets';

test('should render edit pet page', () => {
  const history = createMemoryHistory();
  const { getByTestId } = render(
    <Router history={history}>
      <EditPetForm pet={pet} />
    </Router>
  );
  const editPetForm = getByTestId('editPetForm');
  expect(editPetForm).toBeInTheDocument();
});
