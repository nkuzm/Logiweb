<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Add driver</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div id="wrapper">
    <header>
        <div class="content-wrap">
        </div>
    </header>
    <nav>
        <ul>
            <li><a href="/logiweb">Home page</a></li>
            <li>
                <a href="http://google.com">Google</a>
                <span class="inner-menu">
                   <a href="http://google.com">Gmail</a>
                </span>
            </li>
        </ul>
    </nav>
    <section id="content" class="clearfix">
        <section id="page-content">
            <div class="content-wrap">
                <h1>Add driver (example form)</h1>
                <form name="formName" action="servlet/AddDriver" method="post">
                    <div class="form-element">
                        <input type="number" placeholder="Personal number" required="" id="personalnumber"
                               name="personalnumber"/>
                    </div>
                    <div class="form-element">
                        <input type="text" placeholder="First name" required="" id="firstname" name="firstname"/>
                    </div>
                    <div class="form-element">
                        <input type="text" placeholder="Last name" required="" id="lastname" name="lastname"/>
                    </div>
                    <div class="form-element">
                        <input type="submit" name="add" id="Add" value="Add driver"/>
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
