<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
</head>

<body>

<h1>Photo List</h1>
<form action="/photoedit.jsp" method="get"><input type="submit" value="Add" /></form>
<hr />

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Photo").addSort("dateAdded", Query.SortDirection.DESCENDING);
    List<Entity> photos = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    if (photos.isEmpty()) {
%>
<p>There are no photos.</p>
<%
	} else {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    for (Entity photo : photos) {
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
	        
	        if (photo.getProperty("geoPoint") != null) {
	        	pageContext.setAttribute("photoGeoPoint", photo.getProperty("geoPoint").toString());
	        } else {
	        	pageContext.setAttribute("photoGeoPoint", "");
	        }
	        
	        pageContext.setAttribute("photoUrlMini", photo.getProperty("urlMini"));
	        pageContext.setAttribute("photoUrl500", photo.getProperty("url500"));
	        pageContext.setAttribute("photoUrl500x300", photo.getProperty("url500x300"));
	        pageContext.setAttribute("photoUrlOrig", photo.getProperty("urlOrig"));
	        pageContext.setAttribute("photoDescription", photo.getProperty("description"));
%>
    <table style="border:1px solid black">
    	<tr><td>id</td><td>${fn:escapeXml(photoId)}</td><td></td></tr>
    	<tr><td>dateAdded</td><td>${fn:escapeXml(photoDateAdded)}</td><td></td></tr>
    	<tr><td>dateTaken</td><td>${fn:escapeXml(photoDateTaken)}</td><td></td></tr>
    	<tr><td>fileCode</td><td>${fn:escapeXml(photoFileCode)}</td><td></td></tr>
    	<tr><td>title</td><td>${fn:escapeXml(photoTitle)}</td><td></td></tr>
		<tr><td>geoPoint (Lat, Long)</td><td>${fn:escapeXml(photoGeoPoint)}</td><td></td></tr>
    	<tr><td>urlMini</td><td>${fn:escapeXml(photoUrlMini)}</td><td></td></tr>
    	<tr><td>url500</td><td>${fn:escapeXml(photoUrl500)}</td><td></td></tr>
    	<tr><td>url500x300</td><td>${fn:escapeXml(photoUrl500x300)}</td><td></td></tr>
    	<tr><td>urlOrig</td><td>${fn:escapeXml(photoUrlOrig)}</td><td></td></tr>
    	<tr><td>description</td><td>${fn:escapeXml(photoDescription)}</td><td></td></tr>
    	<tr>
    		<td>
    			<form action="/photoedit.jsp" method="get" style="display:inline-block"><input type="hidden" name="id" value="${fn:escapeXml(photoId)}" /><input type="submit" name="action" value="Edit" /></form>
    			<form action="photoeditsrv" method="post" style="display:inline-block"><input type="hidden" name="id" value="${fn:escapeXml(photoId)}" /><input type="submit" name="action" value="Delete" /></form>
    		</td>
    		<td></td><td></td>
    	</tr>
    </table>
    <br />
<%
    	}
    }
%>

</body>
</html>