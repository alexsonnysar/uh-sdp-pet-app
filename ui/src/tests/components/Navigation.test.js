import React from 'react';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { render } from '@testing-library/react';
import Navigation from '../../components/Navigation';

describe('<Navigation />', () => {
  test('should render navigation', () => {
    const history = createMemoryHistory();
    const { getByTestId } = render(
      <Router history={history}>
        <Navigation handleAuth={() => {}} />
      </Router>
    );
    const navigation = getByTestId('navbar');
    expect(navigation).toBeInTheDocument();
  });
});
