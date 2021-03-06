package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.xmpp.workgroup.Agent;
import org.jivesoftware.xmpp.workgroup.*;
import java.util.LinkedList;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.dispatcher.DispatcherInfo;
import org.jivesoftware.util.ParamUtils;

public final class workgroup_002dqueue_002dmanage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"workgroup-error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write('\n');
      out.write('\n');

    // Get parameters //
    String wgID = ParamUtils.getParameter(request, "wgID");
    long queueID = ParamUtils.getLongParameter(request, "qID", -1L);
    String name = ParamUtils.getParameter(request, "name");
    String description = ParamUtils.getParameter(request, "description");
    // String agentSelector = ParamUtils.getParameter(request,"agentSelector");
    String success = ParamUtils.getParameter(request, "success");


    boolean update = request.getParameter("update") != null;
    int overflow = ParamUtils.getIntParameter(request, "overflow", 1);
    long overflowQID = ParamUtils.getLongParameter(request, "overflowQID", -1L);


    WorkgroupManager wgManager = WorkgroupManager.getInstance();
    WorkgroupAdminManager adminManager = new WorkgroupAdminManager();

    // Load the workgroup
    Workgroup workgroup = wgManager.getWorkgroup(new JID(wgID));

    long offerTimeout = ParamUtils.getLongParameter(request, "offerTimeout", -1);
    if (offerTimeout == -1) {
        offerTimeout = workgroup.getOfferTimeout() / 1000;
    }


    long requestTimeout = ParamUtils.getLongParameter(request, "requestTimeout", -1);
    if (requestTimeout == -1) {
        requestTimeout = workgroup.getRequestTimeout() / 1000;
    }

    AgentManager aManager = wgManager.getAgentManager();
    // Load the request queue:
    RequestQueue queue = workgroup.getRequestQueue(queueID);
    //AgentSelector newSelector = null;

      out.write("\n\n\n\n\n");

    RequestQueue.OverflowType overflowType = null;
    switch (overflow) {
        case 1:
            overflowType = RequestQueue.OverflowType.OVERFLOW_NONE;
            break;
        case 2:
            overflowType = RequestQueue.OverflowType.OVERFLOW_RANDOM;
            break;
        case 3:
            overflowType = RequestQueue.OverflowType.OVERFLOW_BACKUP;
            break;
    }



    Map errors = new HashMap();
    if (update) {
        if (name == null) {
            errors.put("name","");
        }

        else {

        }
        if (errors.size() == 0) {
            queue.setName(name);
            queue.setDescription(description);
            // set timeouts
            DispatcherInfo infos = queue.getDispatcher().getDispatcherInfo();

            infos.setOfferTimeout(offerTimeout*1000L);
            infos.setRequestTimeout(requestTimeout*1000L);
            queue.getDispatcher().setDispatcherInfo(infos);
        }


            queue.setOverflowType(overflowType);
            if (overflowType == RequestQueue.OverflowType.OVERFLOW_BACKUP && overflowQID != -1L) {
                queue.setBackupQueue(workgroup.getRequestQueue(overflowQID));
            }

            response.sendRedirect("workgroup-queue-manage.jsp?success=true&wgID=" + wgID + "&qID=" + queue.getID());
            return;
        }

    RequestQueue backupQueue = null;

    if (errors.size() == 0) {
        name = queue.getName();
        description = queue.getDescription();
        DispatcherInfo dispatcherInfo = queue.getDispatcher().getDispatcherInfo();
        offerTimeout = dispatcherInfo.getOfferTimeout();
        requestTimeout = dispatcherInfo.getRequestTimeout();

        overflowType = queue.getOverflowType();
        if (overflowType == RequestQueue.OverflowType.OVERFLOW_BACKUP) {
            backupQueue = queue.getBackupQueue();
        }
    }

      out.write("\n\n\n<html>\n    <head>\n        <title>Edit Queue Settings - ");
      out.print(queue.getName());
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-queues\"/>\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"edit_queue_properties.html\"/>-->\n\n        <script language=\"JavaScript\" type=\"text/javascript\">\n        function openWin(el) {\n            var win = window.open('user-browser.jsp?formName=f&elName=agents','newWin','width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n        }\n\n        function openAgentGroupWindow(el){\n             var agentwin = window.open('agent-group-browser.jsp?formName=f&elName=agentGroups','newWin','width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n\n        }\n        </script>\n    </head>\n    <body>\n\n<p>\nThe queue name and description helps\nadministrators and agents with identifying a particular request queue.\n</p>\n\n<p>\n<a href=\"workgroup-queue-agents.jsp?wgID=");
      out.print( wgID );
      out.write("&qID=");
      out.print( queueID);
      out.write("\">Edit Agents and Groups</a>\n    &nbsp;\n<a href=\"workgroup-queues.jsp?wgID=");
      out.print( wgID );
      out.write("\">View Queues</a>\n</p>\n\n\n");
 if(success != null && errors.size() == 0) { 
      out.write("\n<div class=\"success\">\n   Workgroup Queue has been updated.\n</div>\n<br/>\n");
 } 
      out.write('\n');
  if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"error\">\n    Please fix the errors below.\n    </div>\n\n");
  } 
      out.write("\n\n<form action=\"workgroup-queue-manage.jsp\" method=\"post\" name=\"f\">\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\n<input type=\"hidden\" name=\"qID\" value=\"");
      out.print( queueID );
      out.write("\">\n    <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n        <tr>\n            <th colspan=\"3\">Queue Settings</th>\n        </tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Name: *</b>\n        ");
  if (errors.get("name") != null) { 
      out.write("\n            <span class=\"jive-error-text\">\n            Please enter a name.\n            </span>\n\n        ");
  } 
      out.write("\n         <br/>\n        <span class=\"jive-description\">\n        Please specify the name of the queue. The queue name should be created based on the Queue Routing rules.\n        </span>\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"name\" size=\"40\"\n         value=\"");
      out.print( ((name != null) ? name : "") );
      out.write("\">\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Description:</b>\n        <br/>\n        <span class=\"jive-description\">Specify a description for this queue to easily identify it.</span>\n    </td>\n    <td class=\"c2\">\n        <textarea name=\"description\" cols=\"40\" rows=\"2\" wrap=\"virtual\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea>\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Request Timeout: (sec) *</b>\n        ");
  if (errors.get("requestTimeout") != null) { 
      out.write("\n            &nbsp;\n            <span class=\"jive-error-text\">\n            Please enter a valid timeout value.\n            </span>\n\n        ");
  } 
      out.write("\n        <br/>\n        <span class=\"jive-description\">The total time before an individual request will timeout if no agents accept it.</span>\n    </td>\n\n                <td width=\"99%\">\n                    <input type=\"text\" name=\"requestTimeout\" value=\"");
      out.print( requestTimeout/1000L );
      out.write("\"\n                     size=\"5\" maxlength=\"10\"\n                    >\n                </td>\n\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Offer Timeout: (sec) *</b>\n        ");
  if (errors.get("offerTimeout") != null) { 
      out.write("\n            &nbsp;\n            <span class=\"jive-error-text\">\n            Please enter a valid timeout value.\n            </span>\n\n        ");
  } 
      out.write("\n        <br/>\n        <span class=\"jive-description\">The time each agent will be giving to accept a chat request.</span>\n    </td>\n    <td class=\"c2\">\n      <input type=\"text\" name=\"offerTimeout\" value=\"");
      out.print( offerTimeout/1000 );
      out.write("\" size=\"5\" maxlength=\"10\">\n    </td>\n</tr>\n\n\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Queue Overflow Policy:</b>\n        <br/>\n        <span class=\"jive-description\">Specify failover for this queue.</span>\n    </td>\n    <td class=\"c2\">\n        <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\" style=\"border-width:0px !important;\">\n        <tr>\n            <td>\n                <input type=\"radio\" name=\"overflow\" value=\"1\" id=\"over01\"\n                 ");
      out.print( ((overflowType==RequestQueue.OverflowType.OVERFLOW_NONE) ? "checked" : "") );
      out.write(">\n            </td>\n            <td>\n                <label for=\"over01\">Never overflow requests</label>\n            </td>\n        </tr>\n        <tr>\n            <td>\n                <input type=\"radio\" name=\"overflow\" value=\"2\" id=\"over02\"\n                 ");
      out.print( ((overflowType==RequestQueue.OverflowType.OVERFLOW_RANDOM) ? "checked" : "") );
      out.write(">\n            </td>\n            <td>\n                <label for=\"over02\">Overflow requests to a random queue</label>\n            </td>\n        </tr>\n\n        ");
  // Get a list of all other queues in this workgroup
            List queues = new LinkedList();
            for(RequestQueue requestQueue : workgroup.getRequestQueues()){
                if (requestQueue.getID() != queueID) {
                    queues.add(requestQueue);
                }
            }
        
      out.write("\n\n        <tr>\n            <td>\n                <input type=\"radio\" name=\"overflow\" value=\"3\" id=\"over03\"\n                 ");
      out.print( ((overflowType==RequestQueue.OverflowType.OVERFLOW_BACKUP) ? "checked" : "") );
      out.write("\n                 ");
      out.print( ((queues.size()==0) ? "disabled" : "") );
      out.write(">\n            </td>\n            <td>\n                <label for=\"over03\">Overflow requests to a specified queue:</label>\n            </td>\n        </tr>\n        <tr>\n            <td>&nbsp;</td>\n            <td>\n                ");
  if (queues.size() == 0) { 
      out.write("\n\n                    No other queues -\n                    <a href=\"workgroup-queue-create.jsp?wgID=");
      out.print( wgID );
      out.write("\">create one</a>.\n\n                ");
  } else { 
      out.write("\n\n                <select size=\"1\" name=\"overflowQID\" onchange=\"this.form.overflow[2].checked=true;\">\n\n                    <option value=\"\"></option>\n\n                    ");
  for (int i=0; i<queues.size(); i++) {
                            RequestQueue q = (RequestQueue)queues.get(i);
                    
      out.write("\n                        <option value=\"");
      out.print( q.getID() );
      out.write("\"\n                        ");
  if (backupQueue != null) { 
      out.write("\n\n                            ");
      out.print( ((backupQueue.getID()==q.getID()) ? "selected" : "") );
      out.write("\n\n                        ");
  } 
      out.write("\n                         >");
      out.print( q.getName() );
      out.write("</option>\n\n                    ");
  } 
      out.write("\n\n                </select>\n\n                ");
  } 
      out.write("\n            </td>\n        </tr>\n        </table>\n    </td>\n</tr>\n</table>\n\n<br>\n\n* Required field.\n\n<br><br>\n\n<input type=\"submit\" name=\"update\" value=\"Save Settings\">\n\n</form>\n\n<script language=\"JavaScript\" type=\"text/javascript\">\ndocument.f.name.focus();\n</script>\n\n</body>\n</html>");
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
