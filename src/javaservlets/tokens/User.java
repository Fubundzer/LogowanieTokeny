package javaservlets.tokens;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sun.util.calendar.LocalGregorianCalendar.Date;

@XmlRootElement(name ="user")
public class User implements Serializable{

	private static final long serialVersionUID=1L;
	private int id;
	private String username;
	private String password;
	private String name;
	private String profession;
	private String token;
	private Date tokenExpDate;
	
	public User(){}
	
	public User(int id, String name, String profession){
		this.id=id;
		this.name=name;
		this.profession=profession;
	}
	
	public int getId(){
		return id;
	}
	
	@XmlElement
	public void setId(int id){
		this.id=id;
	}
	
	public String getUsername(){
		return username;
	}
	
	@XmlElement
	public void setUsername(String username){
		this.username=username;
	}
	
	public String getPassword(){
		return password;
	}
	
	@XmlElement
	public void setPassword(String password){
		this.password=password;
	}
	
	public String getName(){
		return name;
	}
	
	@XmlElement
	public void setName(String name){
		this.name=name;
	}
	
	public String getProfession(){
		return profession;
	}
	
	@XmlElement
	public void setProfession(String profession){
		this.profession=profession;
	}
	
	public String getToken(){
		return token;
	}
	
	@XmlElement
	public void setToken(String token){
		this.token=token;
	}
	
	public Date getTokenExpDate(){
		return tokenExpDate;
	}
	
	@XmlElement
	public void setTokenExpDate(Date tokenExpDate){
		this.tokenExpDate=tokenExpDate;
	}
	
	@Override
	public boolean equals(Object object){
		if(object==null){
			return false;
		}else if(!(object instanceof User)){
			return false;
		}else{
			User user = (User)object;
			if(id==user.getId()
				&& name.equals(user.getName())
				&& profession.equals(user.getProfession())
		){
				return true;
		}
	}
		return false;
	}
}