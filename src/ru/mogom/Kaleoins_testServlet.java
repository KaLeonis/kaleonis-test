package ru.mogom;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class Kaleoins_testServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println("Hello, Roman");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("slider");
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity e : pq.asIterable()) {
			Long num = (Long)e.getProperty("num"); 
			String url = (String)e.getProperty("url"); 
			String name = (String)e.getProperty("name");
			resp.getWriter().println(num);
			resp.getWriter().println(url);
			resp.getWriter().println(name);
			resp.getWriter().println("");
		}
	}
}
