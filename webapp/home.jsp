<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP file</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            Welcome to home page, ${user.login}
        </p>
        <a href="/app/cinema/buyticket">
            Schedule of films
        </a>
    </body>
</html