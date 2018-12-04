package com.doc.central.app.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.doc.central.app.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
@EnableJpaRepositories(Constants.REPOSITORY_PACKAGE_NAME)
@EnableSwagger2
@WebServlet(name = "default", urlPatterns = {Constants.SWAGGER_URL_PATTERN})
public class OneDocDataBaseConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	JpaVendorAdapter jpaVendorAdapter;

	@Value("${init-db:false}")
	private String initDatabase;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("ap.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("ap.datasource.url"));
		dataSource.setUsername(env.getProperty("ap.datasource.username"));
		dataSource.setPassword(env.getProperty("ap.datasource.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan(Constants.MODEL_PACKAGE_NAME);

		Properties jpaProperties = new Properties();

		// Configures the used database dialect. This allows Hibernate to create
		// SQL
		// that is optimized for the used database.
		jpaProperties.put(Constants.HIBERNATE_DIALECT, env.getRequiredProperty(Constants.HIBERNATE_DIALECT));

		// Specifies the action that is invoked to the database when the
		// Hibernate
		// SessionFactory is created or closed.
		jpaProperties.put(Constants.HIBERNATE_HBM2_DDL, env.getRequiredProperty(Constants.HIBERNATE_HBM2_DDL));

		// Configures the naming strategy that is used when Hibernate creates
		// new database objects and schema elements
		jpaProperties.put(Constants.HIBERNATE_EJB_NAMING_STRATEGY,
				env.getRequiredProperty(Constants.HIBERNATE_EJB_NAMING_STRATEGY));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put(Constants.HIBERNATE_SHOW_SQL, env.getRequiredProperty(Constants.HIBERNATE_SHOW_SQL));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put(Constants.HIBERNATE_FORMAT_SQL, env.getRequiredProperty(Constants.HIBERNATE_FORMAT_SQL));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;

	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource(), env).getObject());
		return transactionManager;
	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(Constants.CONTROLLER_PACKAGE_NAME)).paths(PathSelectors.any())
				.build().apiInfo(apiInformation());
	}

	private ApiInfo apiInformation() {

		return new ApiInfoBuilder().title(Constants.SWAGGER_API_INFO_TITLE)
				.description(Constants.SWAGGER_API_INFO_DESCRIPTION).version(Constants.SWAGGER_API_INFO_VERSION).build();

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
		registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui",
				"/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/documentation/swagger-resources/configuration/security",
				"/swagger-resources/configuration/security");
		registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
	}

	 
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jacksonMessageConverter());
		super.configureMessageConverters(converters);
	}
	public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
		MappingJackson2HttpMessageConverter messageConverter = new  MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();	
		mapper.registerModule(new Hibernate5Module());
		messageConverter.setObjectMapper(mapper);
		return messageConverter;

	}
}
