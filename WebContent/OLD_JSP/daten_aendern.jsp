<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="de.randi2.controller.*"%>
<%
	// es werden als Namen fuer die Formularfelder die Werte aus Parameter.person.* und Parameter.benutzerkonto.* benutzt.
	// die Parameter.anfrage_id fuer den Dispatcher ist DispatcherServlet.anfrage_id.JSP_DATEN_AENDERN.name()
	// ein Stellvertreter ist Pflicht bei den rollen admin und studienleiter

	request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.DATEN_AENDERN.toString());

	BenutzerkontoBean aBenutzer_form = (BenutzerkontoBean) (request
			.getSession())
			.getAttribute(DispatcherServlet.sessionParameter.A_Benutzer
			.toString());

	PersonBean aPerson = aBenutzer_form.getBenutzer();
	PersonBean aStellvertreter = aPerson.getStellvertreter();

	String aVorname = aPerson.getVorname();
	String aNachname = aPerson.getNachname();
	String aTitel = aPerson.getTitel().toString();
	char aGeschlecht = aPerson.getGeschlecht();
	String aTelefonnummer = aPerson.getTelefonnummer();
	String aHandynummer = aPerson.getHandynummer();
	String aFax = aPerson.getFax();
	String aEmail = aPerson.getEmail();

	String aStellvertreterVorname = null;
	String aStellvertreterNachname = null;
	String aStellvertreterTitel = null;
	String aStellvertreterTelefon = null;
	char aStellvertreterGeschlecht = NullKonstanten.NULL_CHAR;
	String aStellvertreterEmail = null;

	if (aStellvertreter != null) {

		aStellvertreterVorname = aStellvertreter.getVorname();
		aStellvertreterNachname = aStellvertreter.getNachname();

		if (aStellvertreter.getTitel() == null) {
			aStellvertreterTitel = PersonBean.Titel.KEIN_TITEL
			.toString();
		} else {
			aStellvertreterTitel = aStellvertreter.getTitel()
			.toString();

		}
		aStellvertreterGeschlecht = aPerson.getGeschlecht();
		aStellvertreterTelefon = aStellvertreter.getTelefonnummer();
		aStellvertreterEmail = aStellvertreter.getEmail();

	}

	if (aVorname == null) {

		aVorname = "";

	}
	if (aNachname == null) {

		aNachname = "";

	}

	if (request.getParameter(Parameter.person.TITEL.name()) == null) {

		aTitel = aPerson.getTitel().toString();

	} else {

		aTitel = request.getParameter(Parameter.person.TITEL.name());

	}

	if (request.getParameter(Parameter.person.TELEFONNUMMER.name()) == null) {

		aTelefonnummer = aPerson.getTelefonnummer();

	} else {

		aTelefonnummer = request
		.getParameter(Parameter.person.TELEFONNUMMER.name());

	}

	if (request.getParameter(Parameter.person.HANDYNUMMER.name()) == null) {

		aHandynummer = aPerson.getHandynummer();

	} else {

		aHandynummer = request
		.getParameter(Parameter.person.HANDYNUMMER.name());

	}

	if (request.getParameter(Parameter.person.FAX.name()) == null) {

		aFax = aPerson.getFax();

	} else {

		aFax = request.getParameter(Parameter.person.FAX.name());

	}

	if (aTelefonnummer == null) {

		aTelefonnummer = "";

	}

	if (aHandynummer == null) {

		aHandynummer = "";

	}

	if (aFax == null) {

		aFax = "";

	}

	if (aEmail == null) {
		aEmail = "";
	}

	if (request.getParameter(Parameter.person.STELLVERTRETER_VORNAME
			.name()) == null) {

		if (aStellvertreter != null)
			aStellvertreterVorname = aStellvertreter.getVorname();

	} else {

		aStellvertreterVorname = request
		.getParameter(Parameter.person.STELLVERTRETER_VORNAME
				.name());

	}

	if (aStellvertreterVorname == null) {

		aStellvertreterVorname = "";
	}

	if (request.getParameter(Parameter.person.STELLVERTRETER_NACHNAME
			.name()) == null) {

		if (aStellvertreter != null)
			aStellvertreterNachname = aStellvertreter.getNachname();

	} else {

		aStellvertreterNachname = request
		.getParameter(Parameter.person.STELLVERTRETER_NACHNAME
				.name());

	}

	if (aStellvertreterNachname == null) {

		aStellvertreterNachname = "";
	}

	if (request
			.getParameter(Parameter.person.STELLVERTRETER_TELEFONNUMMER
			.name()) == null) {

		if (aStellvertreter != null)
			aStellvertreterTelefon = aStellvertreter.getTelefonnummer();

	} else {

		aStellvertreterTelefon = request
		.getParameter(Parameter.person.STELLVERTRETER_TELEFONNUMMER
				.name());

	}

	if (aStellvertreterTelefon == null) {

		aStellvertreterTelefon = "";
	}

	if (request.getParameter(Parameter.person.STELLVERTRETER_EMAIL
			.name()) == null) {

		if (aStellvertreter != null)
			aStellvertreterEmail = aStellvertreter.getEmail();

	} else {

		aStellvertreterEmail = request
		.getParameter(Parameter.person.STELLVERTRETER_EMAIL
				.name());

	}

	if (aStellvertreterEmail == null) {

		aStellvertreterEmail = "";
	}

	if (request.getParameter(Parameter.person.STELLVERTRETER_GESCHLECHT
			.name()) == null) {

		if (aStellvertreter != null)
			aStellvertreterGeschlecht = aStellvertreter.getGeschlecht();

	} else {

		aStellvertreterGeschlecht = request.getParameter(
		Parameter.person.STELLVERTRETER_GESCHLECHT.name())
		.charAt(0);

	}

	if (aStellvertreterGeschlecht == NullKonstanten.NULL_LONG) {

		aStellvertreterGeschlecht = 'w';

	}

	if (request.getParameter(Parameter.person.STELLVERTRETER_TITEL
			.name()) == null) {

		//aStellvertreterTitel = aStellvertreter.getTitel().toString();

	} else {

		aStellvertreterTitel = request
		.getParameter(Parameter.person.STELLVERTRETER_TITEL
				.name());

	}

	if (aStellvertreterTitel == null) {

		aStellvertreterTitel = PersonBean.Titel.KEIN_TITEL.toString();

	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_daten_aendern = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'form_daten_aendern'
    });
    
    var titel = new Ext.form.ComboBox({
        fieldLabel: 'Titel:',
        hiddenName:'<%=Parameter.person.TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titel'],
            data : [
			<%
				StringBuffer titel = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titel.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titel%>'],
			<%
					titel.delete(0, titel.length());
				}
			%>
            ]
        }),
        displayField:'titel',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aTitel %>',
        value:'<%=aTitel %>',
        selectOnFocus:true,
        editable:false,
        width:140
    });
    
	var vorname = new Ext.form.MiscField({
        fieldLabel: 'Vorname:',
        value: '<%=aVorname %>',
        width:190
	});    
    
	var nachname = new Ext.form.MiscField({
        fieldLabel: 'Nachname:',
        value: '<%=aNachname %>',
        width:190
	});    
    
	var geschlecht = new Ext.form.MiscField({
        fieldLabel: 'Geschlecht:',
        value: '<%
        	if(aGeschlecht=='m') {
        %>maennlich<%
        	} else if(aGeschlecht=='w') {
		%>weiblich<%
			}
		%>',
        width:190
	});    
        
    var passwort = new Ext.form.TextField({
        fieldLabel: 'Neues Passwort *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT.name() %>',
        value: '',
        allowBlank:true,
        width:190,
        inputType:'password',
        minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte ein Passwort eingeben!'
    });
    
    var passwort_wh = new Ext.form.TextField({
        fieldLabel: 'Neues Passwort wiederholen *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG.name() %>',
        value: '',
        allowBlank:true,
        width:190,
        inputType:'password',
		minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte das Passwort erneut eingeben!'
    });
    
    form_daten_aendern.fieldset({legend:'Pers&ouml;nliche Angaben',
    labelSeparator:''},
    titel,
    vorname,
    nachname,
    geschlecht,passwort, passwort_wh);

	var email = new Ext.form.MiscField({
        fieldLabel: 'E-Mail:',
        value: '<%=aEmail %>',
        width:190
	});     
    
    var telefon = new Ext.form.TextField({
        fieldLabel: 'Telefon *:',
        name: '<%=Parameter.person.TELEFONNUMMER.name() %>',
        value: '<%=aTelefonnummer %>',
        width:190,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        blankText:'Bitte Ihre Telefonnummer eintragen!'
    });
    
    var handy = new Ext.form.TextField({
        fieldLabel: 'Handy:',
        name: '<%=Parameter.person.HANDYNUMMER.name() %>',
        value: '<%=aHandynummer %>',
        width:190,
        allowBlank:true,
        minLength:7,
        maxLength:26,
        maxLengthText:'Handynummer muss 7 bis 26 Zeichen lang sein!',
        minLengthText:'Handynummer muss 7 bis 26 Zeichen lang sein!'
    });

    var fax = new Ext.form.TextField({
        fieldLabel: 'Fax:',
        name: '<%=Parameter.person.FAX.name() %>',
        value: '<%=aFax %>',
        width:190,
        allowBlank:true,
        minLength:6,
        maxLength:26,
        maxLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!'
    });

    form_daten_aendern.fieldset({legend:'Kontaktdaten',
    labelSeparator:''},
    email,
    telefon,
    handy,
    fax);
    
    <%
    
    if ((aBenutzer_form.getRolle().getRollenname()==Rolle.Rollen.ADMIN) || (aBenutzer_form.getRolle().getRollenname()==Rolle.Rollen.STUDIENLEITER)) {
    	
    
    
    %>
    
	var stellvertreter_vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.STELLVERTRETER_VORNAME.name() %>',
        value: '<%=aStellvertreterVorname %>',
        width:190,
        allowBlank:false,
        editable:true,
        minLength:2,
        maxLength:50,
        blankText:'Bitte den Vornamen des Stellvertreters eintragen!',
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!'
    });
    
	var stellvertreter_nachname = new Ext.form.TextField({
        fieldLabel: 'Nachname *:',
        name: '<%=Parameter.person.STELLVERTRETER_NACHNAME.name() %>',
        value: '<%=aStellvertreterNachname %>',
        width:190,
        allowBlank:false,
        editable:true,
        minLength:2,
        maxLength:50,
        blankText:'Bitte den Nachnamen des Stellvertreters eintragen!',
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!'
    });
    
    var stellvertreter_geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['geschlecht'],
            data : [['maennlich'],['weiblich']]
            
        }),
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        <%
        	if(aStellvertreterGeschlecht=='m') {
        %>
		value:'maennlich',
		<%
        	} else if(aStellvertreterGeschlecht=='w') {
		%>
		value:'weiblich',
		<%
			}
		%>
		selectOnFocus:true,
        editable:false,
        width:140,
        allowBlank:true,
        blankText:'Bitte das Geschlecht des Stellvertreters auswaehlen!'
    });
    
    var stellvertreter_titel = new Ext.form.ComboBox({
        fieldLabel: 'Titel *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titel'],
            data : [
			<%
				StringBuffer titel2 = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titel2.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titel2%>'],
			<%
					titel2.delete(0, titel2.length());
				}
			%>
            ]
        }),
        displayField:'titel',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aStellvertreterTitel %>',
        value:'<%=aStellvertreterTitel %>',
        selectOnFocus:true,
        editable:false,
        width:140
    });
    
    var stellvertreter_telefon = new Ext.form.TextField({
        fieldLabel: 'Telefon *:',
        name: '<%=Parameter.person.STELLVERTRETER_TELEFONNUMMER.name() %>',
        value: '<%=aStellvertreterTelefon %>',
        width:190,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        blankText:'Bitte die Telefonnummer des Stellvertreters eintragen!',
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!'
    });
    
    Ext.form.VTypes['emailText'] = 'Bitte eine gueltige E-Mail eintragen!';
    
    var stellvertreter_email = new Ext.form.TextField({
        fieldLabel: 'E-Mail *:',
        name: '<%=Parameter.person.STELLVERTRETER_EMAIL.name() %>',
        value: '<%=aStellvertreterEmail %>',
        width:190,
        allowBlank:false,
        minLength:2,
        maxLength:255,
        maxLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        minLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        blankText:'Bitte die E-Mail des Stellvertreters eintragen!',
        vtype:'email'
    });
    
    form_daten_aendern.fieldset({legend:'Angaben zum Stellvertreter',
    labelSeparator:''},
    stellvertreter_titel,
    stellvertreter_vorname,
    stellvertreter_nachname,
    stellvertreter_geschlecht,
    stellvertreter_telefon,
    stellvertreter_email);
    
    <%
    
    }
    
    %>
    
	form_daten_aendern.addButton('Speichern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_daten_aendern);
	
	


    form_daten_aendern.render('form_daten_aendern');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_daten_aendern.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_DATEN_AENDERN.name() %>'});	
    
    
   });
</script>
<script>
function eraseStellvertreter() {

	var frm = document.getElementById('form_daten_aendern');
	frm.<%=Parameter.person.STELLVERTRETER_NACHNAME %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_VORNAME %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_EMAIL %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_GESCHLECHT %>.value = 'w';
	frm.<%=Parameter.person.STELLVERTRETER_TELEFONNUMMER %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_TITEL %>.value = '<%=PersonBean.Titel.KEIN_TITEL.toString()%>';



}

</script>
</head>

<body>
<%@include file="include/inc_header.jsp"%>


<div id="content"><%@include file="include/inc_nachricht.jsp"%>
<h1>Daten &auml;ndern</h1>
<div id="form_daten_aendern"></div>


&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>