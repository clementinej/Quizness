package listener;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import control.QuiznessAccountManager;
import model.*;

/**
 * Application Lifecycle Listener implementation class myListener
 *
 */
@WebListener
public class AccountListener implements ServletContextListener {

	/*
	 * Creates a new account manager when ServletConetEvent initialized
	 */
    public void contextInitialized(ServletContextEvent arg0) {
    	QuiznessAccountManager manager = null;
		try {
			manager = new QuiznessAccountManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ServletContext context = arg0.getServletContext();
    	context.setAttribute("manager", manager); 	
    }

    /*
     * close connection(non-Javadoc)
     */
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			ServerConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
