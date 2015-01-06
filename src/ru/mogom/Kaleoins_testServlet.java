package ru.mogom;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Kaleoins_testServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println("Hello, Roman");
	}
}
