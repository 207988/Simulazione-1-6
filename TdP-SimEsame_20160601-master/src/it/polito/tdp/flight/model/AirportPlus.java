package it.polito.tdp.flight.model;

public class AirportPlus {
	double distance;
	Airport airport;
	
	
	
	
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
		result = prime * result + ((airport == null) ? 0 : airport.hashCode());
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
		if (airport == null) {
			if (other.airport != null)
				return false;
		} else if (!airport.equals(other.airport))
			return false;
		return true;
	}



	


	



	

}
