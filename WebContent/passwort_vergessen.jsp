<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.utility.Parameter"%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Passwort vergessen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_nachricht.jsp"%>

<div id="content">

<h1>Passwort anfordern</h1>
<br>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="<%=Parameter.anfrage_id %>"
	value="<%=DispatcherServlet.anfrage_id.JSP_PASSWORT_VERGESSEN.name() %>">
<fieldset style="width:60%"><legend><b>Passwort
anfordern<br>
</b></legend>
<table>
	<tr>
		<td>Benutzername<br>
		<input type="text" size="20" maxlength="50" name="<%=Parameter.benutzerkonto.PASSWORT %>"
			tabindex="1"></td>

	</tr>

</table>
</fieldset>

<br>
<table>
	<tr>
		<td><input type="submit" name="anfordern" value="Neues Passwort anfordern"
			tabindex="2">&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Zur&uuml;ck zur Startseite"
			tabindex="3" onClick="top.location.href='index.jsp'"></td>
	</tr>
</table>
</form>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"></div>
</body>
</html>
