package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Route;

public class FlightDAO {

	public List<Airport> getAllAirports() {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add( new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"),
						res.getDouble("Longitude"),
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"))) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	public List<Airline> getAllAirlines() {
			
			String sql = "SELECT * FROM airline" ;
			
			List<Airline> list = new ArrayList<>() ;
			
			try {
				Connection conn = DBConnect.getConnection() ;
	
				PreparedStatement st = conn.prepareStatement(sql) ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					list.add( new Airline(
							res.getInt("Airline_ID"),
							res.getString("name"),
							res.getString("alias"),						
							res.getString("IATA"),
							res.getString("ICAO"),
							res.getString("Callsign"),
							res.getString("Country"),
							res.getString("Active")						
							));
				}
				
				conn.close();
				
				return list ;
			} catch (SQLException e) {
	
				e.printStackTrace();
				return null ;
			}
		}
	public List<Route> getAllRoutes(int id) {
			
			String sql = "SELECT * FROM route WHERE Airline_ID=?" ;
			
			List<Route> list = new ArrayList<>() ;
			
			try {
				Connection conn = DBConnect.getConnection() ;
	
				PreparedStatement st = conn.prepareStatement(sql) ;
				st.setInt(1, id);
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					list.add( new Route(
							res.getString("Airline"),	
							res.getInt("Airline_ID"),
							res.getString("Source_airport"),
							res.getInt("Source_airport_ID"),
							res.getString("Destination_Airport"),
							res.getInt("Destination_airport_ID"),
							res.getString("Codeshare"),						
							res.getInt("Stops"),
							res.getString("Equipment")													
							));
				}
				
				conn.close();
				
				return list ;
			} catch (SQLException e) {
	
				e.printStackTrace();
				return null ;
			}
		}
	
	public static void main(String args[]) {
		FlightDAO dao = new FlightDAO() ;
		
		List<Airport> arps = dao.getAllAirports() ;
		System.out.println(arps);
	}
	
}
