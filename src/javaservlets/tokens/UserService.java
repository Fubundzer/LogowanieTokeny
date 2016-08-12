package javaservlets.tokens;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

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

@Path("/UserService")
public class UserService {

	UserDao userDao=new UserDao();
	private static final String SUCCESS_RESULT="<result>succes</result>";
	private static final String FAILURE_RESULT="<result>failure</result>";
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getUsers(){
		return userDao.getAllUsers();
	}
	
	@GET
	@Path("/users/{userid}")
	@Produces(MediaType.APPLICATION_XML)
	public User getUser(@PathParam("userid") int userid){
		return userDao.getUser(userid);
	}
	
	@PUT
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createUser(@FormParam("id") int id,
			@FormParam("name") String name,
			@FormParam("profession") String profession,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,name,profession);
			int result = userDao.addUser(user);
			if(result==1){
				return SUCCESS_RESULT;
			}
			return FAILURE_RESULT;
	}
	
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateUser(@FormParam("id") int id,
			@FormParam("name") String name,
			@FormParam("profession") String profession,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,name,profession);
			int result=userDao.updateUser(user);
			if(result==1){
				return SUCCESS_RESULT;
			}
			return FAILURE_RESULT;
	}
	
	@DELETE
	@Path("/users/{userid}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteUser(@PathParam("userid") int userid){
		int result = userDao.deleteUser(userid);
		if(result==1){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}
	
	@OPTIONS
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	public String getSupportedOperations(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}
	
		@POST
		@Path("/authentication")	
		@Produces("application/json")
		@Consumes("application/x-www-form-urlencoded")
		public Response authenticateUser(@FormParam("username") String username,
										 @FormParam("password") String password){
			try{
				authenticate(username,password);
				
				String token=issueToken(username);
				
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
		}
		
	}
}