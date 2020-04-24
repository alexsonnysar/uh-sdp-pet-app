import React from 'react';
import { mount } from 'enzyme';
import Button from '@material-ui/core/Button';
import { render, fireEvent } from '@testing-library/react';
import { BrowserRouter, Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import LoginForm from '../../components/LoginForm';

describe('All <LoginForm /> Tests', () => {
  describe('<LoginForm />', () => {
    test('should render login page', () => {
      const { getByTestId } = render(
        <BrowserRouter>
          <LoginForm handleAuth={() => {}} />
        </BrowserRouter>
      );
      const loginForm = getByTestId('loginForm');
      expect(loginForm).toBeInTheDocument();
    });

    const setup = () => {
      const utils = render(
        <BrowserRouter>
          <LoginForm handleAuth={() => {}} />
        </BrowserRouter>
      );
      const input = utils.getByLabelText('Email');
      return {
        input,
        ...utils,
      };
    };

    test('handleChange should change formData', () => {
      const { input } = setup();
      fireEvent.change(input, { target: { value: 'hello@123.com' } });

      expect(input.value).toBe('hello@123.com');
    });
  });

  const simulateChangeOnInput = (wrapper, inputSelector, newValue) => {
    const input = wrapper.find(inputSelector);
    input.simulate('change', {
      target: { value: newValue },
    });
    return wrapper.find(inputSelector);
  };
  describe('<LoginForm /> render tests', () => {
    let mockhandleAuth;
    let mountWrapper;
    let loginButton;
    let emailInput;
    let passwordInput;

    beforeEach(() => {
      const history = createMemoryHistory();
      mockhandleAuth = jest.fn();
      mountWrapper = mount(
        <Router history={history}>
          <LoginForm handleAuth={mockhandleAuth} />
        </Router>
      );
      loginButton = mountWrapper.find('Button[type="submit"]');
    });

    afterEach(() => {
      jest.resetAllMocks();
    });

    test('should render login form', () => {
      expect(mountWrapper).toHaveLength(1);
    });

    test('There should be a login button', () => {
      expect(mountWrapper.find(Button).length).toBe(1);
    });

    test('login button should say Login', () => {
      expect(mountWrapper.find(Button).text()).toBe('Login');
    });

    // test('Clicking the Login button', () => {
    //   const updateEmailInput = simulateChangeOnInput(
    //     mountWrapper,
    //     'input#email',
    //     'Employee@Wednesday.com'
    //   );
    //   const updatePasswordInput = simulateChangeOnInput(
    //     mountWrapper,
    //     'input#password',
    //     'AA#,b@9V3)'
    //   );
    //   expect(updateEmailInput.props().value).toEqual('Employee@Wednesday.com');
    //   expect(updatePasswordInput.props().value).toEqual('AA#,b@9V3)');
    //   loginButton.simulate('click');
    //   expect(mockhandleAuth.mock.calls.length).toEqual(1);
    // });
  });
});
