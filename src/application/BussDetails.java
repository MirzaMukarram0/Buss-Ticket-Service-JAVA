package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class BussDetails {
private String BussNo;
private String source;
private String Destination;
private double Price;
private LocalDate date;
private Seats[] seats;
private int capacity;
private int count;

public BussDetails(String bussNo, String source, String destination, double price, LocalDate date,int cap) {
	super();
	BussNo = bussNo;
	this.source = source;
	Destination = destination;
	Price = price;
	this.date = date;
	capacity=cap;
	seats=new Seats[capacity];
	for(int i=0;i<capacity;i++) {
		seats[i]=new Seats();
	}
	setCount(0);
}


public Seats[] getSeats() {
	return seats;
}

public void setSeats(Seats[] seats) {
	this.seats = seats;
}

public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getDestination() {
	return Destination;
}
public void setDestination(String destination) {
	Destination = destination;
}
public String getBussNo() {
	return BussNo;
}
public void setBussNo(String bussNo) {
	BussNo = bussNo;
}

public double getPrice() {
	return Price;
}

public void setPrice(double price) {
	Price = price;
}
public LocalDate getDate() {
	return date;
}
public void setDate(LocalDate date) {
	this.date = date;
}

public int getCapacity() {
	return capacity;
}
public void setCapacity(int capacity) {
	this.capacity = capacity;
}


public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
}
