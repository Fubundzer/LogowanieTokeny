package javaservlets.tokens;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDao {
	
	public List<User> getAllUsers(){
		List<User> userList = null;
		try{
			File file = new File("Users.dat");
			if(!file.exists()){
				User user = new User(1,"Mahesh","Teacher","asd","asd","token");
				//User user = new User(1,"asd","asd");
				userList=new ArrayList<User>();
				userList.add(user);
				saveUserlist(userList);
			}
			else{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				userList=(List<User>)ois.readObject();
				ois.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return userList;
	}
	
	public User getUser(int id){
		List<User> users = getAllUsers();
		
		for(User user: users){
			if(user.getId()==id){
				return user;
			}
		}
		return null;
	}
	
	public User getUser(String username, String password){
		List<User> users = getAllUsers();
		
		for(User user: users){
			if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
				return user;
			}
		}
		return null;
	}
	
	public int addUser(User pUser){
		List<User> userList = getAllUsers();
		boolean userExists=false;
		for(User user: userList){
			if(user.getId()==pUser.getId()){
				userExists=true;
				break;
			}
		}
		if(!userExists){
			userList.add(pUser);
			saveUserlist(userList);
			return 1;
		}
		return 0;
	}
	
	public int updateUser(User pUser){
		List<User> userList=getAllUsers();
		for(User user: userList){
			if(user.getId()==pUser.getId()){
				int index = userList.indexOf(user);
				userList.set(index,pUser);
				saveUserlist(userList);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteUser(int id){
		List<User> userList = getAllUsers();
		
		for(User user: userList){
			if(user.getId()==id){
				int index = userList.indexOf(user);
				userList.remove(index);
				saveUserlist(userList);
				return 1;
			}
		}
		return 0;
	}
	
	private void saveUserlist(List<User> userList)
	{
		try{
			File file = new File("Users.dat");
			FileOutputStream fos;
			
			fos=new FileOutputStream(file);
			
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(userList);
			oos.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean existUser(String username, String password)
	{
		List<User> users = getAllUsers();
		
		for(User user : users)
		{
			if(user.getUsername().equals(username)&&user.getPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}
	
	public String issueToken(){
		Random random = new SecureRandom();
		String token = new BigInteger(130,random).toString(32);
		return token;
	}
}