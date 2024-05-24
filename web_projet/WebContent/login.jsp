<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Connexion</title>
  
    <!-- Bulma CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 30px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }
        .container {
            margin-top: 100px;
        }
    </style>
</head>
<body>
<section class="hero is-fullheight is-light">
    <div class="hero-body">
        <div class="container">
            <div class="columns is-centered">
                <div class="column is-5-tablet is-4-desktop is-3-widescreen">
                    <div class="form-container">
                        <h2 class="title has-text-centered">Sign In</h2>
                        <form action="LoginControleur" method="post">
                            <div class="field">
                                <label class="label" for="login">Login</label>
                                <div class="control">
                                    <input class="input" type="text" id="login" name="login" required>
                                </div>
                            </div>
                            <div class="field">
                                <label class="label" for="password">Password</label>
                                <div class="control">
                                    <input class="input" type="password" id="password" name="password" required>
                                </div>
                            </div>
                            <div class="field">
                                <div class="control">
                                    <button type="submit" class="button is-primary is-fullwidth">Connexion</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Bulma JS (if needed) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/js/bulma.js"></script>
</body>
</html>
