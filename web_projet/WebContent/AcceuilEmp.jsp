<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List, javax.naming.InitialContext, javax.naming.NamingException, services.CongeSessionRemote, domaine.Conge, domaine.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Liste des Congés</title>
   
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 50px;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1, h2, h3 {
            color: #343a40;
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
        .table {
            border-collapse: collapse;
            width: 100%;
        }
        .table th, .table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #dee2e6;
        }
        .table th {
            background-color: #007bff;
            color: #fff;
            border-color: #007bff;
            text-transform: uppercase;
        }
        .table tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .table tbody tr:hover {
            background-color: #ddd;
        }
        .SOLLICITE { background-color: #ffc107; }
        .VALIDE { background-color: #28a745; }
        .REFUSE { background-color: #dc3545; }
        .ANNULE { background-color: #6c757d; }
        .EN_COURS { background-color: #17a2b8; }
        .ARRETE { background-color: #fd7e14; }
        .FINI { background-color: #e83e8c; }
    </style>
</head>
<body class="body">
<%
    User user = (User) session.getAttribute("user");
    Long userId = user.getId();
    String selectedYear = request.getParameter("year");
    int year;
    if (selectedYear != null) {
        year = Integer.parseInt(selectedYear);
    } else {
        year = 0;
    }
    String selectedEtat = request.getParameter("etat");
    String nom = user.getNom();
%>
<div class="container">
    <h1 class="mb-4">Bienvenue <%= nom %></h1>
    <div>
        <h2 class="mb-4">Liste des Congés</h2>
        <form method="post" action="AcceuilEmp.jsp" class="form-inline mb-3">
            <label for="year" class="mr-2"  >Sélectionnez une année:</label>
            <select name="year" id="year" class="form-control mr-3" onchange="this.form.submit()" style="width:400px; margin-left:200px;">
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

            <label for="etat"  style="margin-left: 10px; margin-top: 50px; margin-right:200px;" >Sélectionnez un état:</label>
            <select name="etat" id="etat" class="form-control" onchange="this.form.submit()" style="width:400px; margin-left:25px;margin-top: 50px; margin-right:100px;">
                <option value="0">Tous</option>
                <option value="SOLLICITE" <%= selectedEtat != null && selectedEtat.equals("SOLLICITE") ? "selected" : "" %>>SOLLICITE</option>
                <option value="VALIDE" <%= selectedEtat != null && selectedEtat.equals("VALIDE") ? "selected" : "" %>>VALIDE</option>
                <option value="REFUSE" <%= selectedEtat != null && selectedEtat.equals("REFUSE") ? "selected" : "" %>>REFUSE</option>
                <option value="ANNULE" <%= selectedEtat != null && selectedEtat.equals("ANNULE") ? "selected" : "" %>>ANNULE</option>
                <option value="EN_COURS" <%= selectedEtat != null && selectedEtat.equals("EN_COURS") ? "selected" : "" %>>EN COURS</option>
                <option value="ARRETE" <%= selectedEtat != null && selectedEtat.equals("ARRETE") ? "selected" : "" %>>ARRETE</option>
                <option value="FINI" <%= selectedEtat != null && selectedEtat.equals("FINI") ? "selected" : "" %>>FINI</option>
            </select>
        </form>
        <hr>
        <table class="table table-bordered table-hover">
            <thead>
                <tr style="background-color: green;">
                    <th>Description</th>
                    <th>Date Deb</th>
                    <th>Date Fin</th>
                    <th>Date Rupture</th>
                    <th>État</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% try {
                       InitialContext context = new InitialContext();
                       CongeSessionRemote SessionRemote = (CongeSessionRemote) context.lookup("ejb:/ejb_projet/CN!services.CongeSessionRemote");
                       List<Conge> Conges;
                       if (selectedEtat != null && !selectedEtat.equals("0")) {
                           Conges = SessionRemote.getAllCongeEmpEtat(userId, selectedEtat);
                       } else {
                           if (year != 0) {
                               Conges = SessionRemote.getAllCongeEmpYear(userId, year);
                           } else {
                               Conges = SessionRemote.getAllCongeEmp(userId);
                           }
                       }

                       if (!Conges.isEmpty()) {
                           for (Conge c : Conges) { %>
                               <tr>
                                   <td><%= c.getDescription() %></td>
                                   <td><%= c.getDateDeb() %></td>
                                   <td><%= c.getDateFin() %></td>
                                   <td><%= c.getDateRupture() %></td>
                                   <td class="<%= c.getEtat() %>"><%= c.getEtat() %></td>
                                   <% if (c.getEtat().equals("SOLLICITE")) { %>
                                       <td>
                                           <form action="AnnulerControleur" method="post" class="d-inline">
                                               <input type="hidden" name="idConge" value="<%= c.getId() %>">
                                               <input type="submit" value="Annuler" class="btn btn-danger btn-sm">
                                           </form>
                                       </td>
                                   <% } else { %>
                                       <td></td>
                                   <% } %>
                               </tr>
                           <% }
                       } else { %>
                           <tr>
                               <td colspan="6">Aucun Congé</td>
                           </tr>
                       <% }
                   } catch (NamingException e) {
                       e.printStackTrace();
                   }
                %>
            </tbody>
        </table>
        <% InitialContext context = new InitialContext();
        CongeSessionRemote SessionRemote = (CongeSessionRemote) context.lookup("ejb:/ejb_projet/CN!services.CongeSessionRemote"); %>
        <h3>Nombre de jours restants pour votre nouveau congé pour cette année :</h3>
        <%= SessionRemote.getNbJRes(userId) %>
        <hr>
        <a href="formConge.jsp" class="btn btn-primary">Ajouter un congé</a>
        <a href="UserDeconnexionController" onclick="return confirm('Voulez-vous vraiment quitter ?')" class="btn btn-danger">Déconnexion</a>
    </div>
</div>

</body>
</html>
