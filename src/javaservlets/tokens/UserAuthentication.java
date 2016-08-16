package javaservlets.tokens;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class UserAuthentication {
	
	/*@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public Response authenticateUser(@Context HttpServletRequest req){
		try{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			authenticate(username,password);
			
			String token=issueToken(username);
			
			userDao.
			
			
			return Response.ok(token).build();
		}catch(Exception e){
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private void authenticate(String username, String password)throws Exception{
		
	}
	
	private String issueToken(String username){
		Random random = new SecureRandom();
		String token = new BigInteger(130,random).toString(32);
		return token;
	}*/
	
}
