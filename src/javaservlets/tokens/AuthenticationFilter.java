package javaservlets.tokens;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.NameBinding;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.server.ContainerRequest;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

	private UserDao userDao;
	
	
	//public AuthenticationFilter(UserDao userDao){
	//	this.userDao=userDao;
	//}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		System.out.println(authorizationHeader);
		
		if(authorizationHeader==null||!authorizationHeader.startsWith("Bearer")){
			System.out.println("!!!");
			throw new NotAuthorizedException("Authorization header must be provided");
		}
		
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		try{
			validateToken(token);
		}catch(Exception e){
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
		System.out.println("okok");
	}
	
	private void validateToken(String token) throws Exception{
		List<User> users=userDao.getAllUsers();
		try{
		for (User user : users){
			if(user.getToken().equals(token)){
				System.out.println("ok");
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
