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
	
	@POST
	@Path("/users/test")
	@Produces(MediaType.TEXT_PLAIN)
	//@Consumes("application/x-www-form-urlencoded")
	public String getUsers(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest req, @Context HttpServletResponse response){
		//String username = req.getParameter("username");
		//String password = req.getParameter("password");	
		
		String ret = username + password;
		
		if(userDao.existUser(username, password)){
			User uUser = new User(userDao.getUser(username, password));
			uUser.setToken(userDao.issueToken());
			
			uUser.setTokenExpDate();
			
			//if(uUser.getTokenExpDate().equals(null))
			//{
			//	ret+="nie ma daty";
			//}
			
			//uUser.setTokenExpDate();
			//ret+=userDao.getUser(username, password).getName();
			userDao.updateUser(uUser);
			ret+="User exists!"+uUser.getToken()+"     "+uUser.getTokenExpDate();
		}else{
			ret+="User does not exist";
		}
		
		return ret;
	}
	
	/*@GET
	@Path("/users")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUsers(@FormParam("username") String username){
		System.out.println(username);
		return Response.ok("username "+ username).build();
	}*/
	
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
			@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,name,profession,username,password);
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
			@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,name,profession,username,password);
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
}
	
	/*	@POST
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
		
	}*/
