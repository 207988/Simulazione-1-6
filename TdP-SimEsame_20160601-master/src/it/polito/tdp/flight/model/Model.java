package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
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
		Collections.sort(linee);
		System.out.println(airports.values().size());
		
			
			
	}
	
	public void creaGrafo(int id){
		FlightDAO dao=new FlightDAO();
		tratte.addAll(dao.getAllRoutes(id));		
		//aggiungo vertici a grafo
		for(Airport a:airports.values()){
			graph.addVertex(a);
		}
		System.out.println(graph.vertexSet().size());
		
		
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
		System.out.println(graph.vertexSet().size());
		System.out.println(graph.edgeSet().size());
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
		
		System.out.println("------"+source+"-----\n"+Graphs.neighborListOf(graph, source));
		for(Airport dest:Graphs.neighborListOf(graph, source)){	
			if(graph.containsEdge(source, dest))
				if(!airRaggiuntiRicorsione.contains(new AirportPlus(0,dest))){		
					DefaultWeightedEdge dwe=graph.getEdge(source,dest);
					distance+=graph.getEdgeWeight(dwe);			
					AirportPlus destp=new AirportPlus(distance,dest);
					airRaggiuntiRicorsione.add(destp);
					this.ricorsione(dest);
					distance-=graph.getEdgeWeight(dwe);	
				}				
			}
			
			
	}
	
	public List<AirportPlus>airportRaggiungibiliAlt(Airport source){
		List<AirportPlus> temp= new ArrayList<AirportPlus>();
		SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graphTemp=new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
		Graphs.addGraph(graphTemp, graph) ;
		for(Airport a:graph.vertexSet()){
			if(graphTemp.containsVertex(a))
				if(graphTemp.edgesOf(a).isEmpty())
					graphTemp.removeVertex(a);			
				
		}
		for(Airport dest:graphTemp.vertexSet()){
			DijkstraShortestPath<Airport,DefaultWeightedEdge> dij=new DijkstraShortestPath<Airport,DefaultWeightedEdge>(graphTemp,source,dest);
			temp.add(new AirportPlus(dij.getPathLength(),dest));
		}
		return temp;
	}
		
	

}


/* QUERY TEST PUNTO 1
 * SELECT COUNT(*)
FROM(SELECT DISTINCT Source_Airport FROM route WHERE Airline_ID=3320
		UNION
		SELECT DISTINCT Destination_Airport FROM route WHERE Airline_ID=3320
) AS temp
*/
