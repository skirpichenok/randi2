package de.randi2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import de.randi2.model.fachklassen.Recht;
import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.utility.Config;
import de.randi2.utility.Jsp;
import de.randi2.utility.JspTitel;
import de.randi2.utility.LogAktion;
import de.randi2.utility.LogLayout;
import de.randi2.utility.Parameter;
import de.randi2.utility.Config.Felder;

/**
 * <p>
 * Diese Klasse repraesentiert den DISPATCHER (== Weiterleiter). Dieser wird von
 * jeder Anfrage im Projekt angesprochen und leitet diese dann an die
 * entsprechenden Unterservlets bzw. direkt an JSPs weiter.
 * </p>
 * 
 * @version $Id: DispatcherServlet.java 2462 2007-05-08 12:20:08Z btheel $
 * @author Andreas Freudling [afreudling@stud.hs-heilbronn.de]
 * @author BTheel [BTheel@stud.hs-heilbronn.de]
 * 
 */
@SuppressWarnings("serial")
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
	// TODO alle Parameter auf Enums umstellen. Task ist Code-Kosmetik, daher
	// zweitrangig --BTheel
	/**
	 * Ist System gesperrt oder nicht.
	 */
	private boolean istSystemGesperrt = true;

	/**
	 * Fehlermeldung, wenn das System gesperrt ist.
	 */
	private String meldungSystemGesperrt = "Meldung des System ist gesperrt";

	/**
	 * Name der Fehlervariablen im Request. Ueber diesen Parameternamen sind die
	 * Fehlermeldungen von der GUI aus dem Request zu gewinnen
	 */
	public static final String FEHLERNACHRICHT = "fehlernachricht";

	/**
	 * Name der Nachricht_ok -Variablen im Reqeust. Wird in
	 * inc_nachricht_erfolgreich.jsp. Sonst nicht verwenden!!!
	 */
	public static final String NACHRICHT_OK = "nachricht_ok";

	/**
	 * Konstruktor.
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
		istSystemGesperrt = Boolean.valueOf(Config
				.getProperty(Felder.SYSTEMSPERRUNG_SYSTEMSPERRUNG));
		Logger.getLogger(this.getClass()).debug(
				"Lade Mitteilung (System gesperrt) aus Config");
		meldungSystemGesperrt = Config
				.getProperty(Felder.SYSTEMSPERRUNG_FEHLERMELDUNG);
	}

	/**
	 * Verwaltet alle anfrage_ids an das DispatcherServlet. Alle anfrage_id's
	 * muesen hier deklariert werden.
	 * 
	 */
	public enum anfrage_id {
		/**
		 * Benutzer loggt sich aus.
		 */
		JSP_HEADER_LOGOUT,

		/**
		 * Benutzer loggt sich ein.
		 */
		JSP_INDEX_LOGIN,

		/**
		 * Benutzer klickt Impressum link
		 */
		JSP_HEADER_IMPRESSUM,

		/**
		 * Benutzer klickt Benutzer registieren auf index.jsp
		 */
		JSP_INDEX_BENUTZER_REGISTRIEREN_EINS,

		/**
		 * Admin möchte ein neues Zentrum anlegen
		 */
		JSP_ZENTRUM_ANLEGEN,

		/**
		 * Ein Nutzer lässt sich ein neues Passwort zuschicken
		 */
		JSP_PASSWORT_VERGESSEN,

		/**
		 * Benutzer hat Disclaimer akzeptiert. (Benutzer registrieren)
		 */
		JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI,

		/**
		 * Benutzer filtert nach Zentren bzw. gibt sein Zentrumspasswort ein.
		 * (Benutzer registieren)
		 */
		JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI,

		/**
		 * 
		 * Benutzer gibt Personendaten ein (Benutzer registrieren)
		 */
		JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER,

		/**
		 * Zentrenverwaltung für Studie
		 */
		JSP_ZENTRUM_ANZEIGEN,
		
		/**
		 * Benutzerdaten aendern
		 */
		JSP_DATEN_AENDERN,
		
		/**
		 * Im Menue wird Daten aendern angklickt
		 */
		JSP_INC_MENUE_DATEN_AENDERN,
		
		/**
		 *Der Admin lässt sich die Studien anzeigen 
		 */
		JSP_INC_MENUE_STUDIEN_ANZEIGEN,
		
		/**
		 * Im Menue wird Patient hinzufuegen gewählt
		 */
		JSP_INC_MENUE_PATIENT_HINZUFUEGEN,
		/**
		 * Im Menue wird die Studienaerzte_Liste angeforderz
		 */
		JSP_INC_MENUE_STUDIENAERZTE_LISTE,
		
		/**
		 * Im Menue wird die Liste der Admins angefordert
		 */
		JSP_INC_MENUE_ADMIN_LISTE,
		
		/**
		 * Im Menue wird Systemadministration gewählt
		 */
		JSP_INC_MENUE_SYSTEMADMINISTRATION,
		
		/**
		 * System sperren wird aufgerufen
		 */
		JSP_INC_MENUE_SYSTEMSPERREN,
		
		/**
		 * Admin anlegen wird geklickt
		 */
		JSP_INC_MENUE_ADMIN_ANLEGEN,
		
		/**
		 * Ein Studienleiter soll angelegt werden.
		 */
		JSP_INC_MENUE_STUDIENLEITER_ANLEGEN,
		

		/**
		 * Leitet den Forward der system_sperren.jsp weiter (kommt als get)
		 */
		JSP_SYSTEM_SPERREN,
		


		/**
		 * Aufforderung das System zu Entsperren
		 */
		AKTION_SYSTEM_ENTSPERREN,
		/**
		 * Aufforderung das System zu Sperren
		 */
		AKTION_SYSTEM_SPERREN,

		/**
		 * Aufforderung, den Benutzer aus dem System abzumelden
		 */
		AKTION_LOGOUT,

		/**
		 * Aufforderung, den Nachrichtendienst anzuzeigen
		 */
		JSP_HEADER_NACHRICHTENDIENST,

		/**
		 * Aufforderung, die Hilfe anzuzeigen
		 */
		JSP_HEADER_HILFE,

		/**
		 * Aufforderung, einen Admin mit den gesendeten Daten anzulegen
		 */
		AKTION_ADMIN_ANLEGEN,

		/**
		 * Aufforderung, den Request an die entsprechende Seite umzuleiten
		 */
		JSP_ADMIN_ANLEGEN,
		
		/**
		 * Aufforderung, einen Studienleiter mit den gesendeten Daten anzulegen
		 */
		AKTION_STUDIENLEITER_ANLEGEN,

		/**
		 * Benutzer suchen.
		 */
		BENUTZER_SUCHEN,

		/**
		 * Zentrum aendern
		 */
		JSP_ZENTRUM_AENDERN,

		/**
		 * "Neue Studie anlegen" auswaehlen
		 */
		JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE,

		/**
		 * Neue Studie anlegen
		 */
		JSP_STUDIE_ANLEGEN,

		/**
		 * Neuen Studienarm zu Studie hinzufuegen
		 */
		JSP_STUDIE_ANLEGEN_ADD_STUDIENARM,

		/**
		 * Studienarm von Studie entfernen
		 */
		JSP_STUDIE_ANLEGEN_DEL_STUDIENARM,
		/**
		 * Neuen Strata zu Studie hinzufuegen
		 */
		JSP_STUDIE_ANLEGEN_ADD_STRATA,

		/**
		 * Strata von Studie entfernen
		 */
		JSP_STUDIE_ANLEGEN_DEL_STRATA,
		/**
		 * Studie pausieren
		 */
		JSP_STUDIE_PAUSIEREN_EINS,
		/**
		 * Studie fortsetzen
		 */
		JSP_STUDIE_FORTSETZEN_EINS,
		/**
		 * Studie aendern
		 */
		JSP_STUDIE_AENDERN,
		/**
		 * Studie auswaehlen
		 */
		JSP_STUDIE_AUSWAEHLEN,

		/**
		 * Simulation einer Studie.
		 */
		JSP_SIMULATION,
		/**
		 * Zentrum anzeigen beim Admin.
		 */
		ZENTRUM_ANZEIGEN_ADMIN, 
		/**
		 * Zentrum ansehen
		 */
		JSP_ZENTRUM_ANSEHEN,
		/**
		 * Studie ansehen
		 */
		JSP_STUDIE_ANSEHEN,
		/**
		 * Button bei Zentrum suchen.
		 */
		ZENTRUM_AENDERN_SPERREN,
		
		/**
		 * Button bei Patient hinzufuegen.
		 */
		JSP_PATIENT_HINZUFUEGEN_AUSFUEHREN;
	}

	/**
	 * Enhaelt die Parameternamen, die in der Session gesetzt werden koennen
	 * 
	 */
	public static enum sessionParameter {
		/**
		 * Konto des Benutzers (BenutzerkontoBean)
		 */
		A_Benutzer("aBenutzer"), // XXX Konto ist als 'aBenutzer' gebunden,
		// nicht ueber diese
		// Kosntante (lplotni 17. Juni: warum denn ?)

		/**
		 * Zentrum fuer das sich der Benutzer anmeldet.
		 */
		ZENTRUM_BENUTZER_ANLEGEN("aZentrum"),

		/**
		 * Die von dem Benutzer ausgewählte, aktuelle Studie
		 */
		AKTUELLE_STUDIE("aStudie");

		/**
		 * String Version des Parameters
		 */
		private String parameter = null;

		/**
		 * Setzt die String Repraesentation des Parameters
		 * 
		 * @param parameter
		 *            String Repraesentation des Parameters
		 */
		private sessionParameter(String parameter) {
			this.parameter = parameter;
		}

		/**
		 * Liefert die String Repraesentation des Parameters
		 * 
		 * @return String Repraesentation des Parameters
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.parameter;
		}
	}

	/**
	 * Enhaelt die Parameternamen, die in dem Request gesetzt werden koennen
	 * 
	 */
	public static enum requestParameter {

		/**
		 * Id der Anfrage an den Dispatcher
		 */
		ANFRAGE_Id("anfrage_id"),

		/**
		 * Titel der aktuellen JSP
		 */
		TITEL("titel"),

		/**
		 * Systemstatus gesperrt[true|false] (boolean)
		 */
		IST_SYSTEM_GESPERRT("system_gesperrt"),

		/**
		 * Haelt die Begruendung der Systemsperrung (String)
		 */
		MITTEILUNG_SYSTEM_GESPERRT("mitteilung_system_gesperrt"),

		/**
		 * Anzahl an Strata
		 */
		ANZAHL_STRATA("anzahl_strata"),
		/**
		 * Anzahl an Armen
		 */
		ANZAHL_ARME("anzahl_arme"),

		/**
		 * Liste der Zentren
		 */
		LISTE_ZENTREN("listeZentren");
		
		/**
		 * String Version des Parameters
		 */
		private String parameter = null;

		/**
		 * Setzt die String Repraesentation des Parameters
		 * 
		 * @param parameter
		 *            String Repraesentation des Parameters
		 */
		private requestParameter(String parameter) {
			this.parameter = parameter;
		}

		/**
		 * Liefert die String Repraesentation des Parameters
		 * 
		 * @return String Repraesentation des Parameters
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.parameter;
		}

	}

	// TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
	/**
	 * Diese Methode nimmt HTTP-GET-Request gemaess HTTP-Servlet Definition
	 * entgegen.
	 * <p>
	 * Ist das System geperrt, so wird die Anfrage auf die Seite
	 * <source>index_gesperrt.jsp</source> umgeleitet, ansonsten wird die Seite
	 * <source>index.jsp</source> aufgerufen.
	 * </p>
	 * <p>
	 * Es ist erforderlich, das fuer einen gueltigen Aufruf sowohl eine
	 * anfrage_id gesetzt als auch der Benutzer eingeloggt ist.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	// TODO Bitte Kommentar ueberpruefen und ggf. anpassen.
	/**
	 * Diese Methode nimmt HTTP-POST-Request gemaess HTTP-Servlet Definition
	 * entgegen.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = (String) request.getParameter(Parameter.anfrage_id);
		// idAttribute nicht entfernen, benutzen dies fuer die Weiterleitung aus dem Benutzerservlet --Btheel
		String idAttribute = (String) request
				.getAttribute(Parameter.anfrage_id);
System.out.println(idAttribute+" "+id);
		// bei jedem Zugriff, Titel zuruecksetzen
		request.setAttribute(DispatcherServlet.requestParameter.TITEL
				.toString(), null);

		// falls ID null dann leite auf den Index weiter
		if ((id == null || id.trim().equals("")) && (idAttribute == null)) {

			weiterleitungAufIndex(request, response);
		} else {
			Logger.getLogger(this.getClass()).debug("[POST]anfrage_id: " + id);
			if (idAttribute != null) {
				id = idAttribute;
			}

			// WEITERLEITUNGEN FUER BENUTZERSERVLET
			// [start]
			// Login
			if (id.equals(DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name())) {
				weiterleitungBenutzerAnmelden(request, response);
			}

			// Benutzer registrieren
			// Schritt 1.1: STARTSEITE->DISCLAIMER
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS
							.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL
						.toString(), "Benutzer anlegen");
				request.getRequestDispatcher("/benutzer_anlegen_eins.jsp")
						.forward(request, response);
			}
			// Schritt 2.1:DISCLAIMER->ZENTRUMAUSWAHL
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_ZWEI");
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}

			// Schritt 3.1: ZENTRUMAUSWAHL: Filterung
			// Schritt 3.2 ZENTRUMAUSWAHL->BENUTZERDATEN_EINGEBEN
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_DREI");
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}

			// Schritt 4: BENUTZERDATEN_EINGEBEN->
			else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER
							.name())) {
				request.setAttribute("anfrage_id",
						"CLASS_DISPATCHERSERVLET_BENUTZER_REGISTRIEREN_VIER");
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);

			} else if (id
					.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN
							.name())) {
				if (!isBenutzerAngemeldet(request)) { // Benutzer nicht
					// angemeldet
					BenutzerkontoBean anonymous = new BenutzerkontoBean();
					// FIXME FRAGE LogAktion mit String anstatt
					// BenutzerkontoBean
					// zum Loggen der IP?
					// anonymous.setBenutzername("Unangemeldeter Benutzer [IP:
					// "+request.getRemoteAddr()+"]");

					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne Login", anonymous);
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				if (!(((BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer")).getRolle())
						.besitzenRolleRecht(Recht.Rechtenamen.SYSTEM_SPERREN)) {
					// Der User besitzt keine entsprechenden Rechte
					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne ausreichende Rechte"
									+ getMeldungSystemGesperrt(),
							(BenutzerkontoBean) request.getSession()
									.getAttribute("aBenutzer"));
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				this.setSystemGesperrt(false);
				Logger.getLogger(this.getClass()).debug(
						"Schalte System wieder frei");
				LogAktion a = new LogAktion("System wurde entsperrt",
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
				Logger.getLogger(LogLayout.ADMINISTRATION).info(a);
				request.getRequestDispatcher("/system_sperren.jsp").forward(
						request, response);
				return;

			} else if (id
					.equals(DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN
							.name())) {
				if (!isBenutzerAngemeldet(request)) { // Benutzer nicht
					// angemeldet
					BenutzerkontoBean anonymous = new BenutzerkontoBean();
					// FIXME FRAGE LogAktion mit String anstatt
					// BenutzerkontoBean
					// zum Loggen der IP?
					// anonymous.setBenutzername("Unangemeldeter Benutzer [IP:
					// "+request.getRemoteAddr()+"]");

					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne Login", anonymous);
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				if (!(((BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer")).getRolle())
						.besitzenRolleRecht(Recht.Rechtenamen.SYSTEM_SPERREN)) {
					// Der User besitzt keine entsprechenden Rechte
					LogAktion a = new LogAktion(
							"Versuchte Systemsperrung ohne ausreichende Rechte"
									+ getMeldungSystemGesperrt(),
							(BenutzerkontoBean) request.getSession()
									.getAttribute("aBenutzer"));
					Logger.getLogger(LogLayout.RECHTEVERLETZUNG).warn(a);
					return;
				}
				this.setSystemGesperrt(true);

				String meldung = StringEscapeUtils
						.escapeHtml((String) request
								.getParameter(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
										.toString()));
				this.setMeldungSystemGesperrt(meldung);

				Logger.getLogger(this.getClass()).debug(
						"Sperre System. Grund: '" + getMeldungSystemGesperrt()
								+ "'");
				LogAktion a = new LogAktion("System wurde gesperrt, Grund: '"
						+ getMeldungSystemGesperrt() + "'",
						(BenutzerkontoBean) request.getSession().getAttribute(
								"aBenutzer"));
				Logger.getLogger(LogLayout.ADMINISTRATION).info(a);
				request.getRequestDispatcher("/system_sperren.jsp").forward(
						request, response);
				return;
			} else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
							.name())) {
				weiterleitungSystemSperrung(request, response);
				return;
			} else if (id.equals(anfrage_id.AKTION_ADMIN_ANLEGEN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an BenutzerServlet weiter");
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_ANLEGEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_DATEN_AENDERN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an BenutzerServlet weiter");
				request
						.setAttribute(
								"anfrage_id",
								BenutzerServlet.anfrage_id.BENUTZERDATEN_AENDERN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id.equals(anfrage_id.JSP_ZENTRUM_AENDERN.name())) {
				Logger.getLogger(this.getClass()).debug(
						"Leite Anfrage an ZentrumServlet weiter");
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.ZENTRUM_AENDERN.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			} else if(id.equals(anfrage_id.JSP_ADMIN_ANLEGEN.name())){
				ZentrumServlet.bindeZentrenListeAnRequest(request);
				request.getRequestDispatcher(Jsp.ADMIN_ANLEGEN).forward(request,
						response);
				}

			// [end]
			// WEITERLEITUNGEN FUER ZENTRUMSERVLET
			// [start]
			
			
			else if (id.equals(anfrage_id.JSP_ZENTRUM_ANSEHEN.name())) {
				request
						.setAttribute(
								"anfrage_id",
								ZentrumServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}
			
			else if (id.equals(anfrage_id.JSP_ZENTRUM_ANLEGEN.name())) {
				request
						.setAttribute(
								"anfrage_id",
								ZentrumServlet.anfrage_id.ClASS_DISPATCHERSERVLET_ZENTRUM_ANLEGEN
										.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			}
			// Benutzer suchen
			else if (id.equals(anfrage_id.BENUTZER_SUCHEN.name())) {
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			}

			else if (id.equals(anfrage_id.JSP_PASSWORT_VERGESSEN.name())) {
				request
						.setAttribute(
								Parameter.anfrage_id,
								BenutzerServlet.anfrage_id.CLASS_DISPATCHERSERVLET_PASSWORT_VERGESSEN
										.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			}

			// [end]

			// WEITERLEITUNG FUER STUDIESERVLET
			// [start]

			else if (id.equals(anfrage_id.JSP_STUDIE_ANSEHEN.name())) {
				request.setAttribute(
						DispatcherServlet.requestParameter.ANFRAGE_Id.name(),
						StudieServlet.anfrage_id.JSP_STUDIE_ANSEHEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			}
			else if (id.equals(anfrage_id.JSP_ZENTRUM_ANZEIGEN.name())) {

				if (request.getParameter(Parameter.filter) == null) {
					request.setAttribute("listeZentren", null);
				} 
				request.setAttribute(
						DispatcherServlet.requestParameter.ANFRAGE_Id.name(),
						StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE
					.name())) {

				// neue Studie anlegen
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANFRAGE_Id
										.name(),
								StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN_NEUESTUDIE
										.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_AUSWAEHLEN.toString())) {
				if (request.getParameter(Parameter.filter) == null) {
					// Studie wurde ausgewaehlt
					request.setAttribute(
							DispatcherServlet.requestParameter.ANFRAGE_Id
									.name(),
							StudieServlet.anfrage_id.AKTION_STUDIE_AUSGEWAEHLT
									.toString());

				} else {
					// auf der studie_auswaehlen.jsp wird die liste gefiltert
					request.setAttribute(
							DispatcherServlet.requestParameter.ANFRAGE_Id
									.name(),
							StudieServlet.anfrage_id.AKTION_STUDIE_AUSWAEHLEN
									.toString());

				}
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_SIMULATION.toString())) {
				// Weiterleitung auf die Simulation seite
				request.getRequestDispatcher(Jsp.SIMULATION).forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN.name())) {

				// neue Studie anlegen
				request.setAttribute(
						DispatcherServlet.requestParameter.ANFRAGE_Id.name(),
						StudieServlet.anfrage_id.AKTION_STUDIE_ANLEGEN.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STRATA
					.name())) {

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
														.toString()))));
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
														.toString()))) + 1);
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STUDIENARM
					.name())) {

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
														.toString()))));
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
														.toString()))) + 1);
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STRATA
					.name())) {

				// neue Strata zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
														.toString()))));
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
														.toString()))) - 1);
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals(anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STUDIENARM
					.name())) {

				// neuer Studienarm zu Studie
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_STRATA
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_STRATA
														.toString()))));
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANZAHL_ARME
										.toString(),
								(Integer
										.parseInt(request
												.getParameter(DispatcherServlet.requestParameter.ANZAHL_ARME
														.toString()))) - 1);
				request.getRequestDispatcher(Jsp.STUDIE_ANLEGEN).forward(
						request, response);

			} else if (id.equals((anfrage_id.JSP_STUDIE_PAUSIEREN_EINS.name()))) {
				// Studie pausieren
				request
						.setAttribute(
								DispatcherServlet.requestParameter.ANFRAGE_Id
										.name(),
								StudieServlet.anfrage_id.AKTION_STUDIE_PAUSIEREN
										.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id
					.equals((anfrage_id.JSP_STUDIE_FORTSETZEN_EINS.name()))) {
				// Studie fortsetzen
				request.setAttribute(
						DispatcherServlet.requestParameter.ANFRAGE_Id.name(),
						StudieServlet.anfrage_id.AKTION_STUDIE_FORTSETZEN
								.name());
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);

			}

			else if (id.equals((anfrage_id.JSP_STUDIE_AENDERN.name()))) {

				request.setAttribute(
						DispatcherServlet.requestParameter.ANFRAGE_Id.name(),
						StudieServlet.anfrage_id.AKTION_STUDIE_AENDERN);
				request.getRequestDispatcher("StudieServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.BENUTZER_SUCHEN.name())) {
				request.setAttribute("anfrage_id",
						BenutzerServlet.anfrage_id.AKTION_BENUTZER_SUCHEN
								.name());
				request.getRequestDispatcher("BenutzerServlet").forward(
						request, response);
			} else if (id
					.equals(DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN
							.name())) {
				weiterleitungSystemSperrung(request, response);
			} else if (id.equals(anfrage_id.AKTION_LOGOUT.name())) {
				// Logger.getLogger(this.getClass()).fatal("Benutzer
				// ausloggen");
				loggeBenutzerAus(request, response);
				return;
			} else if (id.equals(anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name())) {
				request.setAttribute("anfrage_id",
						ZentrumServlet.anfrage_id.AKTION_ZENTRUM_ANZEIGEN_ADMIN
								.name());
				request.getRequestDispatcher("ZentrumServlet").forward(request,
						response);
			} else if (id.equals(anfrage_id.JSP_HEADER_IMPRESSUM.name())) {

				request.setAttribute("anfrage_id",
						anfrage_id.JSP_HEADER_IMPRESSUM.name());
				request.getRequestDispatcher(Jsp.IMPRESSUM).forward(request,
						response);

			} else if (id
					.equals(anfrage_id.JSP_HEADER_NACHRICHTENDIENST.name())) {
				request.setAttribute(DispatcherServlet.requestParameter.TITEL.toString(),JspTitel.NACHRICHTENDIENST);
				request.getRequestDispatcher(Jsp.NACHRICHTENDIENST).forward(
						request, response);
			}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_STUDIEN_ANZEIGEN.name())){
				//TODO noch fertig machen
				request.getRequestDispatcher(Jsp.STUDIE_AUSWAEHLEN).forward(
						request, response);
				
			}
			else if (id.equals(anfrage_id.JSP_INC_MENUE_DATEN_AENDERN.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.DATEN_AENDERN).forward(
						request, response);
				
			}
			else if (id.equals(anfrage_id.JSP_INC_MENUE_STUDIENAERZTE_LISTE.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.STUDIENARZTE_LISTE).forward(
						request, response);
				
			}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_ADMIN_LISTE.name())){
//				TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.ADMIN_LISTE).forward(
						request, response);
			
			}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_PATIENT_HINZUFUEGEN.name())){
				//TODO weiterleitung?!
						request.getRequestDispatcher(Jsp.PATIENT_HINZUFUEGEN).forward(
								request, response);	
			}			
			else if(id.equals(anfrage_id.JSP_INC_MENUE_SYSTEMADMINISTRATION.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.SYSTEMADMINISTRATION).forward(
						request, response);	
	}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_SYSTEMSPERREN.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.SYSTEM_SPERREN).forward(
						request, response);	
	}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_ADMIN_ANLEGEN.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.ADMIN_ANLEGEN).forward(
						request, response);	
	}
			else if(id.equals(anfrage_id.JSP_INC_MENUE_STUDIENLEITER_ANLEGEN.name())){
				//TODO weiterleitung?!
				request.getRequestDispatcher(Jsp.STUDIENLEITER_ANLEGEN).forward(
						request, response);	
	}
			
			// [end]

			// SONSTIGE WEITERLEITUNGEN
			// Schwerer Fehler: Falscher Request
			else {
				System.out.println("Scheiße");
				System.out.println(idAttribute+" "+id);
				Logger.getLogger(this.getClass()).debug(
						"Kein Block in POST fuer die ID '" + id + "' gefunden");
				// TODO Hier muss noch entschieden werden,was passiert
				// Vorschlag: Konnte man als potentienllen Angriff werten und
				// entsprechend loggen --BTheel
			}

		}

	}// doPost

	/**
	 * 
	 * Prueft, ob die ubermittelte SessionID noch gueltig ist und ob an der
	 * Session ein Benutzer angehaengt ist.<br>
	 * Ist dies der Fall, so ist der Benutzer im System angemeldet.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @return <code>true</code>, wenn der Benutzer asngemeldet ist,
	 *         anderenfalls <code>false</code>.
	 */
	protected static boolean isBenutzerAngemeldet(HttpServletRequest request) {
		Logger.getLogger(DispatcherServlet.class).debug(
				"DispatcherServlet.isBenutzerAngemeldet()");
		boolean isSessionGueltig = request.isRequestedSessionIdValid();
		Logger.getLogger(DispatcherServlet.class).debug(
				"Pruefe: Session noch gueltg? " + isSessionGueltig);

		boolean isKontoangebunden = ((request.getSession()
				.getAttribute("aBenutzer")) != null);
		Logger.getLogger(DispatcherServlet.class).debug(
				"Pruefe: Benutzerkonto an Session gebunden? "
						+ isKontoangebunden);
		return (isSessionGueltig & isKontoangebunden);//
	}

	/**
	 * Loggt den Benutzer aus dem System aus und ruft anschlieszend die Methode
	 * {@link #weiterleitungAufIndex(HttpServletRequest, HttpServletResponse)}
	 * auf
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void loggeBenutzerAus(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Logger.getLogger(this.getClass()).debug("loggeBenutzerAus()");
		LogAktion a = new LogAktion("Benutzer hat sich ausgeloggt",
				(BenutzerkontoBean) request.getSession().getAttribute(
						"aBenutzer"));
		Logger.getLogger(LogLayout.LOGIN_LOGOUT).info(a);
		request.getSession().invalidate(); // Alte session zerstoeren
		request.getSession(); // Neue session eroeffnetn
		weiterleitungAufIndex(request, response);// Weiterleitung
	}

	/**
	 * Leitet die Anfrage auf den korrekten Index weiter<br>
	 * Ist das System gesperrt, so wird die Datei 'index_gesperrt.jsp'
	 * aufgerufen, anderenfalls die Datei 'index.jsp'
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void weiterleitungAufIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Session immer killen wenn auf Indexjsp
		try {
			request.getSession(false).invalidate();
		} catch (NullPointerException e) {
			// FIXME was soll hier passieren, dhaehn, einfach nix?
		}
		if (istSystemGesperrt) {// System gesperrt
			Logger
					.getLogger(this.getClass())
					.debug(
							"System gesperrt, leite nach 'index_gesperrt.jsp' um (korrekter Ablauf) ");
			request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
					.toString(), meldungSystemGesperrt);
			request.getRequestDispatcher("index_gesperrt.jsp").forward(request,
					response);
			return;
		} else {// System offen
			Logger
					.getLogger(this.getClass())
					.debug(
							"System offen, leite nach 'index.jsp' um' (korrekter Ablauf)");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
			return;
		}
	}

	/**
	 * Leitet die Anfrage auf die Seite '/system_sperren_main.jsp' weiter und
	 * bindet die dort benoetigten Werte an den Request.
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void weiterleitungSystemSperrung(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
				istSystemGesperrt);
		request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
				.toString(), meldungSystemGesperrt);
		request.getRequestDispatcher("/system_sperren_main.jsp").forward(
				request, response);
		return;

	}

	/**
	 * Leitet die Anfrage nach Authentifikation an das BenutzerServlet weiter
	 * 
	 * @param request
	 *            Der Request fuer das Servlet.
	 * @param response
	 *            Der Response Servlet.
	 * @throws IOException
	 *             Falls Fehler in den E/A-Verarbeitung.
	 * @throws ServletException
	 *             Falls Fehler in der HTTP-Verarbeitung auftreten.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	private void weiterleitungBenutzerAnmelden(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Logger.getLogger(this.getClass()).debug(
				"DispatcherServlet.weiterleitungBenutzerAnmelden()");
		request.setAttribute("anfrage_id", "CLASS_DISPATCHERSERVLET_LOGIN1");
		// request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
		// istSystemGesperrt);
		request.setAttribute(requestParameter.IST_SYSTEM_GESPERRT.toString(),
				istSystemGesperrt);
		if (istSystemGesperrt) {
			// Request verliert Attr. deshalb neu setzten
			request.setAttribute(requestParameter.MITTEILUNG_SYSTEM_GESPERRT
					.toString(), meldungSystemGesperrt);

		}
		request.getRequestDispatcher("BenutzerServlet").forward(request,
				response);

	}

	/**
	 * Liefert den Systemstatus [gesperrt|nicht gesperrt]
	 * 
	 * @return the istSystemGesperrt
	 */
	public boolean istSystemGesperrt() {
		return istSystemGesperrt;
	}

	/**
	 * Setzt den Status des Systems entsprechend des Parameters
	 * 
	 * @param istSystemGesperrt
	 *            <code>true</code> sperrt das System, <code>false</code>
	 *            entsperrt es wieder
	 */
	public void setSystemGesperrt(boolean istSystemGesperrt) {
		this.istSystemGesperrt = istSystemGesperrt;
		// Config.setSystemGesperrt(istSystemGesperrt);
		Logger.getLogger(this.getClass()).debug(
				"System gesperrt geaendert nach " + istSystemGesperrt);

	}

	/**
	 * Liefert die Meldung des Systems/Sperrungsgrund
	 * 
	 * @return Sperungsgrund
	 */
	public String getMeldungSystemGesperrt() {
		return meldungSystemGesperrt;
	}

	/**
	 * Setzt die Fehlermeldung im Dispatcher ({@link DispatcherServlet#meldungSystemGesperrt})
	 * und speichert die Meldung in der Config
	 * 
	 * @param meldungSystemGesperrt
	 *            the systemsperrungFehlermeldung to set
	 */
	public void setMeldungSystemGesperrt(String meldungSystemGesperrt) {
		System.out.println(meldungSystemGesperrt);
		this.meldungSystemGesperrt = meldungSystemGesperrt;
		// Config.setMitteilungSystemsperrung(meldungSystemGesperrt);
	}
}// DispatcherServlet
