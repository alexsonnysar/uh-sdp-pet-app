import React from 'react';
import { render, fireEvent, cleanup } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import axios from 'axios';
import { act } from 'react-dom/test-utils';
import LoginForm from '../../components/LoginForm';

jest.mock('axios');

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

describe('<LoginForm /> Tests', () => {
  afterEach(() => {
    cleanup();
    jest.resetAllMocks();
  });

  test('should render login page', () => {
    const { getByTestId } = render(
      <Router>
        <LoginForm handleAuth={() => {}} />
      </Router>
    );
    const loginForm = getByTestId('loginForm');
    expect(loginForm).toBeInTheDocument();
  });

  test('Input for formData with correct email & password as a user', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <LoginForm handleAuth={() => {}} />
      </Router>
    );
    const emailInput = getByLabelText('Email');

    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: 'hello@123.com' } });
    });
    expect(emailInput.value).toBe('hello@123.com');

    const passwordInput = getByLabelText('Password');
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '$SDP0project1' },
      });
    });
    expect(passwordInput.value).toBe('$SDP0project1');

    await act(async () => {
      fireEvent.click(getByTestId('loginButtonForm'));
    });
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  test('Input for formData with correct email & password as an employee', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: employee }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <LoginForm handleAuth={() => {}} />
      </Router>
    );
    const emailInput = getByLabelText('Email');

    await act(async () => {
      fireEvent.change(emailInput, {
        target: { id: 'email', value: 'employee@123.com' },
      });
    });
    expect(emailInput.value).toBe('employee@123.com');

    const passwordInput = getByLabelText('Password');
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '$SDP0project1' },
      });
    });
    expect(passwordInput.value).toBe('$SDP0project1');

    await act(async () => {
      fireEvent.click(getByTestId('loginButtonForm'));
    });
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  test('handleChange should change formData for incorrect email & Password', async () => {
    axios.post.mockImplementationOnce(() => Promise.resolve({ data: user }));

    const { getByLabelText, getByTestId } = render(
      <Router>
        <LoginForm handleAuth={() => {}} />
      </Router>
    );

    const emailInput = getByLabelText('Email');
    await act(async () => {
      fireEvent.change(emailInput, { target: { id: 'email', value: '' } });
    });
    expect(emailInput.value).toBe('');

    const passwordInput = getByLabelText('Password');
    await act(async () => {
      fireEvent.change(passwordInput, {
        target: { id: 'password', value: '' },
      });
    });
    expect(passwordInput.value).toBe('');

    await act(async () => {
      fireEvent.click(getByTestId('loginButtonForm'));
    });
    expect(axios.post).toHaveBeenCalledTimes(0);
  });
});
