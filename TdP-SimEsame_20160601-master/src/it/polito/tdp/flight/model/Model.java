package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	Map<Integer,Airport>airports=new HashMap<Integer,Airport>();
	List<Airline>linee=new ArrayList<Airline>();
	List<Route>tratte=new ArrayList<Route>();
	SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph=new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
	Set<Airport>airReached =new HashSet<Airport>();
	
	
	public List<Airport> getAeroporti() {
		return new ArrayList<Airport>(airports.values());
	}

	


	public List<Airline> getLinee() {
		return linee;
	}

	


	public Set<Airport> getAirReached() {
		System.out.println(airReached.size());
		return airReached;
	}




	public void load(){
		FlightDAO dao=new FlightDAO();
		for(Airport a:dao.getAllAirports()){
			airports.put(a.getAirportId(),a);
		}
		linee.addAll(dao.getAllAirlines());
		
		
			
			
	}
	
	public void creaGrafo(int id){
		FlightDAO dao=new FlightDAO();
		tratte.addAll(dao.getAllRoutes(id));
		System.out.print(tratte);
		//aggiungo vertici a grafo
		for(Airport a:airports.values()){
			graph.addVertex(a);
		}
		
		
		for(Route r:tratte){
			Airport a1=airports.get(r.getSourceAirportId());
			Airport a2=airports.get(r.getDestinationAirportId());
			airReached.add(a1);
			airReached.add(a2);
			if(!graph.containsEdge(a1, a2)){
				double weight = this.calcolaDistanza(a1, a2);
				DefaultWeightedEdge dwe=graph.addEdge(a1, a2);			
				graph.setEdgeWeight(dwe, weight);			
			}
		}
	}
	
	private double calcolaDistanza(Airport a1,Airport a2){
		LatLng pos1= new LatLng(a1.getLatitude(),a1.getLongitude());
		LatLng pos2= new LatLng(a2.getLatitude(),a2.getLongitude());
		return LatLngTool.distance(pos1,pos2,LengthUnit.KILOMETER);
	}
	
	List<AirportPlus> airRaggiuntiRicorsione= new ArrayList<AirportPlus>();
	double distance;
	
	public List<AirportPlus> airportRaggiungibili(Airport source){
		airRaggiuntiRicorsione.clear();
		distance=0;
		this.ricorsione(source);
		
		return airRaggiuntiRicorsione;
	}
	
	private void ricorsione(Airport source){
		
		
		for(Airport dest:Graphs.neighborListOf(graph, source)){	
			if(!airRaggiuntiRicorsione.contains(dest)){			
			
				DefaultWeightedEdge dwe=graph.getEdge(source,dest);
				distance+=graph.getEdgeWeight(dwe);			
				AirportPlus destp=new AirportPlus(distance,source);
				airRaggiuntiRicorsione.add(destp);
				this.ricorsione(dest);
				distance-=graph.getEdgeWeight(dwe);	
			}
			else
				System.out.println("NULL");
			
		}
		
	}

}


/* QUERY TEST PUNTO 1
 * SELECT COUNT(*)
FROM(SELECT DISTINCT Source_Airport FROM route WHERE Airline_ID=3320
		UNION
		SELECT DISTINCT Destination_Airport FROM route WHERE Airline_ID=3320
) AS temp
*/
