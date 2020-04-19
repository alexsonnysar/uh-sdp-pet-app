import React from 'react';
import { shallow } from 'enzyme';
import PetList from '../../components/PetList';

const petList = [
  {
    id: '1',
    name: 'Garfield',
    type: 'Cat',
  },
  {
    id: '2',
    name: 'Shawn',
    type: 'Bird',
  },
  {
    id: '3',
    name: 'Alex',
    type: 'Dog',
  },
];
describe('<PetList /> Tests', () => {
  let shallowWrapper;

  beforeEach(() => {
    shallowWrapper = shallow(
      <PetList
        heading="Pet List"
        petList={petList}
        action={() => {}}
        deleteButton
        updateButton
      />
    );
  });

  test('should render 3 PetListItem Components', () => {
    expect(shallowWrapper.find('PetListItem').length).toBe(3);
  });
});
