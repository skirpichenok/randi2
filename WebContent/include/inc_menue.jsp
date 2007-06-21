<%@ page import="de.randi2.model.fachklassen.Rolle"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%Rolle.Rollen aRolleMenue=((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getRolle().getRollenname(); %>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<%@page import="de.randi2.controller.StudieServlet"%>
<%@page import="de.randi2.controller.ZentrumServlet"%>
<div id="menue">

<div id="navigation">
<% if (aRolleMenue!=Rolle.Rollen.STATISTIKER) { %>
<ul>
	<li class="top_m">Benutzerverwaltung</li>
	<% if (aRolleMenue!=Rolle.Rollen.SYSOP) { %>
	<li><a class="sub_BV n" href="daten_aendern.jsp">Daten
	&auml;ndern</a></li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
	<li>
	<a class="sub_BV n" href="DispatcherServlet?anfrage_id=<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>">Benutzer suchen</a>
	</li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER) { %>
	<li><a class="sub_BV n" href="studienaerzte_liste.jsp">Studien&auml;rzte
	anzeigen</a></li>
	<%	} %>
	<% if (aRolleMenue==Rolle.Rollen.SYSOP) { %>
	<li><a class="sub_BV n" href="admin_liste.jsp">Admins anzeigen</a></li>
	<%	} %>
</ul>
<% } %> <% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER) { %>
<ul>
	<li class="top_m">Zentrenverwaltung</li>
	<li>
	<a class="sub_BV n" href="StudieServlet?anfrage_id=<%=StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name() %>">Zentren anzeigen</a>
	</li>
	
	
	<%-- } --%>
</ul>
<% } %>  <% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
<ul>
	<li class="top_m">Zentrenverwaltung</li>
	<li>
	<a class="sub_BV n" href="DispatcherServlet?anfrage_id=<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name() %>">Zentren anzeigen</a>
	</li>

</ul>
<% } %><% if (aRolleMenue!=Rolle.Rollen.SYSOP) { %>
<ul>
	<li class="top_m">Studienverwaltung</li>
	<% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
	<li><a class="sub_SV n entry" href="studie_auswaehlen.jsp">Studien
	anzeigen</a></li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER|| aRolleMenue==Rolle.Rollen.STUDIENARZT||aRolleMenue==Rolle.Rollen.STATISTIKER) { %>
	<li><a class="sub_SV n entry" href="studie_ansehen.jsp">Studie
	ansehen</a></li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENARZT) { %>
	<li><a class="sub_SV n entry" href="patient_hinzufuegen.jsp">Patient
	hinzuf&uuml;gen</a></li>
	<% } %>
</ul>
<% } %> <% if (aRolleMenue==Rolle.Rollen.SYSOP || aRolleMenue==Rolle.Rollen.ADMIN) { %>
<ul>
	<% if (aRolleMenue==Rolle.Rollen.SYSOP) { %>
	<li class="top_m"><a href="systemadministration.jsp">Systemadministration</a></li>
	<li class="last"><a class="sub_SA n entry"
		href="system_sperren.jsp">System sperren</a></li>
	<li class="last"><a class="sub_SA n entry"
		href="admin_anlegen.jsp">Admin anlegen</a></li>
	<% } else { %>
	<li class="top_m">Systemadministration</li>
	<li class="last"><a class="sub_SA n entry"
		href="studienleiter_anlegen.jsp">Studienleiter anlegen</a></li>
	<% } %>
</ul>
<% } %>
</div>
</div>
