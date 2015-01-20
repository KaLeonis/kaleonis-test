package ru.mogom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Link;

import java.io.IOException;
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
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		if (req.getParameter("id").isEmpty()) {
			photo = new Entity("Photo");
		} else {
			photo = new Entity("Photo", Long.parseLong(req.getParameter("id")));
		}

		if (req.getParameter("action") != null && !req.getParameter("action").isEmpty() && req.getParameter("action").equalsIgnoreCase("delete")) {
			datastore.delete(photo.getKey());
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date dateAdded = null;
			if (req.getParameter("dateAdded").isEmpty()) {
				dateAdded = new Date();
			} else {
				try {
					dateAdded = sdf.parse(req.getParameter("dateAdded"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			Date dateTaken = null;
			if (!req.getParameter("dateTaken").isEmpty()) {
				try {
					dateTaken = sdf.parse(req.getParameter("dateTaken"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			String fileCode = req.getParameter("fileCode");
			String title = req.getParameter("title");
	
			GeoPt geoPoint = null;
			if (!req.getParameter("geoPointLat").isEmpty() && !req.getParameter("geoPointLong").isEmpty()) {
				geoPoint = new GeoPt(Float.parseFloat(req.getParameter("geoPointLat")), Float.parseFloat(req.getParameter("geoPointLong")));
			}
			
			Link urlMini = new Link(req.getParameter("urlMini"));
			Link url500 = new Link(req.getParameter("url500"));
			Link url500x300 = new Link(req.getParameter("url500x300"));
			Link urlOrig = new Link(req.getParameter("urlOrig"));
			String description = req.getParameter("description");
			
			photo.setProperty("dateAdded", dateAdded);
			photo.setProperty("dateTaken", dateTaken);
			photo.setProperty("fileCode", fileCode);
			photo.setProperty("title", title);
			photo.setProperty("geoPoint", geoPoint);
			photo.setProperty("urlMini", urlMini);
			photo.setProperty("url500", url500);
			photo.setProperty("url500x300", url500x300);
			photo.setProperty("urlOrig", urlOrig);
			photo.setProperty("description", description);
	
			datastore.put(photo);
		}
		
		resp.sendRedirect("/photolist.jsp");
	}
}