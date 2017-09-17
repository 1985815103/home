package code.core;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 类说明：
 * @author Xiaowe.Jiang
 * @version 创建时间：2017年9月15日 下午11:55:06
 */
@Configuration
@ComponentScan("code")
@PropertySource("classpath:sys.properties")
public class AppConfig {
	@Autowired Environment env;
	
	@Value("${jdbc.driver}") String driver;
	@Value("${jdbc.url}") String url;
	@Value("${jdbc.username}") String username;
	@Value("${jdbc.password}") String password;
	
	@Bean
	public DataSource getDataSource() {
		//@Value 注入失败
		driver = env.getProperty("jdbc.driver");
		url = env.getProperty("jdbc.url");
		username = env.getProperty("jdbc.username");
		password = env.getProperty("jdbc.password");
		DriverManagerDataSource ds = new DriverManagerDataSource(url, username, password);
		ds.setDriverClassName(driver);
		
		return ds;
	}
}
