<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List, javax.naming.InitialContext, javax.naming.NamingException, services.CongeSessionRemote, domaine.Conge, domaine.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Liste des Congés</title>

    <!-- Materialize CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet">
    <!-- Custom CSS for styling -->
    <style>
        body {
            background-color: #f5f5f5;
        }
        .container {
            margin-top: 50px;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .SOLLICITE { background-color: #ffeb3b; }
        .VALIDE { background-color: #8bc34a; }
        .REFUSE { background-color: #f44336; }
        .ANNULE { background-color: #b0bec5; }
        .EN_COURS { background-color: #03a9f4; }
        .ARRETE { background-color: #ff9800; }
        .FINI { background-color: #e1bee7; }
        h1, h2 {
            color: #212121;
        }
        table.striped > tbody > tr:hover {
            background-color: #f1f1f1;
        }
        .form-inline {
            margin-bottom: 20px;
        }
        .btn {
            margin-right: 5px;
        }
        .deconnexion-btn {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    Long userId = user.getId();
    
    String selectedYear = request.getParameter("year");
    String nom = user.getNom();
    int year;
    if (selectedYear != null) {
        year = Integer.parseInt(selectedYear);
    } else {
        year = 0;
    }
    String selectedEtat = request.getParameter("etat");
%>
<div class="container">
    <h1 class="mt-5">Bienvenue admin <%= nom %></h1>
    <div>
        <h2>Liste des Congés</h2>
        <form method="post" action="AcceuilAdmin.jsp" class="form-inline mb-3">
            <div class="input-field col s12 m6">
                <select name="year" id="year" onchange="this.form.submit()">
                    <option value="0">Tous</option>
                    <% int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                       for (int i = currentYear + 10; i >= currentYear - 10; i--) {
                           if (i == year) { %>
                               <option value="<%= i %>" selected><%= i %></option>
                           <% } else { %>
                               <option value="<%= i %>"><%= i %></option>
                           <% }
                       } %>
                </select>
                <label for="year">Sélectionnez une année:</label>
            </div>

            <div class="input-field col s12 m6">
                <select
name="etat" id="etat" onchange="this.form.submit()">
                    <option value="0">Tous</option>
                    <option value="SOLLICITE" <%= selectedEtat != null && selectedEtat.equals("SOLLICITE") ? "selected" : "" %>>SOLLICITE</option>
                    <option value="VALIDE" <%= selectedEtat != null && selectedEtat.equals("VALIDE") ? "selected" : "" %>>VALIDE</option>
                    <option value="REFUSE" <%= selectedEtat != null && selectedEtat.equals("REFUSE") ? "selected" : "" %>>REFUSE</option>
                    <option value="ANNULE" <%= selectedEtat != null && selectedEtat.equals("ANNULE") ? "selected" : "" %>>ANNULE</option>
                    <option value="EN_COURS" <%= selectedEtat != null && selectedEtat.equals("EN_COURS") ? "selected" : "" %>>EN COURS</option>
                    <option value="ARRETE" <%= selectedEtat != null && selectedEtat.equals("ARRETE") ? "selected" : "" %>>ARRETE</option>
                    <option value="FINI" <%= selectedEtat != null && selectedEtat.equals("FINI") ? "selected" : "" %>>FINI</option>
                </select>
                <label for="etat">Sélectionnez un état:</label>
            </div>
        </form>
        <hr>
        <table class="striped responsive-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Description</th>
                    <th>Date de Début</th>
                    <th>Date de Fin</th>
                    <th>Date de Rupture</th>
                    <th>État</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody style="margin-top:20px;">
                <%  
                    try {
                        InitialContext context = new InitialContext();
                        CongeSessionRemote SessionRemote = (CongeSessionRemote) context.lookup("ejb:/ejb_projet/CN!services.CongeSessionRemote");
                        List<Conge> Conges = SessionRemote.getAllCongeAdm();
                        if (selectedEtat != null && !selectedEtat.equals("0")) {
                            Conges = SessionRemote.getAllCongeAdmEtat(selectedEtat);
                        } else {
                            if (year != 0) {
                                Conges = SessionRemote.getAllCongeAdmYear(year);
                            } else {
                                Conges = SessionRemote.getAllCongeAdm();
                            }
                        }
                        if (!Conges.isEmpty()) {
                            for (Conge c : Conges) { %>
                                <tr style="margin-top:20px;">
                                    <td><%= c.getId() %></td>
                                    <td><%= c.getDescription() %></td>
                                    <td><%= c.getDateDeb() %></td>
                                    <td><%= c.getDateFin() %></td>
                                    <td><%= c.getDateRupture() %></td>
                                    <td style="margin-top: 50px;" class="<%= c.getEtat() %>"><%= c.getEtat() %></td>
                                    <td>
                                        <% if (c.getEtat().equals("SOLLICITE")) { %>
                                            <form action="ValiderControleur" method="post" class="d-inline">
                                                <input type="hidden" name="idConge" value="<%= c.getId() %>">
                                                <button type="submit" class="btn btn-small green">Valider</button>
                                            </form>
                                            <form action="RefuserControleur" method="post" class="d-inline">
                                                <input type="hidden" name="idConge" value="<%= c.getId() %>">
                                                <button type="submit" class="btn btn-small red">Refuser</button>
                                            </form>
                                        <% } else if (c.getEtat().equals("EN_COURS")) { %>
                                            <form action="ArreterControleur" method="post" class="d-inline">
                                                <input type="hidden" name="idConge" value="<%= c.getId() %>">
                                                <button type="submit" class="btn btn-small orange">Arrêter</button>
                                            </form>
                                        <% } else if (c.getEtat().equals("VALIDE")) { %>
                                            <form action="RefuserControleur" method="post" class="d-inline">
                                                <input type="hidden" name="idConge" value="<%= c.getId() %>">
                                                <button type="submit" class="btn btn-small red">Refuser</button>
                                            </form>
                                        <% } %>
                                    </td>
                                </tr>
                            <% }
                        } else { %>
                            <tr>
                                <td colspan="7" class="center-align">Aucun Congé</td> 
                            </tr>
                        <% }
                    } catch (NamingException e) {
                        e.printStackTrace(); 
                    }
                %>
            </tbody>
        </table>
        <hr>   
        <a href="UserDeconnexionController" onclick="return confirm('Voulez-vous vraiment quitter ?')" class="btn red deconnexion-btn">Déconnexion</a>
    </div>
</div>

<!-- Materialize JS and dependencies -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems);
    });
</script>
</body>
</html>
                