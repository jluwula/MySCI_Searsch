/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2023-12-05 07:24:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class success_005fregister_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <title>恭喜，注册成功！</title>\r\n");
      out.write("    <meta http-equiv=\"refresh\" content=\"5;url=./loginpage.html\">\r\n");
      out.write("    <style>\r\n");
      out.write("        .holder{\r\n");
      out.write("            width:1000px;\r\n");
      out.write("            height:650px;\r\n");
      out.write("            margin-left:auto;\r\n");
      out.write("            margin-right:auto;\r\n");
      out.write("        }\r\n");
      out.write("        .pic-holder{\r\n");
      out.write("            width:500px;\r\n");
      out.write("            height:500px;\r\n");
      out.write("            margin-left:auto;\r\n");
      out.write("            margin-right:auto;\r\n");
      out.write("        }\r\n");
      out.write("        img{\r\n");
      out.write("            width:500px;\r\n");
      out.write("            height:500px;\r\n");
      out.write("        }\r\n");
      out.write("        .text{\r\n");
      out.write("            margin-left:180px;\r\n");
      out.write("            font-size:20px;\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("    <script src=\"./js/jquery-3.6.0.min.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div class=\"holder\">\r\n");
      out.write("        <div class=\"text\">\r\n");
      out.write("            恭喜，注册成功！5秒钟之后跳转回登录页！\r\n");
      out.write("            您的ID是<p id=\"uid\">");
      out.print( (String)request.getAttribute("uid") );
      out.write("</p>\r\n");
      out.write("            如果失效，请<a href=\"./loginpage.html\">点击此处</a>!\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"pic-holder\">\r\n");
      out.write("            <img src=\"./img_src/success.jpg\">\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
