<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            Welcome to home page, ${user.login}
        </p>

        <p><a href="/app/cinema/mainpageforclient">
            Schedule of films
        </p></a>
        <p><a href="/app/cinema/ticket/mytickets">
            My tickets
        </p></a>
    </body>
</html