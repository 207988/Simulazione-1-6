package it.polito.tdp.flight;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportPlus;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	Model model;
	

    public void setModel(Model m) {
		this.model = m;
		model.load();
		boxAirline.getItems().addAll(model.getLinee());
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRaggiungibili(ActionEvent event) {
    	if(boxAirline.getValue()==null){
    		txtResult.clear();
    		txtResult.setText("Seleziona una compagnia");
    		return;
    	}
    	else{
    		model.creaGrafo(boxAirline.getValue().getAirlineId());
    		for(Airport a:model.getAirReached()){
    			txtResult.appendText(a+"\n");
     		}
    		boxAirport.getItems().addAll(model.getAirReached());
    	}
    }

    @FXML
    void doServiti(ActionEvent event) {
    	txtResult.clear();
    	if(boxAirport.getValue()==null){
    		txtResult.clear();
    		txtResult.setText("Seleziona una compagnia");
    		return;
    	}
    	else{
    		List<AirportPlus> temp= new ArrayList<AirportPlus>(model.airportRaggiungibili(boxAirport.getValue()));
    		for(AirportPlus a:temp)
    			txtResult.appendText(a+"\n");
    		
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }
}
