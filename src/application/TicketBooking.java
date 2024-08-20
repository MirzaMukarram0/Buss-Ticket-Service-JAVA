package application;

public class TicketBooking {
	
private Boolean status;
private BussDetails bookedbuss;

public TicketBooking(BussDetails bookedbuss) {
	super();
	this.status = false;
	this.bookedbuss = bookedbuss;
}

public Boolean getStatus() {
	return status;
}
public void setStatus(Boolean status) {
	this.status = status;
}
public BussDetails getBookedbuss() {
	return bookedbuss;
}
public void setBookedbuss(BussDetails bookedbuss) {
	this.bookedbuss = bookedbuss;
}
}
