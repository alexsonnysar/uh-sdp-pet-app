import React from "react";
import { render } from "@testing-library/react";
import EmployeeDashboard from "../pages/EmployeeDashboard";

test("should render employee dashboard page", () => {
  const { getByTestId } = render(<EmployeeDashboard />);
  const empDash = getByTestId("empdash");
  expect(empDash).toBeInTheDocument();
});
