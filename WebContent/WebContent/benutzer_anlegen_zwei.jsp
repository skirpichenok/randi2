<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Benutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<form>
<h1>Benutzer anlegen</h1>

<fieldset style="width: 60%"><legend><b>Zentrum suchen </b></legend>

<p>
Name&nbsp;der&nbsp;Institution:&nbsp;&nbsp;
<br>
<input type="Text" name="name_institution" value="" size="30" maxlength="50">
<input type="button" name="filtern_button" value="Filtern">
</p>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="40%">Name der Institution</th>
		<th width="30%">Abteilung</th>
		<th width="20%">Passwort</th>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum1</td>
		<td>Abteilung xyz</td>
		<td><input type="password" name="textfield2" tabindex="1"></td>
		<td><input type="button" name="bestaetigen" value="Weiter" tabindex="2" onclick="location.href='benutzer_anlegen_drei.jsp'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	
	</tr>
	<tr class="tblrow1">
		<td>Zentrum2</td>
		<td>Abteilung2</td>
				<td><input type="password" name="textfield2" tabindex="3"></td>
				<td><input type="button" name="bestaetigen" value="Weiter" tabindex="4" onclick="location.href='benutzer_anlegen_drei.jsp'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	<tr class="tblrow2">
		<td>Zentrum2</td>
		<td>Abteilung87</td>
				<td><input type="password" name="textfield2" tabindex="5"></td>
			<td><input type="button" name="bestaetigen" value="Weiter" tabindex="6" onclick="location.href='benutzer_anlegen_drei.jsp'" valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	
	</tr>
</table>
     
      
</fieldset>
</form>
<form>
		<table>
		<tr>
		<td><input type="button" name="abbrechen" value="Zur&uuml;ck" tabindex="2" onClick="location.href='benutzer_anlegen_eins.jsp'"></td>
		</tr>
		</table>
</form>
				<%@include file="include/inc_footer.jsp"%>
	</div>
	<div id="show_none">

	</div>
<div id="show_none">
</div>
</body>
</html>
