package application;

public class Traveller {
private String Name;
private String Email;
private String password;


public Traveller(String name, String email, String password) {
	super();
	Name = name;
	Email = email;
	this.password = password;
}

public Traveller() {
	super();
	// TODO Auto-generated constructor stub
}

public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
}
