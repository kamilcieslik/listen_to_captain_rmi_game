package rmi.impl;

import rmi.Player;

import java.io.Serializable;

public class PlayerImpl implements  Serializable {
    private static final long serialVersionUID = 1L;

    private Player connection;
    private String captainNickname;
    private String type;
    private String nickname;
    private Integer numberOfPoints =0;

    public PlayerImpl() {
    }

    public PlayerImpl(Player connection, String type, String nickname, String captainNickname) {
        this.connection = connection;
        this.type = type;
        this.nickname = nickname;
        this.captainNickname = captainNickname;
    }

    public Player getConnection() {
        return connection;
    }

    public void setConnection(Player connection) {
        this.connection = connection;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCaptainNickname() {
        return captainNickname;
    }

    public void setCaptainNickname(String captainNickname) {
        this.captainNickname = captainNickname;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(Integer numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public void addPoint(Integer points){
        numberOfPoints+=points;
        if (numberOfPoints<0)
            numberOfPoints=0;
    }
}
