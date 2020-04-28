import React from 'react';
import { render, fireEvent, cleanup } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { act } from 'react-dom/test-utils';
import axios from 'axios';
import RegisterForm from '../../components/RegisterForm';

jest.mock('axios');

const response = {
  message: 'response registered successfully!',
};

const user = {
  jwt: 'mockJwt',
  id: '1',
  roles: 'ROLE_User',
};

const employee = {
  jwt: 'mockJwt',
  id: '1',
  roles: 'ROLE_Employee',
};

describe('<RegisterForm /> Tests', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });
  test('should render register page', () => {
    const { getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const registerForm = getByTestId('registerForm');
    expect(registerForm).toBeInTheDocument();
  });

  const setup = () => {
    const utils = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );

    const input = utils.getByLabelText(/Name/i);
    return {
      input,
      ...utils,
    };
  };

  test('handleChange should change formData', () => {
    const { input } = setup();
    fireEvent.change(input, { target: { value: 'Roger' } });

    expect(input.value).toBe('Roger');
  });

  test('click on register button', () => {
    const { getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );

    const submit = getByTestId('submit');
    fireEvent.click(submit);
    expect(localStorage.getItem('jwt')).toEqual(null);
  });

  test('Input for formData with correct info as a user', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const nameInput = getByLabelText(/Name/i);

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: 'Test Name' } });
    });
    expect(nameInput.value).toBe('Test Name');

    const emailInput = getByLabelText(/Email/i);

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: 'hello@123.com' } });
    });
    expect(emailInput.value).toBe('hello@123.com');

    const passwordInput = getByLabelText(/Password-Field/i, { selector: 'input' });
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '$SDP0project1' },
      });
    });
    expect(passwordInput.value).toBe('$SDP0project1');

    const passwordConfirmInput = getByLabelText(/Confirm Password/i);
    await act(async () => {
      fireEvent.change(passwordConfirmInput, {
        target: { id: 'passwordConfirm', value: '$SDP0project1' },
      });
    });

    expect(passwordConfirmInput.value).toBe('$SDP0project1');

    await act(async () => {
      fireEvent.click(getByTestId('submit'));
    });
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  test('Input for formData with correct info as an employee', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: employee }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const nameInput = getByLabelText(/Name/i);

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: 'Test Name' } });
    });
    expect(nameInput.value).toBe('Test Name');

    const emailInput = getByLabelText(/Email/i);

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: 'hello@123.com' } });
    });
    expect(emailInput.value).toBe('hello@123.com');

    const passwordInput = getByLabelText(/Password-Field/i, { selector: 'input' });
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '$SDP0project1' },
      });
    });
    expect(passwordInput.value).toBe('$SDP0project1');

    const passwordConfirmInput = getByLabelText(/Confirm Password/i);
    await act(async () => {
      fireEvent.change(passwordConfirmInput, {
        target: { id: 'passwordConfirm', value: '$SDP0project1' },
      });
    });
    expect(passwordConfirmInput.value).toBe('$SDP0project1');

    await act(async () => {
      fireEvent.click(getByTestId('submit'));
    });
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  test('Input for formData with empty values', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const nameInput = getByLabelText(/Name/i);

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: '' } });
    });
    expect(nameInput.value).toBe('');

    const emailInput = getByLabelText(/Email/i);

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: '' } });
    });
    expect(emailInput.value).toBe('');

    const passwordInput = getByLabelText(/Password-Field/i, { selector: 'input' });
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '' },
      });
    });
    expect(passwordInput.value).toBe('');

    const passwordConfirmInput = getByLabelText(/Confirm Password/i);
    await act(async () => {
      fireEvent.change(passwordConfirmInput, {
        target: { id: 'passwordConfirm', value: '' },
      });
    });

    expect(passwordConfirmInput.value).toBe('');

    await act(async () => {
      fireEvent.click(getByTestId('submit'));
    });
    expect(axios.post).toHaveBeenCalledTimes(0);
  });

  test('Input for formData with incorrect email', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const nameInput = getByLabelText(/Name/i);

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: 'Adam' } });
    });
    expect(nameInput.value).toBe('Adam');

    const emailInput = getByLabelText(/Email/i);

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: 'aol.com' } });
    });
    expect(emailInput.value).toBe('aol.com');

    const passwordInput = getByLabelText(/Password-Field/i, { selector: 'input' });
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '4' },
      });
    });
    expect(passwordInput.value).toBe('4');

    const passwordConfirmInput = getByLabelText(/Confirm Password/i);
    await act(async () => {
      fireEvent.change(passwordConfirmInput, {
        target: { id: 'passwordConfirm', value: '5' },
      });
    });

    expect(passwordConfirmInput.value).toBe('5');

    await act(async () => {
      fireEvent.click(getByTestId('submit'));
    });
    expect(axios.post).toHaveBeenCalledTimes(0);
  });

  test('Input for formData with password mismatch', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: response }));
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <RegisterForm handleAuth={() => {}} />
      </Router>
    );
    const nameInput = getByLabelText(/Name/i);

    await act(async () => {
      fireEvent.change(nameInput, { target: { id: 'name', value: 'Adam' } });
    });
    expect(nameInput.value).toBe('Adam');

    const emailInput = getByLabelText(/Email/i);

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: 'user@123.com' } });
    });
    expect(emailInput.value).toBe('user@123.com');

    const passwordInput = getByLabelText(/Password-Field/i, { selector: 'input' });
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '4' },
      });
    });
    expect(passwordInput.value).toBe('4');

    const passwordConfirmInput = getByLabelText(/Confirm Password/i);
    await act(async () => {
      fireEvent.change(passwordConfirmInput, {
        target: { id: 'passwordConfirm', value: '5' },
      });
    });

    expect(passwordConfirmInput.value).toBe('5');

    await act(async () => {
      fireEvent.click(getByTestId('submit'));
    });
    expect(axios.post).toHaveBeenCalledTimes(0);
  });
});
