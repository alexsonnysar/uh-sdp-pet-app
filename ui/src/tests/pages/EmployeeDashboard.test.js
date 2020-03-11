import React from "react";
import { render, cleanup, waitForElement } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import EmployeeDashboard from "../../pages/EmployeeDashboard";
import axiosMock from "axios";

jest.mock("axios");

afterEach(cleanup);

test("should render employee dashboard page", () => {
  const { getByTestId } = render(<EmployeeDashboard />);
  const empDash = getByTestId("empdash");
  expect(empDash).toBeInTheDocument();
});

test("should render employee dashboard with mock API", async () => {
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

  const { getByTestId } = render(<EmployeeDashboard />);

  expect(getByTestId("loading")).toHaveTextContent(
    "No List of Pets to Show :("
  );

  const loadedPetList = await waitForElement(() => getByTestId("loadedList"));
  expect(loadedPetList).toBeInTheDocument();
  expect(axiosMock.get).toHaveBeenCalledTimes(1);
});
