package de.randi2.datenbank;

import java.util.Vector;

import de.randi2.datenbank.exceptions.DatenbankExceptions;

/**
 * Diese Klasse repraesentiert eine Schnittstelle fuer den Datenbankzugriff.
 * Methoden zum Suchen von Daten sowie zum Schreiben von Daten sind vorhanden.
 * 
 * @version $Id: DatenbankSchnittstelle.java 2395 2007-05-04 10:31:30Z tnoack $
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 */
public interface DatenbankSchnittstelle {

	/**
	 * Konstante fuer einen neuen Datensatz.
	 */
	public static final int NEUER_DATENSATZ = 1;
	/**
	 * Konstante fuer einen zu aktualisierende Datensatz.
	 */
	public static final int AKTUALISIERE_DATENSATZ = 2;
	/**
	 * Konstante fuer einen zu loeschenden Datensatz.
	 */
	public static final int LOESCHE_DATENSATZ = 3;

	/**
	 * <p>
	 * Diese Methode sucht alle in der Datenbank gespeicherten Objekte, welche
	 * der Filterung, die durch das uebergebene Objekt definiert wurde,
	 * entsprechen. Dabei werden alle Attribute welche != null bzw. !=
	 * Null-Konstanten (siehe {@link de.randi2.utility.NullKonstanten}) sind,
	 * als Filter verwendet.
	 * </p>
	 * <p>
	 * Strings werden mit LIKE abgeprueft.
	 * Numerische Werte werden auf Gleichheit geprueft.
	 * Datum Werte werden auf Gleichheit geprueft, außer es werden
	 * ,wie z.B. bei Studie das Start- und Enddatum, zwei Werte uebergeben.
	 * In diesem Fall wird der Bereich zwischen den beiden Werte gesucht.
	 * Boolsche Werte werden standardmaessig mit <code>false</code> initialisiert.
	 * Diese muessen bei Bedarf explizit gesetzt werden. Um z.B. alle Studien zu erhalten
	 * muessen zwei Abfragen geschickt werden. Diese duerfen sich nur im Wert aktiv unterscheiden. 
	 * Die gelieferten Vektoren (mit jeweils allen aktiven und nicht aktiven Studien) koennen
	 * dann addiert werden.
	 * </p>
	 * <p>
	 * Falls keine Objekte der Filterung entsprechen, wird ein leerer Vektor
	 * zurueckgegeben.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link DBObjekt} erben
	 * @param zuSuchendesObjekt
	 *            das zusuchende Objekt mit Attributen als Filter, darf nicht
	 *            <code>null</code> sein.
	 * @return ein Vektor von auf die Filterung zutreffenden Objekten
	 * @throws DatenbankExceptions
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankExceptions.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankExceptions.SUCHOBJEKT_IST_KEIN_FILTER: Das
	 *             Objekt, welches zum Suchen eingesetzt wurde, war kein Filter
	 *             (vlg. {@link DBObjekt})</li>
	 *             <li>DatenbankExceptions.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends DBObjekt> Vector<T> suchenObjekt(T zuSuchendesObjekt)
			throws DatenbankExceptions;

	/**
	 * <p>
	 * Diese Methode schreibt das uebergebene Objekt mit allen Attributen in die
	 * Datenbank und liefert bei Erfolg das geschriebene Objekt zurueck, bei
	 * welchem dann das Feld id gesetzt wurde.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link DBObjekt} erben
	 * @param zuSchreibendesObjekt
	 *            das zuschreibende Objekt
	 * @return das geschriebene Objekt. Objekt enthaelt jetzt die ID des
	 *         Datensatzes in der Datenbank
	 * @throws DatenbankExceptions
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankExceptions.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankExceptions.SCHREIBEN_ERR</li>
	 *             <li>DatenbankExceptions.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends DBObjekt> T schreibenObjekt(T zuSchreibendesObjekt)
			throws DatenbankExceptions;

	/**
	 * <p>
	 * Diese Methode liefert gezielt ein Objekt dessen ID bekannt ist.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link DBObjekt} erben
	 * @param id
	 *            Die ID des zu suchenden Objektes.
	 * @param nullObjekt
	 *            Ein Nullobjekt der jeweiligen Klasse.
	 * @return Das gesuchte Objekt.
	 * @throws DatenbankExceptions
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankExceptions.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankExceptions.ID_NICHT_VORHANDEN</li>
	 *             <li>DatenbankExceptions.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends DBObjekt> T suchenObjektId(long id, T nullObjekt)
			throws DatenbankExceptions;

	/**
	 * <p>
	 * Diese Methode liefert zu dem gegebenen <code>vater</code> eine Liste
	 * der zugehoerigen Kinder. Die Methode bietet wahlweise die Moeglichkeit
	 * ueber gesetzte Suchwoerter in dem uebergebenen Kind-Objekt, die Liste der
	 * Kinder einzuschraenken oder durch Verwendung eines Null-Objekts die
	 * gesamte Liste der Kinder auszugeben.
	 * </p>
	 * 
	 * @param <T>
	 *            Der Typ des Kind-Objekts.
	 * @param <U>
	 *            Der Typ des Vater-Objekts.
	 * @param vater
	 *            Vater-Objekt, dessen Mitglieder/Kinder gesucht werden sollen.
	 *            Das Objekt muss auf jeden Fall die eigene ID enthalten, die es
	 *            in der Datenbank identifiziert.
	 * @param kind
	 *            Muster des zu suchenden Kind-Objektes. Gesetzte Attribute
	 *            beschraenken die Liste auf selbige ein. Ein Null-Objekt
	 *            bewirkt die Rueckgabe der gesamten Liste.
	 * @return Die Liste der gefundenen Kinder.
	 * @throws DatenbankExceptions
	 * 				Folgende Messages sind moeglich:
	 * 				<ul>
	 * 					<li>DatenbankExceptions.CONNECTION_ERR</li>
	 * 				</ul>
	 */
	<T extends DBObjekt, U extends DBObjekt> Vector<T> suchenMitgliederObjekte(
			U vater, T kind) throws DatenbankExceptions;

