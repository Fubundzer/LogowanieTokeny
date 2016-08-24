package javaservlets.tokens;

import java.io.IOException;
import java.util.List;

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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
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
	@Path("/users/test2")
	@Produces(MediaType.APPLICATION_XML)
	public	String getHeader(@Context HttpHeaders hh){
		//String cos2=hh.AUTHORIZATION;
		MultivaluedMap<String, String> headerParams=hh.getRequestHeaders();
		String cos =headerParams.toString();
		return cos;
	}
	
	@POST
	@Path("/users/test")
	@Produces(MediaType.TEXT_HTML)
	//@Consumes("application/x-www-form-urlencoded")
	public String getUsers(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest req, @Context HttpServletResponse response) throws IOException{	
		
		String html = "<html><head><body><a href=\"http://localhost:8080/HelloWorldWeb/rest/UserService/users/test3\">link</a></body></head></html>";
		
		
		String ret = username + password;
		if(userDao.existUser(username, password)){
			User uUser = new User(userDao.getUser(username, password));
			uUser.setToken(userDao.issueToken());
			
			uUser.setTokenExpDate();
			userDao.updateUser(uUser);
			response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+uUser.getToken());
			System.out.println(response.getHeader(HttpHeaders.AUTHORIZATION));
			
			ret+="User exists!  "+uUser.getToken()+"     "+userDao.getUser(uUser.getId()).getTokenExpDate();
		}else{
			ret+="User does not exist";
		}
		
		return ret+html;
	}
	
	@POST
	@Path("/users/login2")
	@Produces(MediaType.TEXT_HTML)
	public Response getUsers(@FormParam("username") String username,
			@FormParam("password") String password) throws IOException{	

		
		if(userDao.existUser(username, password)){
			User uUser = new User(userDao.getUser(username, password));
			uUser.setToken(userDao.issueToken());
			
			uUser.setTokenExpDate();
			userDao.updateUser(uUser);
			System.out.println(userDao.getUser(uUser.getId()).getToken());	
			return Response.ok(userDao.getUser(uUser.getId()).getToken()).header(HttpHeaders.AUTHORIZATION, uUser.getToken()).build();
		}else{
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Secured
	@Path("/users/test3")
	@Produces(MediaType.TEXT_HTML)
	public String test6(@Context HttpHeaders httpHeaders){
		return httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);
	}
	
	@GET
	@Secured
	@Path("/users/test3")
	@Produces(MediaType.TEXT_HTML)
	public String test3(@Context HttpHeaders httpHeaders){
		return "asda";
	}
	
	@GET
	@Path("/users/test4")
	@Produces(MediaType.TEXT_HTML)
	public String test4()
	{
		String html = "<html><head><body><a href=\"http://localhost:8080/HelloWorldWeb/rest/UserService/users/test6\">link</a></body></head></html>";
		
		return html;
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
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("token") String token,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,username,password,token);
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
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("token") String token,
			@Context HttpServletResponse servletResponse) throws IOException{
			User user = new User(id,username,password,token);
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