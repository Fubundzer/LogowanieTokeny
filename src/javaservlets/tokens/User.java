package javaservlets.tokens;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//import sun.util.calendar.LocalGregorianCalendar.Date;
import java.util.Date;

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
	
	public User(int id, String name, String profession,String username, String password, String token){
		this.id=id;
		this.name=name;
		this.profession=profession;
		this.username=username;
		this.password=password;
		this.token=token;
		this.tokenExpDate=new Date();
	}
	
	public User(User user){
		this.id=user.id;
		this.name=user.name;
		this.profession=user.profession;
		this.username=user.username;
		this.password=user.password;
		this.tokenExpDate=user.getTokenExpDate();
	}
	
	/*public User(int id,String username, String password){
		this.id=id;
		this.username=username;
		this.password=password;
	}*/
	
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
	
	//@XmlElement
	public void setToken(String token){
		this.token=token;
	}
	
	public Date getTokenExpDate(){
		return tokenExpDate;
	}
	
	//@XmlElement
	public void setTokenExpDate(){
		//new Date().getHours()-this.tokenExpDate.getHours()>1||
		if(new Date().getTime()-this.tokenExpDate.getTime()>=3600000)
		{
			System.out.println("test  "+(new Date().getTime()-this.tokenExpDate.getTime())+"  "+this.tokenExpDate.toString());
		
			Date data=new Date();
			
			this.tokenExpDate.setTime(new Date().getTime());;
			System.out.println(this.tokenExpDate.toString());
		}else{
			Date data = new Date();
			data.setHours(11);
			System.out.println(tokenExpDate.getTime());
			System.out.println(data.getTime()-new Date().getTime());
			System.out.println(this.tokenExpDate.toString()+"   "+data.toString()+"   "+data.getTime());
			System.out.println("asdas");
		}
		//this.tokenExpDate=tokenExpDate;
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