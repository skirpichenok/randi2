<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_AUSWAEHLEN.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">

<h1>Zentrum suchen</h1>
<fieldset style="width: 60%"><legend><b>Zentrum
suchen </b></legend>

<p>Name&nbsp;der&nbsp;Institution:&nbsp;&nbsp; <br>
<input type="Text" name="name_institution" value="" size="30"
	maxlength="50"> <input type="button" name="filtern_button"
	value="Filtern"></p>

<table width="80%">
	<tr class="tblrow1" align="left">
		<th width="30%">Name der Institution</th>
		<th width="30%">Abteilung</th>
		<th width="20%">Status</th>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum1</td>
		<td>Abteilung xyz</td>
		<td>aktiv</td>

		<td>
		<form><!--	<% if (aBenutzer.getBenutzername().equals("sl")) { %> //-->
		<a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!-- <% } else if (aBenutzer.getBenutzername().equals("admin")){ %> //-->
		<a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!-- <% } %> //-->


		</form>
		</td>
	</tr>
	<tr class="tblrow1">
		<td>Zentrum2</td>
		<td>Abteilung2</td>
		<td>inaktiv</td>

		<td>
		<form><!--	<% if (aBenutzer.getBenutzername().equals("sl")) { %> //-->
		<a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!--	<% } else if (aBenutzer.getBenutzername().equals("admin")){ %> //-->
		<a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!--	<% } %> //-->

		</form>
		</td>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum2</td>
		<td>Abteilung87</td>
		<td>aktiv</td>

		<td>
		<form><!--		<% if (aBenutzer.getBenutzername().equals("sl")) { %> //-->
		<a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!--	<% } else if (aBenutzer.getBenutzername().equals("admin")){ %> //-->
		<a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <!--	<% } %> //-->

		</form>
		</td>
	</tr>
</table>





</fieldset>
</div>
<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>
<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
