package com.galaxy.ggolf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.ggolf.domain.User;
import com.galaxy.ggolf.manager.SessionManager;

 
public class SecurityInterceptor extends AbstractPhaseInterceptor<Message> {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private SessionManager sessionManager;
	
    public SecurityInterceptor() {
        super(Phase.RECEIVE);
    }
    
    public void setSessionManager(SessionManager sessionManager){
    	this.sessionManager = sessionManager;
    }
    
    public void handleMessage(Message message) {
    	
    	logger.debug("Interceptor start");
    	
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        
        boolean authenticated = true;
        
        if(null!=request){
        	
        	if(request.getHeader("auth")!=null){
        		User user = sessionManager.getUser(request.getHeader("auth"));
            	logger.info("Current user is {}", user);
        	}
        }
        
        if(!authenticated){
	        Response response = Response.ok("Error").status(Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).build();
	        message.getExchange().put(Response.class, response);
        }

    }
 
    public void handleFault(Message messageParam) {
    }
}