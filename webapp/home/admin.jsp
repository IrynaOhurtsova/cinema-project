<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="Home"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
                  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <p>
            <lang:print message="Welcome"/>${user.login}
        </p>

        <p><a href="/app/cinema/mainpage">
            <lang:print message="Schedule"/>
        </p></a>
        <p><a href="/app/seance/createnewseance.jsp">
            <lang:print message="Create_seance"/>
        </p></a>
        <p><a href="/app/cinema/user/logout">
             <lang:print message="Log_out"/>
        </p></a>
        <form action="/app/cinema/user/change/language" method="GET">
             <input type="hidden" name="view" value="/home/admin.jsp"/>
                    <select name="selectedLocale">
                        <c:forEach var="locale" items="${sessionScope.locales}">
                               <option value="${locale}">
                                    ${locale}
                               </option>
                         </c:forEach>
                    </select>
              <input type="submit" value="<lang:print message="Change_language"/>">
        </form>
    </body>
</html