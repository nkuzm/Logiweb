<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Login page</title>
  <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div id="wrapper">
  <header>
    <div class="content-wrap">
    </div>
  </header>
  <section id="content" class="clearfix">
    <section id="page-content">
      <div class="content-wrap">
        <h1>Please sign in</h1>
        <form name="formName" action="servlet/AddDriver" method="post">
          <div class="form-element">
            <input type="email" placeholder="Email" required="" id="email"
                   name="email"/>
          </div>
          <div class="form-element">
            <input type="password" placeholder="Password" required="" id="password" name="password"/>
          </div>
          <div class="form-element">
            <input type="submit" name="sign in" id="sign in" value="Sign in"/>
          </div>
        </form>
      </div>
    </section>
    <aside>
      <div class="content-wrap">

      </div>
    </aside>
  </section>
  <div id="empty-div"></div>
</div>
<div id="overlay"></div>
<div class="modal-dialog"></div>
<footer></footer>
</body>
</html>