	/**
	 * <p>
	 * Diese Methode loescht das uebergebene Objekt aus der Datenbank.
	 * </p>
	 * 
	 * @param <T>
	 *            Klasse des Attributes, muss von {@link DBObjekt} erben
	 * @param zuLoeschendesObjekt
	 *            Objekt das geloescht werden soll. Die ID des zuloeschenden
	 *            Objekts muss gesetzt sein.
	 * @throws DatenbankExceptions
	 *             Folgende Messages sind moeglich:
	 *             <ul>
	 *             <li>DatenbankExceptions.ARGUMENT_IST_NULL: Versuchter
	 *             Methodenaufruf mit <code>null</code> </li>
	 *             <li>DatenbankExceptions.LOESCHEN_ERR</li>
	 *             <li>DatenbankExceptions.CONNECTION_ERR</li>
	 *             </ul>
	 */
	<T extends DBObjekt> void loeschenObjekt(T zuLoeschendesObjekt)
			throws DatenbankExceptions;
	
	/**
	 * <p>
	 * Diese Methode liefert zu dem gegebenen <code>vater</code> das zugehoerige 
	 * Kind. Ein Null-Objekt erlaubt die Rückgabe des Kindobjektes.
	 * </p>
	 * 
	 * @param <T>
	 *            Der Typ des Kind-Objekts.
	 * @param <U>
	 *            Der Typ des Vater-Objekts.
	 * @param vater
	 *            Vater-Objekt, dessen Mitglieder/Kinder gesucht werden sollen.
	 *            Das Objekt muss auf jeden Fall die eigene ID enthalten, die es
	 *            in der Datenbank identifiziert.
	 * @param kind
	 *            NullObjekt des Kindes.
	 * @return Das gefundene Kind.
	 * @throws DatenbankExceptions
	 * 				Folgende Messages sind moeglich:
	 * 				<ul>
	 * 					<li>DatenbankExceptions.CONNECTION_ERR</li>
	 * 					<li>DatenbankExceptions.VECTOR_RELATION_FEHLER</li>
	 * 				</ul>
	 */
	<T extends DBObjekt, U extends DBObjekt> T suchenMitgliedEinsZuEins(U vater, T kind) throws DatenbankExceptions;

}
