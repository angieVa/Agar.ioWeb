package io.model;

import java.util.ArrayList;
import java.util.Date;

import io.connection.Client;

/**
 * Game
 */
public class Game {

    private ArrayList<Cell> cells;
    private State state;
    private int score;
    private Date Fecha;
    private ArrayList<String> adversarios;
    


    public Game(){
        cells=new ArrayList<>();
       Fecha = new Date();
      
    }
    
    

    
    public Date getFecha() {
		return Fecha;
	}




	public void setFecha(Date fecha) {
		Fecha = fecha;
	}




	public ArrayList<String> getAdversarios() {
		return adversarios;
	}




	public void setAdversarios(ArrayList<String> adversarios) {
		this.adversarios = adversarios;
	}




	/**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

	public State getState() {
		// TODO Auto-generated method stub
		return state;
	}


	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}



	
}