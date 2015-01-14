package ru.mogom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Link;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PhotoEditServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Entity photo;
		
		if (req.getParameter("id") == null) {
			photo = new Entity("Photo");
		} else {
			photo = new Entity("Photo", Long.parseLong(req.getParameter("id")));
		}

		Date dateAdded = new Date();
		try {
			dateAdded = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(req.getParameter("dateAdded"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		Date dateTaken = new Date();
		String fileCode = req.getParameter("fileCode");
		String title = req.getParameter("title");
//		GeoPt geoPoint = new GeoPt(Float.parseFloat(req.getParameter("geoPointLat")), Float.parseFloat(req.getParameter("geoPointLong")));
		Link urlMini = new Link(req.getParameter("urlMini"));
		Link url500 = new Link(req.getParameter("url500"));
		Link url500x300 = new Link(req.getParameter("url500x300"));
		Link urlOrig = new Link(req.getParameter("urlOrig"));

		
		photo.setProperty("dateAdded", dateAdded);
//		photo.setProperty("dateTaken", dateTaken);
		photo.setProperty("fileCode", fileCode);
		photo.setProperty("title", title);
//		photo.setProperty("geoPoint", geoPoint);
		photo.setProperty("urlMini", urlMini);
		photo.setProperty("url500", url500);
		photo.setProperty("url500x300", url500x300);
		photo.setProperty("urlOrig", urlOrig);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(photo);

		resp.sendRedirect("/photolist.jsp");
	}
}