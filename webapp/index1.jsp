<!DOCTYPE html>
<html>
    <head>
    <style>
    body{
                background: #eee; /* ���� ���� �������� */
    }
    .Myform{
                width:300px; /* ������ ����� */
                height: 225px; /* ������ ����� */
                background: #fff; /* ��� ����� */
                border-radius: 10px; /* ������������ ���� ����� */
                margin: 10% auto; /* ������ ������ � ������������ �� �������� */
                box-shadow: 2px 2px 4px 0px #000000; /* ���� ����� */
    }
    .Myform h1 {
                margin: 0; /* ������� ������� */
                background-color: #282830; /* ��� ��������� */
                border-radius: 10px 10px 0 0; /* ���������� ���� ������ */
                color: #fff; /* ���� ������ */
                font-size: 14px; /* ������ ������ */
                padding: 20px; /* ������� */
                text-align: center; /* ����������� ����� �� ������ */
                text-transform: uppercase; /* ��� ������� ��������� */
    }
    .inp{
                padding:20px; /* ������� */
    }
    .log{
                border: 1px solid #dcdcdc; /* ����� */
                padding: 12px 10px; /* ������� ������ */
                width: 260px; /* ������ */
                border-radius: 5px 5px 0 0; /* ������������ ���� ������ */
    }
    .pass{
                border: 1px solid #dcdcdc; /* ����� */
                padding: 12px 10px; /* ������� ������ */
                width: 260px; /* ������ */
                border-radius: 0px 0px 5px 5px; /* ������������ ���� ����� */
    }
    .btn{
                background: #1dabb8; /* ��� */
                border-radius: 5px; /* ������������ ���� */
                color: #fff; /* ���� ������ */
                float: right; /* ������������ ������ */
                font-weight: bold; /* ������ ����� */
                margin: 10px; /* ������� */
                padding: 12px 20px; /* ������ ��� ������ */
    }
    .info{
                width:130px; /* ������ */
                float: left; /* ������������ ����� */
                padding-top: 20px; /* ����� ������ ��� ������ */
    a{
                color:#999; /* ���� ������ */
                text-decoration: none; /* ������� ������������� */

    }
    a:hover{
                color: #1dabb8; /* ���� ������ ��� ��������� */

    }
    </style>
        <title>Cinema</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <div class="Myform">
        <h1>
            Hi, this is cinema project please login to get started
        </h1>
        <div class="inp">
         <form method="POST" action="/app/cinema/user/login">
              <label for="login">login:</label>
              <input class="log" type="text" id="login" name="login">
              <label for="pass">password:</label>
              <input class="pass" type="password" id="password" name="password">
              <input class="btn" type="submit" value="login">
         </form>
        <p><a href="/app/cinema/mainpage">
           Login as guest
        </p></a>
        <p><a href="/app/registeruser.jsp">
           Sign up
        </p></a>
        </div>
        </div>
    </body>
</html>