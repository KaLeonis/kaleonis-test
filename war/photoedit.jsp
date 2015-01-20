<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.GeoPt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
</head>

<body>
<%
	if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
    	pageContext.setAttribute("photoId", "");
		pageContext.setAttribute("photoDateAdded", "");
		pageContext.setAttribute("photoDateTaken", "");
		pageContext.setAttribute("photoFileCode", "");
		pageContext.setAttribute("photoTitle", "");
		pageContext.setAttribute("photoGeoPointLat", "");
		pageContext.setAttribute("photoGeoPointLong", "");
        pageContext.setAttribute("photoUrlMini", "");
        pageContext.setAttribute("photoUrl500", "");
        pageContext.setAttribute("photoUrl500x300", "");
        pageContext.setAttribute("photoUrlOrig", "");
        pageContext.setAttribute("photoDescription", "");
	} else {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key k = KeyFactory.createKey("Photo", Long.parseLong(request.getParameter("id")));
	    Entity photo = datastore.get(k);
	    
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
    	pageContext.setAttribute("photoId", Long.toString(photo.getKey().getId()));
    	
		if (photo.getProperty("dateAdded") != null) {
    		pageContext.setAttribute("photoDateAdded", sdf.format(photo.getProperty("dateAdded")));
    	} else {
    		pageContext.setAttribute("photoDateAdded", "");
    	}
    	
     	if (photo.getProperty("dateTaken") != null) {
	    	pageContext.setAttribute("photoDateTaken", sdf.format(photo.getProperty("dateTaken")));
    	} else {
	    	pageContext.setAttribute("photoDateTaken", "");
    	}
     	
    	pageContext.setAttribute("photoFileCode", photo.getProperty("fileCode"));
		pageContext.setAttribute("photoTitle", photo.getProperty("title"));

		GeoPt geoPoint = (GeoPt) photo.getProperty("geoPoint");
		if (geoPoint != null) {
			pageContext.setAttribute("photoGeoPointLat", Float.toString(geoPoint.getLatitude()));
			pageContext.setAttribute("photoGeoPointLong", Float.toString(geoPoint.getLongitude()));
		}
		
        pageContext.setAttribute("photoUrlMini", photo.getProperty("urlMini"));
        pageContext.setAttribute("photoUrl500", photo.getProperty("url500"));
        pageContext.setAttribute("photoUrl500x300", photo.getProperty("url500x300"));
        pageContext.setAttribute("photoUrlOrig", photo.getProperty("urlOrig"));
        pageContext.setAttribute("photoDescription", photo.getProperty("description"));
}
%>

<form action="/photoeditsrv" method="post">
    <table style="border:1px solid black">
     	<tr><td>id</td><td><input type="text" name="id" value="${fn:escapeXml(photoId)}" readonly="readonly" /></td><td></td></tr>
     	<tr><td>dateAdded</td><td><input type="datetime" name="dateAdded" value="${fn:escapeXml(photoDateAdded)}" /></td><td>Format: yyyy-MM-dd HH:mm:ss</td></tr>
    	<tr><td>dateTaken</td><td><input type="datetime" name="dateTaken" value="${fn:escapeXml(photoDateTaken)}" /></td><td></td></tr>
    	<tr><td>fileCode</td><td><input type="text" name="fileCode" value="${fn:escapeXml(photoFileCode)}" /></td><td></td></tr>
    	<tr><td>title</td><td><input type="text" name="title" value="${fn:escapeXml(photoTitle)}" /></td><td></td></tr>
		<tr><td>geoPoint (Lat, Long)</td><td><input type="number" step="any" name="geoPointLat" value="${fn:escapeXml(photoGeoPointLat)}" />, <input type="number" step="any" name="geoPointLong" value="${fn:escapeXml(photoGeoPointLong)}" /></td><td></td></tr>
    	<tr><td>urlMini</td><td><input type="text" name="urlMini" value="${fn:escapeXml(photoUrlMini)}" /></td><td></td></tr>
    	<tr><td>url500</td><td><input type="text" name="url500" value="${fn:escapeXml(photoUrl500)}" /></td><td></td></tr>
    	<tr><td>url500x300</td><td><input type="text" name="url500x300" value="${fn:escapeXml(photoUrl500x300)}" /></td><td></td></tr>
    	<tr><td>urlOrig</td><td><input type="text" name="urlOrig" value="${fn:escapeXml(photoUrlOrig)}" /></td><td></td></tr>
    	<tr><td>description</td><td><textarea name="description" rows="3" cols="60" value="${fn:escapeXml(photoDiscription)}"></textarea></td><td></td></tr>

    	<tr><td><% if (pageContext.getAttribute("photoId") == null || pageContext.getAttribute("photoId").equals("")) { %> <input type="submit" name="submit" value="Add" /> <% } else { %> <input type="submit" name="submit" value="Save" /> <% } %></td><td></td><td></td></tr>
    </table>
</form>

</body>
</html>