<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="Cinema"/></title>
        <meta charset="UTF-8">
    </head>
    <body>
    <form method="POST" action="/app/cinema/user/login">
      <div class="container">
        <label for="login"><b><lang:print message="Login"/></b></label>
        <input type="text" placeholder=<lang:print message="Login"/> name="login" required>

        <label for="password"><b><lang:print message="Password"/></b></label>
        <input type="password" placeholder=<lang:print message="Password"/> name="password" required>

        <button type="submit"><lang:print message="Login_as_user"/></button></form>

       <form method="GET" action="/app/cinema/mainpage">
           <button type="submit"><lang:print message="Login_as_guest"/></button>
       </form>

       <form method="GET" action="/app/registeruser.jsp">
            <button type="submit"><lang:print message="Sign_up"/></button>
        </form>

       <form action="/app/cinema/user/change/language" method="GET">
          <input type="hidden" name="view" value=""/>
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
    </body>

</html>