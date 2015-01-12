package ru.mogom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SliderEditorServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//    UserService userService = UserServiceFactory.getUserService();
//    User user = userService.getCurrentUser();

    String guestbookName = req.getParameter("guestbookName");
    Key guestbookKey = KeyFactory.createKey("Slider", guestbookName);
    Integer num = Integer.parseInt(req.getParameter("num"));
    String name = req.getParameter("name");
    String url = req.getParameter("url");
    Entity slide = new Entity("Slider", guestbookKey);
    slide.setProperty("num", num);
    slide.setProperty("name", name);
    slide.setProperty("url", url);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(slide);

    resp.sendRedirect("/slidereditor.jsp?guestbookName=" + guestbookName);
  }
}