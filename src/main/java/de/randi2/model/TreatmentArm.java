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
package de.randi2.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * The Class TreatmentArm.
 */
@Entity
public class TreatmentArm extends AbstractDomainObject {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -1745930698279268352L;
	/**
	 * The name.
	 */
	@NotNull
	@NotEmpty
	@Size(max = MAX_VARCHAR_LENGTH)
	private String name = null;
	/**
	 * The description.
	 */
	@Lob
	@NotNull
	private String description = null;
	/**
	 * The planned subjects.
	 */
	@Range(min = 1, max = Integer.MAX_VALUE)
	private int plannedSubjects = 0;
	/**
	 * The trial.
	 */
	@NotNull
	@ManyToOne
	private Trial trial = null;
	/**
	 * The subjects.
	 */
	@OneToMany(mappedBy = "arm")
	@OrderBy("createdAt ASC")
	private List<TrialSubject> subjects = new ArrayList<TrialSubject>();

	/**
	 * Adds the subject.
	 *
	 * @param subject
	 * the subject
	 */
	public void addSubject(TrialSubject subject) {
		this.subjects.add(subject);
	}

	/**
	 * Gets the current subjects amount.
	 *
	 * @return the current subjects amount
	 */
	@Transient
	public int getCurrentSubjectsAmount() {
		return getSubjects().size();
	}

	/**
	 * Gets the fill level.
	 *
	 * @return the fill level
	 */
	@Transient
	public float getFillLevel() {
		return ((float) getCurrentSubjectsAmount() / getPlannedSubjects()) * 100;
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		result = prime * result + plannedSubjects;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TreatmentArm other = (TreatmentArm) obj;
		if (getId() != other.getId()) return false;
		if (description == null) {
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (plannedSubjects != other.plannedSubjects) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public TreatmentArm() {
	}

	/**
	 * The name.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getName() {
		return this.name;
	}

	/**
	 * The description.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getDescription() {
		return this.description;
	}

	/**
	 * The planned subjects.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getPlannedSubjects() {
		return this.plannedSubjects;
	}

	/**
	 * The trial.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public Trial getTrial() {
		return this.trial;
	}

	/**
	 * The subjects.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public List<TrialSubject> getSubjects() {
		return this.subjects;
	}

	/**
	 * The name.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * The description.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * The planned subjects.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setPlannedSubjects(final int plannedSubjects) {
		this.plannedSubjects = plannedSubjects;
	}

	/**
	 * The trial.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTrial(final Trial trial) {
		this.trial = trial;
	}

	/**
	 * The subjects.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSubjects(final List<TrialSubject> subjects) {
		this.subjects = subjects;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "TreatmentArm(name=" + this.getName() + ", description=" + this.getDescription() + ", plannedSubjects=" + this.getPlannedSubjects() + ", trial=" + this.getTrial() + ", subjects=" + this.getSubjects() + ")";
	}
}
