package control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xerces.internal.parsers.XMLParser;

/**
 * Servlet implementation class ParseQuizUpload
 */
@WebServlet("/ParseQuizUpload")
@MultipartConfig
public class ParseQuizUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParseQuizUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Part filePart = request.getPart("file1"); 
		
		String filename = null;
		String header = filePart.getHeader("content-disposition");
		if(header != null){
			String[] parts = header.split(";"); 
			for(int i = 0 ; i < parts.length; i++){
				if(parts[i].trim().startsWith("filename")){
					String result = parts[i].substring(parts[i].indexOf('=')+1); 
					result = result.trim().replace("\"", "");
					filename = result; 
				}
			}
		}
		
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			
			File file = new File ("C:/Users/Tony/Desktop/bunny.xml"); 
			boolean exists = file.exists();
			boolean isfile = file.isFile();
			String test = file.toString(); 
			
			ServletContext context = getServletContext(); 
			InputStream input = context.getResourceAsStream(filename);
			
			FileInputStream fis = new FileInputStream(filename);
			int b = fis.read();
			Document doc = db.parse(fis);
			doc.setDocumentURI(filename);
			
			doc.getDocumentElement().normalize();
			
			NodeList element = doc.getElementsByTagName("question");
			System.out.println("est");
			
		} catch (Exception e) { }
	}
}
	

