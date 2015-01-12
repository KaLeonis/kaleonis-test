<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>

<html>
<head>
</head>

<body>

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Slider").addSort("num", Query.SortDirection.ASCENDING);
    List<Entity> slides = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    if (slides.isEmpty()) {
%>
<p>Slider has no slides.</p>
<%
	} else {
%>
<p>Slides in Slider:</p>
<%
	    for (Entity slide : slides) {
	        pageContext.setAttribute("slide_num", slide.getProperty("num"));
	        pageContext.setAttribute("slide_name", slide.getProperty("name"));
	        pageContext.setAttribute("slide_url", slide.getProperty("url"));
%>
<p>${fn:escapeXml(slide_num)}<br />
${fn:escapeXml(slide_name)}<br />
${fn:escapeXml(slide_url)}</p>
<%
    	}
    }
%>

<form action="/slidereditor" method="post">
    <div><input type="number" name="num" /></div>
    <div><input type="text" name="name" /></div>
    <div><input type="text" name="url" /></div>
    <div><input type="submit" value="Add Slide"/></div>
</form>

</body>
</html>