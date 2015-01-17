package com.myproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import java.util.Properties;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import com.myproject.DaoInterfaceImpl;
import com.myproject.DaoInterface;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.http.MediaType;
import com.myproject.resolver.JsonViewResolver;
import org.springframework.web.servlet.ViewResolver;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@EnableWebMvc
@Configuration
@ComponentScan("com.myproject")
@EnableTransactionManagement
@Import({SecuritySettings.class})
public class ApplicationContext extends WebMvcConfigurerAdapter{

	@Override
 	public void addResourceHandlers(final ResourceHandlerRegistry registry){
 		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
 	}


	@Bean(name="viewResolver")
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;	
	}

	@Bean(name="dataSource")	
	@Resource(name="jdbc/cms")
	public DataSource dataSourceLookup(){
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/cms");
		return dataSource;
	}
	// @Bean(name = "dataSource")
	// public DataSource getDataSource() {
	//     BasicDataSource dataSource = new BasicDataSource();
	//     dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	//     dataSource.setUrl("jdbc:mysql://localhost:3306/cms");
	//     dataSource.setUsername("root");
	//     dataSource.setPassword("admin");
	 
 //    	return dataSource;
	// }

	private Properties getHibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.show_sql","true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}

	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource){
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.scanPackages("com.myproject");
		sessionBuilder.addProperties(getHibernateProperties());
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}

	@Autowired
	@Bean(name="daoInterfaceImpl")
	public DaoInterface getDaoInterfaceImpl(SessionFactory sessionFactory){
		return new DaoInterfaceImpl(sessionFactory);
	}


	// @Override
	// public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
	// 	configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
	// }

	// @Bean
	// public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
	// 	ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	// 	resolver.setContentNegotiationManager(manager);

	// 	List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
	// 	resolvers.add(getViewResolver());
	// 	resolvers.add(jsonViewResolver());

	// 	resolver.setViewResolvers(resolvers);
	// 	return resolver;
	// }	

	// @Bean
	// public ViewResolver jsonViewResolver(){
	// 	return new JsonViewResolver();
	// }


}