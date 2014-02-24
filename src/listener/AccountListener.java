package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import control.QuiznessAccountManager;


/**
 * Application Lifecycle Listener implementation class myListener
 *
 */
@WebListener
public class AccountListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent arg0) {
    	QuiznessAccountManager manager = new QuiznessAccountManager();//creates an instance of an AccountManager
    	ServletContext context = arg0.getServletContext();
    	context.setAttribute("manager", manager);//grabable attribute
    	
    }


    public void contextDestroyed(ServletContextEvent arg0) {
        //nothing to clean up
    }
	
}
