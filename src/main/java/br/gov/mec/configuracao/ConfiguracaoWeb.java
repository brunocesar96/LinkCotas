package br.gov.mec.configuracao;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = {"br.gov.mec"})
public class ConfiguracaoWeb extends WebMvcConfigurerAdapter {


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"br.gov.mec"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "datasource")
    public static DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        //ds.setUrl("jdbc:postgresql://10.0.3.6:5432/biblioteca_digital");
        ds.setUrl("jdbc:postgresql://10.0.11.38:5432/link-cotas");
        ds.setUsername("postgres");
        ds.setPassword("labtime2017");

        return ds;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sessionFactory);
        return tx;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**/*.html").addResourceLocations("/web/");
//        registry.addResourceHandler("/web/**").addResourceLocations("/web/");
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/WEB-INF/pages/home.jsp");
//        registry.addViewController("/upload").setViewName("/WEB-INF/pages/upload.jsp");
//        registry.addViewController("/sucess").setViewName("/WEB-INF/pages/sucess.jsp");
//    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
//
//        Gson gson = buildGson();
//
//        gsonHttpMessageConverter.setGson(gson);
//
//        converters.add(gsonHttpMessageConverter);
//    }
//
//    protected Gson buildGson() {
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//        return gson;
//    }


//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setDefaultEncoding("UTF-8");
//        //messageSource.setBasenames("sucessos", "erros");
//        return messageSource;
//    }
//
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver getCommonsMultipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        /*multipartResolver.setMaxUploadSize(20971520); // 20MB
//        multipartResolver.setMaxInMemorySize(1048576);    // 1MB*/
//        return multipartResolver;
//    }

    //Conversor base64 para byte[] e byte[] para base64
//    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
//        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            return DatatypeConverter.parseBase64Binary(json.getAsString());
//        }
//
//        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
//            return new JsonPrimitive(DatatypeConverter.printBase64Binary(src));
//        }
//    }
}

