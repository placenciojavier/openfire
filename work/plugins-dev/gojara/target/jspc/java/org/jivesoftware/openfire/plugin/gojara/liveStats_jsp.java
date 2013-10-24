package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.permissions.PermissionManager;
import org.jivesoftware.openfire.plugin.gojara.database.DatabaseManager;
import org.dom4j.tree.DefaultElement;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.session.ComponentSession;
import java.util.Collection;
import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.util.JiveGlobals;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;
import org.jivesoftware.util.ParamUtils;

public final class liveStats_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

	// webManager.init(request, response, session, application, out);
	boolean componentSet = request.getParameter("component") != null;
	String component = "";
	if (componentSet) {
		component = request.getParameter("component");
	}

	Date currentDate = new Date(System.currentTimeMillis());
	String now = currentDate.getHours() + ":" + currentDate.getMinutes() + "." + currentDate.getSeconds()
			+ "   " + currentDate.getDate() + "." + currentDate.getMonth() + "." + currentDate.getYear();

      out.write("\n\n<html>\n<head>\n<title>Live logs ");
      out.print(componentSet ? "for " + component : "");
      out.write("</title>\n<meta name=\"decorator\" content=\"none\" />\n\n\n<link href=\"./css/liveStats.css\" rel=\"stylesheet\" type=\"text/css\">\n<script src=\"./js/http.js\" type=\"text/javascript\"></script>\n<script src=\"./js/jquery.js\" type=\"text/javascript\"></script>\n<script src=\"./js/liveStats.js\" type=\"text/javascript\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"./js/jquery.flot.js\"></script>\n</head>\n\n<body>\n\t<div class=\"div-main\">\n\t\t<div class=\"header\">\n\t\t\tLive statistics for\n\t\t\t");
      out.print(componentSet ? component : "NOT SET");
      out.write("</div>\n\t\t<div class=\"graph\">Here should appear your stats</div>\n\n\t\t<table id=\"logTable\">\n\t\t\t<thead>\n\t\t\t\t<tr>\n\t\t\t\t\t<th>Date</th>\n\t\t\t\t\t<th>Type</th>\n\t\t\t\t\t<th>From</th>\n\t\t\t\t\t<th>To</th>\n\t\t\t\t</tr>\n\t\t\t</thead>\n\t\t\t<tbody class=\"tableBegin\">\n\t\t\t</tbody>\n\t\t\t<tfoot>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\">Live logging since ");
      out.print(now);
      out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td colspan=\"2\"><form id=\"limitForm\">\n\t\t\t\t\t\t\t<input type=\"text\" id=\"tableLimit\">\n\t\t\t\t\t\t</form></td>\n\t\t\t\t</tr>\n\t\t\t</tfoot>\n\t\t</table>\n\t</div>\n\t<span id=\"logSince\" style=\"visibility: hidden;\">");
      out.print(System.currentTimeMillis());
      out.write("</span>\n\n</body>\n\n\n\n\n</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
