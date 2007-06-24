<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.*"
	import="de.randi2.controller.StudieServlet"
	import="de.randi2.utility.Parameter"%>
<%
			Vector<ZentrumBean> zugehoerigeZentren = (Vector<ZentrumBean>) request
			.getAttribute("zugehoerigeZentren");

	Vector<ZentrumBean> nichtZugehoerigeZentren = (Vector<ZentrumBean>) request
			.getAttribute("nichtZugehoerigeZentren");

	Vector<ZentrumBean> gefilterteZentren = (Vector<ZentrumBean>) request
			.getAttribute("listeZentren");

	StudieBean aSession = (StudieBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.name());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Benutzerverwaltung</title>

<script type="text/javascript">
<!--
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
//-->
</script>

<link rel="stylesheet" type="text/css"
	href="js/ext/resources/css/ext-all.css" />
<script language="Javascript" src="js/motionpack.js"> </script>

<script type="text/javascript" src="js/ext/adapter/yui/yui-utilities.js"></script>

<script type="text/javascript"
	src="js/ext/adapter/yui/ext-yui-adapter.js"></script>
<!-- ENDLIBS -->

<script type="text/javascript" src="js/ext/ext-all.js"></script>

<script type="text/javascript" src="js/zentrum_anzeigen.js"></script>

<link rel="stylesheet" type="text/css" href="css/style.css">
</head>


<body onload="hideFilter();">

<%@include file="include/inc_header.jsp"%>

<div id="content">
<form action="StudieServlet" method="post"><input type="hidden"
	name="<%=Parameter.anfrage_id %>"
	value="<%=StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name() %>">

<h1>Zentrum suchen</h1>

<fieldset style="width: 90%;"><legend><b>Zentrum
suchen </b></legend><br />

&nbsp;&nbsp;&nbsp; <img alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /> <b>Filter ein-/ausblenden</b>

<div id="filterdiv" style="overflow:hidden; height: 75px;">
<table width="90%">
	<tr>
		<td>Name&nbsp;der&nbsp;Institution:</td>
		<td>Name&nbsp;der&nbsp;Abteilung:</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><input type="Text" name="<%=Parameter.zentrum.INSTITUTION.name() %>"
			value="" size="30" maxlength="50" /></td>
		<td><input type="Text" name="<%=Parameter.zentrum.ABTEILUNGSNAME.name() %>" 
		value="" size="30" maxlength="50" /></td>
		<td><input type="submit" name="Filtern" value="Filtern" /></td>
	</tr>
</table>

</div>
<br />
<br />

<table width="90%" border="0" cellspacing="5" cellpadding="2"
	id="zentren">
	<thead align="left">
		<tr style="background:#eeeeee;" class="tblrow1">
			<th width="35%">Name der Institution</th>
			<th width="35%">Abteilung</th>
			<th width="10%">Status</th>
			<th width="20%">Aktion</th>
		</tr>
	</thead>

	<%
		String reihe = "tblrow1";
		String aktiv = "aktiv";
		int anzahlZentren = aSession.getAnzahlZentren();
		int i = 0;

		if (gefilterteZentren != null) {
			while (i < gefilterteZentren.size()) {
				if (gefilterteZentren.elementAt(i).getIstAktiviert()) {
			aktiv = "aktiv";
				} else {
			aktiv = "inaktiv";
				}
	%>
	<tr class=<%=reihe %>>
		<td><%=gefilterteZentren.elementAt(i).getInstitution()%></td>
		<td><%=gefilterteZentren.elementAt(i).getAbteilung()%></td>
		<td align=center><%=aktiv%></td>
		<td width="40px">
		<%
		if (aBenutzer.getRolle().toString().equals("STUDIENLEITER")) {
		%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 			} else if (aBenutzer.getRolle().toString().equals(
 			"ADMINISTRATOR")) {
 %> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 }
 %>
		</td>
	</tr>

	<%
			if (reihe.equals("tblrow1")) {
			reihe = "tblrow2";
				} else {
			reihe = "tblrow1";
				}
				i++;
			}

		} else {

			if (zugehoerigeZentren != null) {
				while (i < anzahlZentren) {
			if (zugehoerigeZentren.elementAt(i).getIstAktiviert()) {
				aktiv = "aktiv";
			} else {
				aktiv = "inaktiv";
			}
	%>
	<tr class=<%=reihe %>>
		<td><%=zugehoerigeZentren.elementAt(i)
										.getInstitution()%></td>
		<td><%=zugehoerigeZentren.elementAt(i)
										.getAbteilung()%></td>
		<td align=center><%=aktiv%></td>
		<td width="40px">
		<%
						if (aBenutzer.getRolle().toString().equals(
						"STUDIENLEITER")) {
		%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 				} else if (aBenutzer.getRolle().toString().equals(
 				"ADMINISTRATOR")) {
 %> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 }
 %>
		</td>
	</tr>

	<%
				if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
			} else {
				reihe = "tblrow1";
			}
			i++;
				}
			}
			i = 0;
			if (nichtZugehoerigeZentren != null) {
				while (i < nichtZugehoerigeZentren.size()) {
			if (nichtZugehoerigeZentren.elementAt(i)
					.getIstAktiviert()) {
				aktiv = "aktiv";
			} else {
				aktiv = "inaktiv";
			}
	%>
	<tr class=<%=reihe %>>
		<td><%=nichtZugehoerigeZentren.elementAt(i)
										.getInstitution()%></td>
		<td><%=nichtZugehoerigeZentren.elementAt(i)
										.getAbteilung()%></td>
		<td><%=aktiv%></td>
		<td>
		<%
						if (aBenutzer.getRolle().toString().equals(
						"STUDIENLEITER")) {
		%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <br>
		<a href="zentrum_studie_zuordnen.jsp"> <input type="submit"
			name="studie_hinzufuegen" value="Zu Studie hinzuf&uuml;gen"></a>
		<%
						} else if (aBenutzer.getRolle().toString().equals(
						"ADMINISTRATOR")) {
		%> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 }
 %>
		</td>
	</tr>

	<%
				if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
			} else {
				reihe = "tblrow1";
			}
			i++;
				}
			}
		}
	%>


</table>
<table width="90%" border="0" cellspacing="5" cellpadding="2">

	<tr>
		<td><input type="button" name="zurueck" value="Zur&uuml;ck"
			tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>
</fieldset>
<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>


</form>
</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
