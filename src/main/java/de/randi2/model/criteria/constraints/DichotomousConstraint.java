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
package de.randi2.model.criteria.constraints;

import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import de.randi2.unsorted.ConstraintViolatedException;

@Entity
public class DichotomousConstraint extends AbstractConstraint<String> {

	protected DichotomousConstraint(){}
	
	public DichotomousConstraint(List<String> args)
			throws ConstraintViolatedException {
		super(args);
	}

	private static final long serialVersionUID = -1224367469711016048L;

	@Getter @Setter
	private String expectedValue;


	@Override
	public void isValueCorrect(String _value) throws ConstraintViolatedException {
		if (!expectedValue.equals(_value)) {
			throw new ConstraintViolatedException();
		}
	}

	@Override
	protected void configure(List<String> args)
			throws ConstraintViolatedException {
		if (args == null || args.size() != 1 || args.get(0) == null)
			throw new ConstraintViolatedException();
		this.expectedValue = args.get(0);
	}
	
	@Override
	public String getUIName() {
		return expectedValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expectedValue == null) ? 0 : expectedValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DichotomousConstraint other = (DichotomousConstraint) obj;
		if (expectedValue == null) {
			if (other.expectedValue != null)
				return false;
		} else if (!expectedValue.equals(other.expectedValue))
			return false;
		return true;
	}
}
