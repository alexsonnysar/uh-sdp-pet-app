import React from 'react';
import { shallow } from 'enzyme';
import RequestList from '../../components/RequestList';
import { requestList } from '../mocks/requests';

describe('<RequestList /> Tests', () => {
  let shallowWrapper;

  beforeEach(() => {
    shallowWrapper = shallow(
      <RequestList
        heading="Request List"
        requestList={requestList}
        putRequest={() => {}}
      />
    );
  });

  test('should render 3 RequestListItem Components', () => {
    expect(shallowWrapper.find('RequestListItem').length).toBe(3);
  });
});
