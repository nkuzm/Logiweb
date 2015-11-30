<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/Header.jsp">
	<jsp:param name="title" value="Drivers List" />
	<jsp:param value="manager.css" name="css" />
	<jsp:param value="RemoveDriver.js" name="js" />
</jsp:include>

<jsp:include page="/Menu.jsp">
	<jsp:param name="homeLink" value="/manager" />
	<jsp:param name="userRoleForTitle" value="Manager" />
</jsp:include>

<jsp:include page="ext/DriverListSnippet.jsp">
    <jsp:param name="privelege" value="edit" />
</jsp:include>

<jsp:include page="/Footer.jsp" />