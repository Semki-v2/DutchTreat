import org.eclipse.jetty.server.handler.AbstractHandler;

public class TempServer extends AbstractHandler {
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>Hello World</h1>");
	}

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new HelloWorld());

		server.start();
		server.join();
	}
}
