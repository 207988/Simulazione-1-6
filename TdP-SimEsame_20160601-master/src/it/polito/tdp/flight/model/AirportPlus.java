package it.polito.tdp.flight.model;

public class AirportPlus implements Comparable<AirportPlus> {
	private double distance;
	private Airport airport;
	private int id;
	
	
	
	
	public Airport getAirport() {
		return airport;
	}



	public void setAirport(Airport airport) {
		this.airport = airport;
	}



	public AirportPlus(double distance, Airport airport) {
		super();
		this.distance = distance;
		this.airport = airport;
		this.id=airport.getAirportId();
		
	}



	public double getDistance() {
		return distance;
	}



	public void setDistance(double distance) {
		this.distance = distance;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirportPlus other = (AirportPlus) obj;
		if (id != other.id)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return airport+"->"+distance;
	}



	@Override
	public int compareTo(AirportPlus arg0) {
		
		return (int)(this.distance-arg0.distance);
	}
	
	



	



	


	


	



	

}
