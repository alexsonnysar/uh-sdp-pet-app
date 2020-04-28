import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { render, fireEvent } from '@testing-library/react';
import axios from 'axios';
import { act } from 'react-dom/test-utils';
import RegisterPetForm from '../../components/RegisterPetForm';

jest.mock('axios');

const response = {
  message: 'Success Message',
};

describe('<RegisterPetForm /> Tests', () => {
  test('should render register pet page', () => {
    const { getByTestId } = render(
      <Router>
        <RegisterPetForm />
      </Router>
    );
    const registerPetForm = getByTestId('registerPetForm');
    expect(registerPetForm).toBeInTheDocument();
  });

  test('Input for formData with correct info as a user', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));

    const { getByLabelText } = render(
      <Router>
        <RegisterPetForm />
      </Router>
    );
    const nameInput = getByLabelText('Name');

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: 'Josie' } });
    });
    expect(nameInput.value).toBe('Josie');

    const weightInput = getByLabelText('Weight');
    await act(async () => {
      fireEvent.change(weightInput, { target: { id: 'weight', value: '20' } });
    });
    expect(weightInput.value).toBe('20');

    const descriptionInput = getByLabelText('Description');
    await act(async () => {
      fireEvent.change(descriptionInput, {
        target: { id: 'description', value: 'Test description' },
      });
    });
    expect(descriptionInput.value).toBe('Test description');
  });
});
