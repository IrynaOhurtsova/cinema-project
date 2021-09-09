<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="Creating_a_seance"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
                          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <p>
            <lang:print message="Creating_a_seance"/>
        </p>

         <form method="GET" action="/app/cinema/seance/create"
         onsubmit="alert('<lang:print message="You_have_created_new_seance"/>')">
              <div class="container">
              <label for="date"><b><lang:print message="Date"/>:</b></label>
              <input type="date" id="date" name="date" >
              <label for="time"><b><lang:print message="Time"/>:</b></label>
              <input type="time" id="time" name="time" value="11:00">
              <label for="title"><b><lang:print message="Title"/>:</b></label>
              <input type="text" id="title" name="title" value="The Addams Family">
              <label for="price"><b><lang:print message="Price"/>:</b></label>
              <input type="text" id="price" name="price" value="50">
              <label for="seating_capacity"><b><lang:print message="Room_capacity"/>:</b></label>
              <input type="text" id="seatingCapacity" name="seatingCapacity" value="300">
              <label for="free_places"><b><lang:print message="Free_places"/></b>:</label>
              <input type="text" id="freePlaces" name="freePlaces" value="300">
              <button type="submit"><lang:print message="Create_seance"/></button>
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