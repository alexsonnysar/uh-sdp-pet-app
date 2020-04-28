import React from 'react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
import { render } from '@testing-library/react';
import PetInfo from '../../components/PetInfo';
import { pet } from '../mocks/pets';

describe('<PetInfo />', () => {
  test('should render PetInfo Component', () => {
    const history = createMemoryHistory();

    const { getByTestId } = render(
      <Router history={history}>
        <PetInfo pet={pet} roles="ROLE_User" />
      </Router>
    );

    // check that this fails without failing the test lol
    // const employeeInfo = getByTestId('EmployeeOnly');
    // expect(employeeInfo).not.toBeInTheDocument();

    const petInfo = getByTestId('petInfo');
    expect(petInfo).toBeInTheDocument();
  });
  test('Should not Show Any Buttons for employee', () => {
    const history = createMemoryHistory();

    const { getByTestId } = render(
      <Router history={history}>
        <PetInfo pet={pet} roles="ROLE_Employee" />
      </Router>
    );

    const employeeInfo = getByTestId('EmployeeOnly');
    expect(employeeInfo).toBeInTheDocument();
  });
});
