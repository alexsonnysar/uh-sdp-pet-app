import React from "react";
import { render, cleanup, waitForElement } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Home from "../pages/Home";
import axiosMock from "axios";

jest.mock("axios");

afterEach(cleanup);

test("should render pet cards from mocked API call", async () => {
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

  const { getByTestId } = render(<Home />);

  expect(getByTestId("loading")).toHaveTextContent("No pets to show :(");

  const loadedPetCardList = await waitForElement(() => getByTestId("loaded"));
  expect(loadedPetCardList).toBeInTheDocument();
  expect(axiosMock.get).toHaveBeenCalledTimes(1);
});

const result = {
  errorCode: 401,
  errorMessage: "Unauthorized"
};

test("should return error", async () => {
  axiosMock.get.mockReturnValue(Promise.reject(new Error(result)));
  await expect(axiosMock.get).rejects.toThrow(result);
});
