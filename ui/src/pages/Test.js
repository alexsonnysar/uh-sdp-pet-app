import React from "react";

class Test extends React.Component {
  constructor() {
    super();

    this.state = {
      pets: [
        {
          id: "001",
          name: "Buddy",
          type: "doge",
          sex: "M",
          age: "Child",
          size: "large",
          weight: 123.4,
          dateAdded: "2020-02-26T00:16:17.472-0600",
          description: "He is very wet. Just like all the time",
          imageNames: [
            "walking in the park",
            "catching a frisbee",
            "biting the neighbors kid..."
          ],
          adopted: false
        },
        {
          id: "002",
          name: "Bella",
          type: "doge",
          sex: "M",
          age: "Child",
          size: "large",
          weight: 123.4,
          dateAdded: "2020-02-26T00:16:17.472-0600",
          description: "He is very wet. Just like all the time",
          imageNames: [
            "walking in the park",
            "catching a frisbee",
            "biting the neighbors kid..."
          ],
          adopted: false
        },
        {
          id: "003",
          name: "Josie",
          type: "doge",
          sex: "M",
          age: "Child",
          size: "large",
          weight: 123.4,
          dateAdded: "2020-02-26T00:16:17.472-0600",
          description: "He is very wet. Just like all the time",
          imageNames: [
            "walking in the park",
            "catching a frisbee",
            "biting the neighbors kid..."
          ],
          adopted: false
        }
      ]
    };
  }

  render() {
    return (
      <div>
        {this.state.pets.map(pet => (
          <h1> {pet.name} </h1>
        ))}
      </div>
    );
  }
}

export default Test;
