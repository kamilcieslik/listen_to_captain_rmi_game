package rmi;

import rmi.remote.Captain;

import java.io.Serializable;

public class CaptainClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Captain connection;
    private String name;
    private Integer numberOfPlayers;
    private Boolean activeGame = false;

    public CaptainClient(Captain connection, String name) {
        this.connection = connection;
        this.name = name;
    }

    public Captain getConnection() {
        return connection;
    }

    public void setConnection(Captain connection) {
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void incrementNumberOfPlayers(){
        numberOfPlayers++;
    }

    public void decrementNumberOfPlayers(){
        numberOfPlayers--;
    }

    public Boolean getActiveGame() {
        return activeGame;
    }

    public void setActiveGame(Boolean activeGame) {
        this.activeGame = activeGame;
    }

    @Override
    public String toString() {
        return "CaptainClient{" +
                "name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}
