<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="Registration"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
                          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <p>
            <lang:print message="Create_login_and_password"/>
        </p>
        <form method="POST" action="/app/cinema/user/register">
              <div class="container">
                <label for="login"><b><lang:print message="Login"/></b></label>
                <input type="text" placeholder=<lang:print message="Login"/> name="login" required>

                <label for="password"><b><lang:print message="Password"/></b></label>
                <input type="password" placeholder=<lang:print message="Password"/> name="password" required>

                <button type="submit"><lang:print message="To_register"/></button></form>

               <form action="/app/cinema/user/change/language" method="POST">
                  <input type="hidden" name="view" value="/registeruser.jsp"/>
                           <select name="selectedLocale">
                                   <c:forEach var="locale" items="${sessionScope.locales}">
                                       <option value="${locale}">
                                           ${locale}
                                       </option>
                                   </c:forEach>
                           </select>
                  <input type="submit" value="<lang:print message="Change_language"/>">
              </form>

              </div>
    </body>

    <style>
          form {
              border: 3px solid #f1f1f1;
          }

          /* Full-width inputs */
          input[type=text], input[type=password] {
              text-align: center;
              width: 100%;
              padding: 12px 20px;
              margin: 8px 0;
              display: inline-block;
              border: 1px solid #ccc;
              box-sizing: border-box;
          }

          /* Set a style for all buttons */
          button {
              background-color: #4CAF50;
              color: white;
              padding: 14px 20px;
              margin: 8px 0;
              border: none;
              cursor: pointer;
              width: 100%;
          }

          /* Add a hover effect for buttons */
          button:hover {
              opacity: 0.8;
          }

          /* Extra style for the cancel button (red) */
          .cancelbtn {
              width: auto;
              padding: 10px 18px;
              background-color: #f44336;
          }

          /* Add padding to containers */
          .container {
              text-align: center;
              padding: 16px;
          }

          /* Change styles for span and cancel button on extra small screens */
          @media screen and (max-width: 300px) {
              span.password {
                  display: block;
                  float: none;
              }
              .cancelbtn {
                  width: 100%;
              }
          }
          </style>
</html>