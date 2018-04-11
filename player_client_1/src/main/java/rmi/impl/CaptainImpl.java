package rmi.impl;

import rmi.Captain;

import java.io.Serializable;

public class CaptainImpl   implements Serializable {
    private static final long serialVersionUID = 1L;

    private Captain connection;
    public String name;
    private Integer numberOfPlayers;
    private Boolean activeGame = false;

    public CaptainImpl(Captain connection, String name) {
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
        return "CaptainImpl{" +
                "name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}
