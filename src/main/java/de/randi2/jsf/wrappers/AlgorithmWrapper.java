// Generated by delombok at Mon Aug 29 15:32:21 MSK 2016
package de.randi2.jsf.wrappers;

import javax.faces.event.ActionEvent;
import de.randi2.jsf.backingBeans.BlockR;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.utility.JSFViewUtitlity;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

public class AlgorithmWrapper {
	private static final String COMPANEL = "compPanel";
	private static final String BIASPANEL = "biasPanel";
	private static final String URNPANEL = "urnPanel";
	private static final String BLOCKPANEL = "blockPanel";
	private static final String TRUNCPANEL = "truncPanel";
	private static final String MINIPANEL = "miniPanel";
	private int possition;
	private final int id;
	private BlockR blockR = new BlockR();
	private AbstractRandomizationConfig conf;
	private String description;
	private SimulationHandler handler;

	public AlgorithmWrapper(AbstractRandomizationConfig config, SimulationHandler handler, int id) {
		this.conf = config;
		this.handler = handler;
		this.id = id;
	}

	/**
	 * String ID defining the showed algorithm panel.
	 */
	private String panelType = "AlgorithmErrorPanel";

	/**
	 * Returns the String ID of an panel which need
	 *
	 * @return
	 */
	public String getPanelType() {
		if (CompleteRandomizationConfig.class.isInstance(conf)) panelType = COMPANEL;
		 else if (BiasedCoinRandomizationConfig.class.isInstance(conf)) panelType = BIASPANEL;
		 else if (UrnDesignConfig.class.isInstance(conf)) panelType = URNPANEL;
		 else if (BlockRandomizationConfig.class.isInstance(conf)) panelType = BLOCKPANEL;
		 else if (TruncatedBinomialDesignConfig.class.isInstance(conf)) panelType = TRUNCPANEL;
		 else if (MinimizationConfig.class.isInstance(conf)) panelType = MINIPANEL;
		return panelType;
	}

	public String getPossitionString() {
		return Integer.toString(possition);
	}

	/**
	 * Action listener for removing an algorithm.
	 *
	 * @param event
	 */
	public void removeAlgorithm(ActionEvent event) {
		handler.getRandomisationConfigs().remove(this);
		handler.renameAlgorithmPossitions();
		JSFViewUtitlity.refreshJSFPage();
		handler.setSimulationConfigurationTabIndex(2);
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof AlgorithmWrapper)) return false;
		final AlgorithmWrapper other = (AlgorithmWrapper) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		if (this.getId() != other.getId()) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof AlgorithmWrapper;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.getId();
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getPossition() {
		return this.possition;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setPossition(final int possition) {
		this.possition = possition;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getId() {
		return this.id;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public BlockR getBlockR() {
		return this.blockR;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setBlockR(final BlockR blockR) {
		this.blockR = blockR;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public AbstractRandomizationConfig getConf() {
		return this.conf;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setConf(final AbstractRandomizationConfig conf) {
		this.conf = conf;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getDescription() {
		return this.description;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setDescription(final String description) {
		this.description = description;
	}
}
