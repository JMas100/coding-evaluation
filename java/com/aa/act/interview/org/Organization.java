package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;
	private int employeeNum = 1;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {

		// Search through listed titles.
		Position position = titleFinder(this.root, title);

		// If title is found, place employee under title.
		if (position != null) {
			Optional<Employee> newEmployee = Optional.of(new Employee(this.employeeNum++, person));
			position.setEmployee(newEmployee);
			return Optional.of(position);
		}
		// If no title is found, return empty.
		return Optional.empty();
	}

	private Position titleFinder(Position root, String title) {
		// If title is found
		if (root.getTitle().equals(title)) {
			return root;
		} else {
			for (Position position : root.getDirectReports()) {
				Position PositionFound = titleFinder(position, title);
				// if title was not found
				if (PositionFound == null) {
					continue;
				} else {
					return PositionFound;
				}
			}
		}
		// Returns null if title not found
		return null;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
