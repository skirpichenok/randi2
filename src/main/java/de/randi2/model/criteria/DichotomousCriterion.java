// Generated by delombok at Mon Aug 29 15:32:21 MSK 2016
/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotEmpty;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.unsorted.ConstraintViolatedException;

@Entity
public class DichotomousCriterion extends AbstractCriterion<String, DichotomousConstraint> {
	private static final long serialVersionUID = -2153872079417596823L;
	@NotEmpty
	private String option1 = null;
	@NotEmpty
	private String option2 = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.model.criteria.AbstractCriterion#getConfiguredValues()
	 */
	@Override
	public List<String> getConfiguredValues() {
		if (option1 == null || option2 == null || option1.isEmpty() || option2.isEmpty()) {
			return null; // The Values are not configured.
		} else if (configuredValues == null) {
			configuredValues = new ArrayList<String>();
			configuredValues.add(option1);
			configuredValues.add(option2);
		} else {
			configuredValues.clear();
			configuredValues.add(option1);
			configuredValues.add(option2);
		}
		return configuredValues;
	}

	@Override
	public void isValueCorrect(String value) throws ConstraintViolatedException {
		if (!(option1.equals(value) || option2.equals(value))) {
			throw new ConstraintViolatedException();
		}
		if (inclusionConstraint != null) {
			inclusionConstraint.isValueCorrect(value);
		}
	}

	@Override
	public Class<DichotomousConstraint> getContstraintType() {
		return DichotomousConstraint.class;
	}

	@Override
	public void setInclusionConstraint(DichotomousConstraint inclusionConstraint) throws ConstraintViolatedException {
		if (inclusionConstraint == null || option1.equals(inclusionConstraint.getExpectedValue()) || option2.equals(inclusionConstraint.getExpectedValue())) {
			this.inclusionConstraint = inclusionConstraint;
		} else {
			throw new ConstraintViolatedException();
		}
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getOption1() {
		return this.option1;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setOption1(final String option1) {
		this.option1 = option1;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getOption2() {
		return this.option2;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setOption2(final String option2) {
		this.option2 = option2;
	}
}
