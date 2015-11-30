<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="Header.jsp">
    <jsp:param name="title" value="Login page" />
    <jsp:param value="/login.css" name="css"/>
</jsp:include>

<div class="container">

    <form class="form-signin" method="POST" action="login">
        <h2 class="form-signin-heading">Please sign in</h2>

        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" name="email" value="${email}" id="inputEmail" class="form-control"
               placeholder="Email address" required autofocus>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control"
            placeholder="Password" required>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>

        <c:if test="${not empty error}">
            <div class="alert alert-warning">
                <strong>Warning!</strong> ${error}
            </div>
        </c:if>

    </form>

</div>

<jsp:include page="/Footer.jsp"/>

</body>
</html>
