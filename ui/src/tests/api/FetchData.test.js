import React from "react";
import { render, cleanup, waitForElement } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import axiosMock from "axios";
import { FetchData } from "../../api/FetchData";

jest.mock("axios");
afterEach(cleanup);

test("should return successful info", async () => {
  const url = "http://localhost:8080/pet";
  axiosMock.get.mockResolvedValue({
    data: [
      {
        id: 1,
        name: "Garfield",
        type: "Cat",
        gender: "M"
      },
      {
        id: 2,
        name: "Charlotte",
        type: "Bird",
        gender: "F"
      },
      {
        id: 3,
        name: "Alex",
        type: "Dog",
        gender: "M"
      }
    ]
  });

  await FetchData(url)
    .infoFetch()
    .then(data => expect(data).toEqual(axiosMock.get));
  expect(axiosMock.get).toHaveBeenCalledTimes(1);
});
