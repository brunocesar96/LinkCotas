package br.gov.mec.configuracao;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.annotation.ServletSecurity;


public class InicializadorWeb implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(ConfiguracaoWeb.class);

        //adicionando listerner ao container
        container.addListener(new ContextLoaderListener(dispatcherContext));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);
        //
        //container.addFilter("My filter", Filtro.class).addMappingForServletNames(null,false,DISPATCHER_SERVLET_NAME);

         //Filtro do Spring Security
//        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("springSecurityFilterChain");
//        FilterRegistration.Dynamic filtroSpringSecurity = container.addFilter("springSecurityFilterChain", delegatingFilterProxy);
//        filtroSpringSecurity.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
//
        // Force HTTPS, and don't specify any roles for this constraint
        HttpConstraintElement forceHttpsConstraint = new HttpConstraintElement(ServletSecurity.TransportGuarantee.NONE); // Para voltar ao que estava por .NONE
        ServletSecurityElement securityElement = new ServletSecurityElement(forceHttpsConstraint);
//
//        // Add the security element to the servlet
//        dispatcher.setServletSecurity(securityElement);
    }
}