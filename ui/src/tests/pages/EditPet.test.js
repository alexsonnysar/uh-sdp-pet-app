import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import EditPet from '../../pages/EditPet';

test('should render edit pet page', () => {
  const history = createMemoryHistory();
  const { getByTestId } = render(
    <Router history={history}>
      <EditPet />
    </Router>
  );
  const editPet = getByTestId('editPet');
  expect(editPet).toBeInTheDocument();
});
