<!DOCTYPE html>
<html>
    <head>
        <title>JSP file</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            Please create login and password to get started
        </p>
         <form method="POST" action="/app/cinema/user/register">
              <label for="login">login:</label>
              <input type="text" id="login" name="login">
              <label for="pass">password:</label>
              <input type="password" id="password" name="password">
              <input type="submit" value="sign up">
         </form>
    </body>
</html>