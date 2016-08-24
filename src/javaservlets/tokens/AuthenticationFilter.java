package javaservlets.tokens;

import java.io.IOException;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{

	private UserDao userDao;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		//requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "asda");
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		System.out.println(authorizationHeader);
		
		if(authorizationHeader==null||!authorizationHeader.startsWith("auth_token")){
			System.out.println("!!!");
			throw new NotAuthorizedException("Authorization header must be provided");
		}
		
		String token = authorizationHeader.substring("auth_token".length()).trim();
		
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
