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
package de.randi2.jsf.controllerBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import com.icesoft.faces.component.ext.HtmlInputText;
import de.randi2.jsf.backingBeans.RegisterPage;
import de.randi2.jsf.converters.LoginConverter;
import de.randi2.jsf.converters.RoleConverter;
import de.randi2.jsf.exceptions.RegistrationException;
import de.randi2.jsf.supportBeans.PermissionVerifier;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.Role;
import de.randi2.model.TrialSite;
import de.randi2.services.TrialSiteService;
import de.randi2.services.UserService;
import de.randi2.utility.logging.LogEntry;
import de.randi2.utility.logging.LogEntry.ActionType;
import de.randi2.utility.logging.LogService;

/**
 * <p>
 * This class takes care of login objects and contains all UI-specific methods
 * needed for working with login and person objects.
 * </p>
 *
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
@ManagedBean(name = "loginHandler")
@SessionScoped
public class LoginHandler extends AbstractHandler<Login> {
	/*
	 * Services classes to work with - provided via JSF/Spring brige.
	 */
	@ManagedProperty("#{userService}")
	private UserService userService;
	@ManagedProperty("#{logService}")
	private LogService logService;
	@ManagedProperty("#{trialSiteService}")
	private TrialSiteService siteService;
	/*
	 * Reference to the application popup logic.
	 */
	@ManagedProperty("#{popups}")
	private Popups popups;
	/*
	 * Current signed in user.
	 */
	private Login loggedInUser = null;
	private LoginConverter loginConverter;

	public LoginConverter getLoginConverter() {
		if (loginConverter == null) loginConverter = new LoginConverter(userService);
		return loginConverter;
	}

	/*
	 * Current (chosen) locale of the UI.
	 */
	private Locale chosenLocale = null;
	/*
	 * newUser - object for the self registration process tsPasswort - trail
	 * site password for the self registration process
	 */
	private Login newUser = null;
	private String tsPassword = null;
	private List<SelectItem> trialSites;

	public List<SelectItem> getTrialSites() {
		if (trialSites == null) {
			trialSites = new ArrayList<SelectItem>();
			trialSites.add(new SelectItem(null, "please select"));
			for (TrialSite site : siteService.getAll()) {
				trialSites.add(new SelectItem(site, site.getName()));
			}
		}
		return trialSites;
	}

	private TrialSite selectedTrialSite;
	private List<SelectItem> roles;

	public List<SelectItem> getRoles() {
		if (roles == null) {
			roles = new ArrayList<SelectItem>();
			roles.add(new SelectItem(null, "please select"));
			for (Role r : userService.getRolesToAssign(loggedInUser)) {
				roles.add(new SelectItem(r, getRoleConverter().getAsString(null, null, r)));
			}
		}
		return roles;
	}

	private Role selectedRole;
	private RoleConverter roleConverter;

	public RoleConverter getRoleConverter() {
		if (roleConverter == null) roleConverter = new RoleConverter(userService, getChosenLocale());
		return roleConverter;
	}

	/*
	 * UI logic
	 */
	/**
	 * ActionListener for the "add role" event.
	 *
	 * @param event
	 */
	public void addRole(ActionEvent event) {
		currentObject.addRole(selectedRole);
	}

	/**
	 * ActionListener for the "remove role" event.
	 *
	 * @param event
	 */
	public void removeRole(ActionEvent event) {
		assert (userService != null);
		Role tRole = (Role) (((UIComponent) event.getComponent().getChildren().get(0)).getValueExpression("value").getValue(FacesContext.getCurrentInstance().getELContext()));
		currentObject.removeRole(tRole);
	}

	/**
	 * Action Method for the "change password" action.
	 *
	 * @return
	 */
	public String changePassword() {
		this.saveObject();
		popups.hideChangePasswordPopup();
		return Randi2.SUCCESS;
	}

	public String changeTrialSite() {
		if (selectedTrialSite != null) {
			popups.hideChangeTrialSitePopup();
			try {
				siteService.changePersonTrialSite(selectedTrialSite, currentObject.getPerson());
				return Randi2.SUCCESS;
			} catch (Exception exp) {
				Randi2.showMessage(exp);
				return Randi2.ERROR;
			} finally {
				if (currentObject.getId() == loggedInUser.getId()) loggedInUser = currentObject;
				refresh();
			}
		}
		return Randi2.ERROR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#saveObject()
	 */
	@Override
	public String saveObject() {
		assert (currentObject != null);
		try {
			currentObject = userService.update(currentObject);
			// Making the pop up visible
			popups.setUserSavedPVisible(true);
			return Randi2.SUCCESS;
		} catch (Exception exp) {
			Randi2.showMessage(exp);
			return Randi2.ERROR;
		} finally {
			if (currentObject.getId() == loggedInUser.getId()) loggedInUser = currentObject;
			refresh();
		}
	}

	/**
	 * This method is responsible for the users registration.
	 *
	 * @return Randi2.SUCCESS normally. Randi2.ERROR in case of an error.
	 */
	public String registerUser() {
		/*
		 * TODO We could try to move the newUser and tsPassword object as well
		 * as a part of this functionality into the RegisterPage bean.
		 */
		try {
			if (creatingMode) {
				// A new user was created by another logged in user
				newUser = currentObject;
			} else {
				// Normal self-registration - trial site password check!
				assert (newUser != null);
				try {
					if (!siteService.authorize(selectedTrialSite, tsPassword)) {
						throw new RegistrationException(RegistrationException.PASSWORD_ERROR);
					}
				} catch (NullPointerException exp1) {
					// No trial site selected
					throw new RegistrationException(RegistrationException.TRIAL_SITE_ERROR);
				}
			}
			/* Setting the data in the new object */
			newUser.setPrefLocale(getChosenLocale());
			newUser.setUsername(newUser.getPerson().getEmail());
			/* The new object will be saved */
			if (creatingMode) {
				userService.create(newUser, selectedTrialSite);
			} else {
				userService.register(newUser, selectedTrialSite);
			}
			// Making the successPopup visible (NORMAL REGISTRATION)
			if (!creatingMode) {
				((RegisterPage) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "registerPage")).setRegPvisible(true);
				// Reseting the objects used for the registration process
				this.cleanUp();
				// Invalidate Reg-Session
				invalidateSession();
			}
			// Making the popup visible (CREATING AN USER BY ANOTHER USER)
			if (creatingMode) {
				popups.setUserSavedPVisible(true);
			}
			return Randi2.SUCCESS;
		} catch (ConstraintViolationException exp) {
			for (ConstraintViolation<?> v : exp.getConstraintViolations()) {
				Randi2.showMessage(v.getPropertyPath() + " : " + v.getMessage());
			}
			return Randi2.ERROR;
		} catch (Exception e) {
			Randi2.showMessage(e);
			return Randi2.ERROR;
		}
	}

	/**
	 * This method saves the current loggedInUser-object and log it out.
	 *
	 * @return Randi2.SUCCESS
	 */
	public String logoutUser() {
		loggedInUser = userService.update(loggedInUser);
		invalidateSession();
		logService.logChange(ActionType.LOGOUT, loggedInUser.getUsername(), loggedInUser);
		return Randi2.SUCCESS;
	}

	/**
	 * This method clean all references within the LoginHandler-object.
	 */
	public void cleanUp() {
		tsPassword = null;
		newUser = null;
		trialSites = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.handlers.AbstractHandler#refreshShowedObject()
	 */
	@Override
	public String refreshShowedObject() {
		if (currentObject.getId() == AbstractDomainObject.NOT_YET_SAVED_ID) {
			currentObject = null;
			newUser = null;
			currentObject = getNewUser();
		} else {
			currentObject = userService.getObject(currentObject.getId());
		}
		selectedTrialSite = null;
		refresh();
		return Randi2.SUCCESS;
	}

	public void setUSEnglish(ActionEvent event) {
		this.loggedInUser.setPrefLocale(Locale.US);
		this.setChosenLocale(Locale.US);
	}

	public void setDEGerman(ActionEvent event) {
		this.loggedInUser.setPrefLocale(Locale.GERMANY);
		this.setChosenLocale(Locale.GERMANY);
	}

	/*
	 * GET & SET Methods
	 */
	/**
	 * Provides the current logged in user.
	 *
	 * @return
	 */
	public Login getLoggedInUser() {
		if (loggedInUser == null) {
			try {
				loggedInUser = (Login) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				/*
				 * Reloading the user from the database to ensure that the
				 * object is attached to the correct session
				 */
				// loggedInUser = userService.getObject(loggedInUser.getId());
				loggedInUser.setLastLoggedIn(new GregorianCalendar());
			} catch (NullPointerException exp) {
				Logger.getLogger(this.getClass()).debug("NPE", exp);
			}
		}
		return this.loggedInUser;
	}

	/**
	 * This method provide the locale chosen by the logged user. If the user
	 * didn't choose anyone, but his standard browser-locale is supported, then
	 * it will be provided. Otherwise the applications default locale will be
	 * used. setTri
	 *
	 * @return locale for the loged in user
	 */
	public Locale getChosenLocale() {
		if (this.loggedInUser != null) {
			if (this.loggedInUser.getPrefLocale() != null) {
				this.chosenLocale = this.loggedInUser.getPrefLocale();
			}
		} else {
			this.chosenLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
			Iterator<Locale> supportedLocales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
			while (supportedLocales.hasNext()) {
				if (supportedLocales.next().equals(this.chosenLocale)) return this.chosenLocale;
			}
			this.chosenLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		}
		return this.chosenLocale;
	}

	/**
	 * This method specifies if the object that is currently showed in the UI,
	 * is editable or not.
	 *
	 * @return
	 */
	public boolean isEditable() {
		PermissionVerifier permissionVerifier = ((PermissionVerifier) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "permissionVerifier"));
		if (currentObject.equals(this.loggedInUser) || permissionVerifier.isAllowedEditUser(currentObject)) {
			editable = true;
		} else {
			editable = creatingMode;
		}
		return editable;
	}

	/**
	 * This method provides a Login object for the registration and user
	 * creation process.
	 *
	 * @return A Login object, which represents the new user.
	 */
	public Login getNewUser() {
		if (newUser == null) {
			// Starting the registration process
			assert (userService != null);
			if (creatingMode) {
				// new user created from another user
				newUser = new Login();
				newUser.setPerson(new Person());
			} else {
				// self-registration process
				newUser = userService.prepareInvestigator();
			}
		}
		return newUser;
	}

	/**
	 * Use this method to invalidate the current HTTPSession and show the
	 * loginPage
	 */
	public void invalidateSession() {
		final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute(Randi2.RANDI2_END, "The end");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + Randi2.SECURE_LOGOUT_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for initial assistant creation
	 *
	 * @param event
	 */
	public void createAssistant(ActionEvent event) {
		if (currentObject != null) currentObject.getPerson().setAssistant(new Person());
	}

	/**
	 * Provides the audit log entries for the showed object.
	 *
	 * @return
	 */
	public List<LogEntry> getLogEntries() {
		return logService.getLogEntries(currentObject.getUsername());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.randi2.jsf.controllerBeans.AbstractHandler#createPlainObject()
	 */
	@Override
	protected Login createPlainObject() {
		Login l = new Login();
		l.setPerson(new Person());
		return l;
	}

	public TrialSite getCurrentUsersTrialSite() {
		return siteService.getTrialSiteFromPerson(currentObject.getPerson());
	}

	public void passwordChangeListener(ValueChangeEvent event) {
		if (!event.getNewValue().equals(event.getOldValue())) {
			HtmlInputText confirmationPasswordInput = (HtmlInputText) event.getComponent().getParent().getParent().findComponent("pConfirmation");
			confirmationPasswordInput.setValue("");
		}
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setLogService(final LogService logService) {
		this.logService = logService;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSiteService(final TrialSiteService siteService) {
		this.siteService = siteService;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setPopups(final Popups popups) {
		this.popups = popups;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setLoggedInUser(final Login loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setChosenLocale(final Locale chosenLocale) {
		this.chosenLocale = chosenLocale;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getTsPassword() {
		return this.tsPassword;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTsPassword(final String tsPassword) {
		this.tsPassword = tsPassword;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public TrialSite getSelectedTrialSite() {
		return this.selectedTrialSite;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSelectedTrialSite(final TrialSite selectedTrialSite) {
		this.selectedTrialSite = selectedTrialSite;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public Role getSelectedRole() {
		return this.selectedRole;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSelectedRole(final Role selectedRole) {
		this.selectedRole = selectedRole;
	}
}
