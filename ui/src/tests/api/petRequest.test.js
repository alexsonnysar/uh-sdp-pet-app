import getAllPets from "../../api/petRequests";

jest.mock("../../api/fetchData.js");
const pets = [
  {
    id: "5e669d801dbdd96036ec3b8f",
    name: "Josie",
    type: "dog",
    sex: "F",
    age: "young",
    size: "medium",
    weight: 43.4,
    dateAdded: "2020-03-09T19:48:16.418+0000",
    description: "She is full of energy.",
    imageNames: [
      "runningOnTheBeach",
      "biting_the_neighbors_kid",
      "SleepingOnTheCouch"
    ],
    active: false,
    adopted: false
  },
  {
    id: "5e66b0523c5d425f75ded9ec",
    name: "Buddy",
    type: "dog",
    sex: "M",
    age: "Child",
    size: "large",
    weight: 123.4,
    dateAdded: "2020-03-09T21:08:34.446+0000",
    description: "He is very wet. Just like all the time",
    imageNames: [
      "walking in the park",
      "biting the neighbors kid...",
      "catching a frisbee"
    ],
    active: false,
    adopted: false
  }
];

test("should run mocked api", async () => {
  const url = "http://localhost:8080/pet";
  expect.assertions(1);
  const data = await getAllPets(url);
  console.log("Data: " + data);
  expect(data).toBe(pets);
});
