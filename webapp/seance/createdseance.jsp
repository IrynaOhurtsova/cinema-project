<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>New Seance</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            New seance created on ${seance.date} at ${seance.time}
        </p>
        <a href="/app/cinema/mainpagewithdelete">
            Schedule of films
        </a>
    </body>
</html>