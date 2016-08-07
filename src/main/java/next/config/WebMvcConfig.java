package next.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import core.web.argumentresolver.LoginUserHandlerMethodArgumentResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "next.controller", "next.dao", "next.service", "next.support.context" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final int CACHE_PERIOD = 31556926; // one year
    
    private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE";
	private static final String DB_USERNAME = "sa";
	private static final String DB_PW = "";
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource bean = new BasicDataSource();
		bean.setDriverClassName(DB_DRIVER);
		bean.setUrl(DB_URL);
		bean.setUsername(DB_USERNAME);
		bean.setPassword(DB_PW);
		return bean;
	}
	
	@Bean
	public Connection connection() {
		try {
			return dataSource().getConnection();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        return bean;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
    	return new JdbcTemplate(dataSource());
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/static_resources/")
                .setCachePeriod(CACHE_PERIOD);
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Serving static files using the Servlet container's default Servlet.
        configurer.enable();
    }    
}
