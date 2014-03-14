package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class XMLUpload {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // TODO Auto-generated method stub
		response.setContentType("application/xml;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	    if(isMultipart){
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        try{
	            List<FileItem> fields = upload.parseRequest((RequestContext) request);
	            Iterator<FileItem> it = fields.iterator();
	            while (it.hasNext()) {
	                FileItem fileItem = it.next();

	                out.println(fileItem.getString());
	          }
	        }catch (FileUploadException e) {
	            e.printStackTrace();
	        }       
	    }
	}
}
