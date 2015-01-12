package ru.mogom;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class Kaleoins_testServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world (1)");
		resp.getWriter().println("");

		
	//	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
/*
		Entity tom = new Entity("Person", "Tom");
		Key tomKey = tom.getKey();
		datastore.put(tom);


		Entity weddingPhoto = new Entity("Photo", tomKey);
		weddingPhoto.setProperty("imageURL",
		                         "http://domain.com/some/path/to/wedding_photo.jpg");

		Entity babyPhoto = new Entity("Photo", tomKey);
		babyPhoto.setProperty("imageURL",
		                      "http://domain.com/some/path/to/baby_photo.jpg");

		Entity dancePhoto = new Entity("Photo", tomKey);
		dancePhoto.setProperty("imageURL",
		                       "http://domain.com/some/path/to/dance_photo.jpg");

		Entity campingPhoto = new Entity("Photo");
		campingPhoto.setProperty("imageURL",
		                         "http://domain.com/some/path/to/camping_photo.jpg");

		List<Entity> photoList = Arrays.asList(weddingPhoto, babyPhoto,
		                                       dancePhoto, campingPhoto);
		datastore.put(photoList);
*/
/*
		Query photoQuery = new Query("Photo");
		                        // .setAncestor(tomKey);  


		// This returns weddingPhoto, babyPhoto, and dancePhoto,
		// but not campingPhoto, because tom is not an ancestor
		List<Entity> results = datastore.prepare(photoQuery)
		                                .asList(FetchOptions.Builder.withDefaults());		
		
		for (Entity e : results) {
			String s = (String)e.getProperty("imageURL");
			resp.getWriter().println(s);
		}
		
*/
/*		Query photoQuery = new Query("Slider");
		List<Entity> results = datastore.prepare(photoQuery).asList(FetchOptions.Builder.withDefaults());		

		for (Entity e : results) {
			String s = (String)e.getProperty("name");
			resp.getWriter().println(s);
		}
*/
		
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Slider");
		PreparedQuery pq = datastore.prepare(q);
		
		//datastore.get(KeyFactory.createKey("slider", 5668600916475904))
		//resp.getWriter().println("pq.countEntities() = " + pq.countEntities(null));
	/*	
		for (Entity e : pq.asIterable()) {
			Long num = (Long)e.getProperty("num"); 
			String url = (String)e.getProperty("url"); 
			String name = (String)e.getProperty("name");
			resp.getWriter().println("num = " + num);
			resp.getWriter().println("url = " + url);
			resp.getWriter().println("name = " + name);
			resp.getWriter().println("");
		}
*/
		
		Query photoQuery = new Query("Slider");
		List<Entity> results = datastore.prepare(photoQuery).asList(FetchOptions.Builder.withLimit(5)); // withDefaults());		

		int i = 0;
		for (Entity e : results) {
			i++;
			Long num = (Long)e.getProperty("num"); 
			String url = (String)e.getProperty("url"); 
			String name = (String)e.getProperty("name");
			resp.getWriter().println("--== " + i + " ==--");
			resp.getWriter().println("num = " + num);
			resp.getWriter().println("url = " + url);
			resp.getWriter().println("name = " + name);
			resp.getWriter().println("");
		}
		
		resp.getWriter().println("The End");
	}
}
