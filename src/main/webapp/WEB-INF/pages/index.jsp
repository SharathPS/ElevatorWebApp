
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/custom.css" />
<title>Elevator Status</title>
<div class="center-block" style="width: 150px">
	<div id="elevatorStatus" class="btn-group-lg">
		<button type="button" value="1" onclick="loadXMLDoc(this.value)"
			class="btn btn-primary">Elevator 1 Status</button>
		<button type="button" value="2" onclick="loadXMLDoc(this.value)"
			class="btn btn-primary">Elevator 2 Status</button>
	</div>

	<div id="elevatorStatusDetails" class="bg-info"></div>
</div>

<script>
	function loadXMLDoc(val) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var arr = JSON.parse(this.responseText);
				if (arr.up == true) {
					document.getElementById("elevatorStatusDetails").innerHTML = "Lift"
							+ val + " is at "+ arr.floorNo + " floor";
				} else if (arr.down == true) {
					document.getElementById("elevatorStatusDetails").innerHTML = "Lift"
							+ val + " is at " + arr.floorNo + " floor";
				}
			}
		};
		url = val + "/status";
		xhttp.open("GET", url, true);
		xhttp.send();
	}
</script>
</body>
</html>