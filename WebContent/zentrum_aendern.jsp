<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Zentrum aendern</title>

<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.Parameter"
	import="de.randi2.controller.DispatcherServlet"%>
</head>

<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form action="DispatcherServlet" method="post" name="user" id="user"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_AENDERN.name() %>">
<h1>Zentrum aendern</h1>

<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<%
		//Holen des Zentrums, das angezeigt und geaendert werden soll.
		//TODO: Muss noch KORRIGIERT werden!!!
		ZentrumBean aZentrum = aBenutzer.getZentrum();
	%>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="46" maxlength="40" name="Institution"
			value="<%if(aZentrum != null) {out.print(aZentrum.getInstitution());}%>"
			tabindex="1"></td>
	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="46" maxlength="40" name="Abteilung"
			value="<%if(aZentrum != null) {out.print(aZentrum.getAbteilung());}%>"
			tabindex="2"></td>
	</tr>
	<tr>
		<td>Strasse *
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Hausnr * <br>
		<input type="text" size="30" maxlength="30" name="Strasse"
			value="<%if(aZentrum != null) {out.print(aZentrum.getStrasse());}%>"
			tabindex="3"> &nbsp;&nbsp;&nbsp; <input type="text" size="8"
			maxlength="8" name="Hausnummer"
			value="<%if(aZentrum != null) {out.print(aZentrum.getHausnr());}%>"
			tabindex="4"></td>
	</tr>
	<tr>
		<td>PLZ * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ort * <br>
		<input type="text" size="6" maxlength="6" name="PLZ"
			value="<%if(aZentrum != null) {out.print(aZentrum.getPlz());}%>"
			tabindex="5">&nbsp;&nbsp;&nbsp; <input type="text" size="33"
			maxlength="33" name="Ort"
			value="<%if(aZentrum != null) {out.print(aZentrum.getOrt());}%>"
			tabindex="6"></td>

	</tr>
	<tr>
		<!-- Wenn Passwort nicht geaendert werden soll, dann wird bei leer gelassenen Feldern
		das alte Passwort weiter verwendet. -->
		<td>Passwort *<br>
		<input type="password" size="25" maxlength="30" name="Passwort"
			tabindex="6" value=""></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="password" size="25" maxlength="30" name="Passwort_wh"
			tabindex="7" value=""></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset><legend><b>Angaben zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size"38" maxlength="40" name="VornameA"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getVorname());}}%>"
			tabindex="7">&nbsp;&nbsp;&nbsp;</td>
		<td>Nachname *<br>
		<input type="text" size="38" maxlength="40" name="NachnameA"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getNachname());}}%>"
			tabindex="8"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich"
			<%if (aZentrum != null) {if(aZentrum.getAnsprechpartner() != null){if(aZentrum.getAnsprechpartner().getGeschlecht() == 'w'){out.print("checked");}}}%>>weiblich
		<input type="radio" name="maennlich"
			<%if (aZentrum != null) {if(aZentrum.getAnsprechpartner() != null){if(aZentrum.getAnsprechpartner().getGeschlecht() == 'm'){out.print("checked");}}}%>>m&auml;nnlich</td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size="40" maxlength="40" name="TelefonA"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getTelefonnummer());}}%>"
			tabindex="9"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="40" maxlength="40" name="FaxA"
			value="<%if(aZentrum != null) {
						if(aZentrum.getAnsprechpartner() != null) {
							String fax = aZentrum.getAnsprechpartner().getFax();
							if(fax != null) {
								out.print(fax);
							} else {
								out.print("");
							}
						}
					 }%>"
			tabindex="10"></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size="46" maxlength="50" name="EmailA"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getEmail());}}%>"
			tabindex="11"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
<br>

<table>
	<tr>
		<td><input type="submit" name="Submit" value="Zentrum aendern"
			tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13"></td>
	</tr>
</table>
</form>
<br>

&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>