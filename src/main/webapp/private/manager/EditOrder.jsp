
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<jsp:include page="/Header.jsp">
	<jsp:param name="title" value="Edit order # ${orderId}" />
	<jsp:param value="manager.css" name="css" />
	<jsp:param
		value="/PostFormByAjax.js, /RemoveTruckAndDriversFromOrder.js, /ChangeOrderStatus.js, /LimitCheckboxesForDriverAssignment.js"
		name="js" />
</jsp:include>

<jsp:include page="/Menu.jsp">
	<jsp:param name="homeLink" value="/manager" />
	<jsp:param name="userRoleForTitle" value="Manager" />
</jsp:include>

<!-- Edit Order -->
<div class="panel panel-primary">
	<div class="panel-heading">
		<h1>
			Edit order #<c:out value="${orderId}"/>
		</h1>
	</div>

	<div class="panel-body">

		<!-- Info -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Order info</h3>
			</div>
			<div class="panel-body">
			 <h4>Max weight<small> x1000kg</small>:<span class="label label-info">${orderRoute.maxWeightOnCourse}</span></h4>
			 <h4>Estimated time to deliver<small> hours</small>:<span class="label label-info"><fmt:formatNumber value="${orderRoute.estimatedTime}" pattern="0.0"/></span></h4>
			 
			 <h4>Assigned truck<small> truck number</small>:
			     <c:choose>
			         <c:when test="${empty order.assignedTruckFK}"><span class="label label-warning">Not assigned</span></c:when>
			         <c:otherwise><span class="label label-success">${order.assignedTruckFK.truckNumber }</span></c:otherwise>
			     </c:choose>
			 </h4>
			 
			 <h4>Assigned drivers 
			     <c:if test="${!empty order.assignedTruckFK}">
			         (${fn:length(order.assignedTruckFK.driversInTruck)} / ${order.assignedTruckFK.driverCount})
                 </c:if>:
                 
                 <c:choose>
                     <c:when test="${!empty order.assignedTruckFK && !empty order.assignedTruckFK.driversInTruck}">
                        <c:forEach items="${order.assignedTruckFK.driversInTruck}" var="driver">
                            <a href="
                                    <c:url value="showDriver">
                                        <c:param name="driverId" value="${driver.driverId}" />
                                    </c:url>">${driver.firstName} ${driver.lastName}</a><span class="comma-separator">,</span>
                        </c:forEach>
                     </c:when>
                     <c:otherwise><span class="label label-danger">Not assigned</span></c:otherwise>
                 </c:choose>
             </h4>
			
			<h4>Status: ${order.orderStatus}</h4>

            <!-- Remove drivers and truck -->
            <c:if test="${order.orderStatus == 'CREATED'}">
				<button type="button" class="btn btn-default btn-xs" onclick="removeTruckAndDriverFromOrder(${order.orderId})">
					<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
					Remove truck and drivers
				</button>
		    </c:if>

			</div>
		</div>
		<!-- /Info -->

        <!-- Buttons -->
		<div class="row margin-bottom">
			<div class="col-md-2 col-md-offset-5">

				<div class="btn-group-vertical" role="group" aria-label="...">

					<!-- Add cargo -->
					<button type="button" class="btn btn-default btn-lg <c:if test="${order.orderStatus != 'CREATED' || !empty order.assignedTruckFK}">disabled</c:if>" data-toggle="modal" data-target="#add-cargo">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span><span
							class="glyphicon glyphicon-oil" aria-hidden="true"></span> Add
						freight
					</button>

					<!-- Assign truck -->
					<button type="button"
						class="btn btn-default btn-lg <c:if test="${!empty order.assignedTruckFK || order.orderStatus != 'CREATED'}">disabled</c:if>"
						data-toggle="modal" data-target="#assign-truck">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span><span
							class="glyphicon glyphicon-bed" aria-hidden="true"></span> Assign
						truck
					</button>

					<!-- Assign driver to tuck -->
					<button type="button" class="btn btn-default btn-lg <c:if test="${empty order.assignedTruckFK || (!empty order.assignedTruckFK.driversInTruck && fn:length(order.assignedTruckFK.driversInTruck) >= order.assignedTruckFK.driverCount)}">disabled</c:if>"
					data-toggle="modal" data-target="#assign-driver">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span><span
							class="glyphicon glyphicon-user" aria-hidden="true"></span>
						Assign drivers to Truck
					</button>
					
					<!-- Status change -->
					<button type="button" class="btn btn-default btn-lg 
					    <c:choose>
	                       <c:when test="${order.orderStatus != 'READY_TO_GO' && !empty order.assignedTruckFK && (!empty order.assignedTruckFK.driversInTruck && fn:length(order.assignedTruckFK.driversInTruck) >= order.assignedTruckFK.driverCount)}"></c:when>
	                       
	                       <c:otherwise>disabled</c:otherwise>
                        </c:choose>"

						data-toggle="modal" data-target="#change-status-modal"  onclick="changeOrderStatusToReady(${order.orderId}) "><span
							class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
						Ready!
					</button>
					

				</div>

			</div>
		</div>
		<!-- /Buttons -->


        <!-- Cargo list -->
		<div class="panel panel-info">
			<div class="panel-heading">
				<h5>
					Freights in order
				</h5>
			</div>
			<div class="panel-body">
				<table class="table">
					<thead>
						<tr>
							<th>Freight ID</th>
							<th>Freight description</th>
							<th>Freight weight <small>x1000kg</small></th>
							<th>Freight status</th>
							<th>City from</th>
							<th>City to</th>
						</tr>
					</thead>

					<tbody>

						<c:forEach items="${order.orderLines}" var="freight">
							<tr>
								<td>${freight.freightId}</td>
								<td>${freight.description}</td>
								<td>${freight.weight}</td>
								<td>${freight.freightStatus}</td>
								<td>${freight.cityFromFK.name}</td>
								<td>${freight.cityToFK.name}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
		<!-- /Cargo list -->

		<!-- Print waypoints-->
		<jsp:include page="ext/WaypointsSnippet.jsp">
			<jsp:param name="routeInfo" value="${orderRoute}" />
		</jsp:include>

	</div>

</div>


<jsp:include page="/Footer.jsp" />