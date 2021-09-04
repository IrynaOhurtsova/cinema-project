<!DOCTYPE html>
<html>
    <head>
        <title>Creating seance</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            This is page for creating seance
        </p>
         <form method="GET" action="/app/cinema/seance/create">
              <label for="date">date:</label>
              <input type="date" id="date" name="date" >
              <label for="time">time:</label>
              <input type="time" id="time" name="time" value="11:00">
              <label for="title">title:</label>
              <input type="text" id="title" name="title" value="The Addams Family">
              <label for="price">price:</label>
              <input type="text" id="price" name="price" value="50">
              <label for="seating_capacity">seating_capacity</label>
              <input type="text" id="seatingCapacity" name="seatingCapacity" value="300">
              <label for="free_places">free_places</label>
              <input type="text" id="freePlaces" name="freePlaces" value="300">
              <input type="submit" value="create">
         </form>
    </body>
</html>