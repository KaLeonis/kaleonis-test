package ru.mogom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SliderServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    resp.setContentType("application/json; charset=UTF-8");

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("Slider").addSort("num", Query.SortDirection.ASCENDING);
	    List<Entity> slides = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    
	    if (slides.isEmpty()) {
	        resp.getWriter().print("[{}]");
		} else {
			StringBuilder sb = new StringBuilder();
		    sb.append("[\n");
			
			for (Entity slide : slides) {
		    	if (sb.length() > 5) {
			    	sb.append(",\n");
		    	}
		    	
		    	sb.append("  {\n    \"num\":\"");
		    	sb.append(slide.getProperty("num"));
		    	sb.append("\",\n    \"name\":\"");
		    	sb.append(slide.getProperty("name"));
		    	sb.append("\",\n    \"url\":\"");
		    	sb.append(slide.getProperty("url"));
		    	sb.append("\"\n  }");
		    }
			
			sb.append("\n]");
			resp.getWriter().print(sb.toString());
			resp.getWriter().flush();  
		}
	}
}