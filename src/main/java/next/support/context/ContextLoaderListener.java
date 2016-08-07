package next.support.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

//@WebListener
@Component
public class ContextLoaderListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
		
		logger.info("Completed Load ServletContext!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
