package de.randi2.simulation.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationRun;
import de.randi2.unsorted.ContraintViolatedException;


public class SimulationServiceImpl implements SimulationService {

	@Override
	public SimulationResult simulateTrial(Trial trial, int runs) {
		SimulationResult simResult = new SimulationResult();
		Random random = new Random();
		Trial copyTrial = copyTrial(trial);
		long startTime;
		for (int run = 0; run < runs; run++) {
			startTime = System.nanoTime();
			Trial simTrial = resetTrial(copyTrial);
			SimulationRun simRun = new SimulationRun(simTrial.getTreatmentArms().size());
			ArrayList<TrialSite> pSites = new ArrayList<TrialSite>(simTrial
					.getParticipatingSites());
			for (int i = 0; i < simTrial.getPlannedSubjectAmount(); i++) {
				TrialSubject subject = generateTrialSubject(simTrial, random);
				subject.setTrialSite(pSites.get(random.nextInt(pSites.size())));
				TreatmentArm assignedArm = simTrial
						.getRandomizationConfiguration().getAlgorithm()
						.randomize(subject);
				subject.setArm(assignedArm);
				subject.setRandNumber(i + "_" + assignedArm.getName());
				subject.setCounter(i);
				subject.setIdentification(subject.getRandNumber());
				assignedArm.addSubject(subject);
			}
			for(int i = 0; i<simTrial.getTreatmentArms().size();i++){
				simRun.getSubjectsPerArms()[i] = simTrial.getTreatmentArms().get(i).getCurrentSubjectsAmount();
			}
			simRun.setTime((System.nanoTime()-startTime)/1000000);
			simResult.addSimulationRun(simRun);
		}
		return simResult;
	}

	private static Trial copyTrial(Trial trial) {
		int id = 0;
		Trial cTrial = new Trial();
		cTrial.setId(id++);
		cTrial.setParticipatingSites(new HashSet<TrialSite>(trial
				.getParticipatingSites()));
		cTrial.setStratifyTrialSite(trial.isStratifyTrialSite());
		cTrial.setStartDate(trial.getStartDate());
		cTrial.setEndDate(trial.getEndDate());

		ArrayList<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			TreatmentArm cArm = new TreatmentArm();
			cArm.setName(arm.getName());
			cArm.setPlannedSubjects(arm.getPlannedSubjects());
			cArm.setId(id++);
			cArm.setTrial(cTrial);
			arms.add(cArm);
		}
		cTrial.setTreatmentArms(arms);
		for (AbstractCriterion<?, ?> cr : trial.getCriteria()) {
			if (cr instanceof DateCriterion) {
				DateCriterion ccr = new DateCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				for (DateConstraint co : ((DateCriterion) cr).getStrata()) {
					DateConstraint cco = new DateConstraint();
					cco.setFirstDate(co.getFirstDate());
					cco.setSecondDate(co.getSecondDate());
					cco.setId(id++);
					ccr.addStrata(cco);
				}
				cTrial.addCriterion(ccr);
			} else if (cr instanceof DichotomousCriterion) {
				DichotomousCriterion ccr = new DichotomousCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setOption1(((DichotomousCriterion) cr).getOption1());
				ccr.setOption2(((DichotomousCriterion) cr).getOption2());
				for (DichotomousConstraint co : ((DichotomousCriterion) cr)
						.getStrata()) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(co.getExpectedValue());
					DichotomousConstraint cco;
					try {
						cco = new DichotomousConstraint(values);
						cco.setId(id++);
						ccr.addStrata(cco);
					} catch (ContraintViolatedException e) {
					}
				}
				cTrial.addCriterion(ccr);
			} else if (cr instanceof OrdinalCriterion) {
				OrdinalCriterion ccr = new OrdinalCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setElements(((OrdinalCriterion)cr).getElements());
				for (OrdinalConstraint co : ((OrdinalCriterion) cr).getStrata()) {
					ArrayList<String> values = new ArrayList<String>(co
							.getExpectedValues());
					OrdinalConstraint cco;
					try {
						cco = new OrdinalConstraint(values);
						cco.setId(id++);
						ccr.addStrata(cco);
					} catch (ContraintViolatedException e) {
					}
				}
				cTrial.addCriterion(ccr);
			}

		}
		cTrial.setRandomizationConfiguration(trial.getRandomizationConfiguration());
		return cTrial;
	}

	private static Trial resetTrial(Trial trial) {
		int id = 0;
		ArrayList<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			TreatmentArm cArm = new TreatmentArm();
			cArm.setName(arm.getName());
			cArm.setPlannedSubjects(arm.getPlannedSubjects());
			cArm.setId(id++);
			cArm.setTrial(trial);
			arms.add(cArm);
		}
		trial.setTreatmentArms(arms);
		return trial;
	}

	private static TrialSubject generateTrialSubject(Trial trial, Random random) {
		TrialSubject subject = new TrialSubject();
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for (AbstractCriterion<?, ?> cr : trial.getCriteria()) {
			SubjectProperty<Serializable> pr = new SubjectProperty<Serializable>(
					cr);
			try {
				pr.setValue(cr.getConfiguredValues().get(random.nextInt(cr.getConfiguredValues().size())));
			} catch (ContraintViolatedException e) {
			}
			tempSet.add(pr);
		}
		subject.setProperties(tempSet);
		return subject;
	}
}
