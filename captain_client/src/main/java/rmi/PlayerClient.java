package rmi;

import rmi.remote.Player;

import java.io.Serializable;

public class PlayerClient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Player connection;
    private String captainName;
    private String type;
    private String nickname;
    private Integer numberOfPoints;
    private String roundAnswers = "";

    public PlayerClient() {
    }

    public PlayerClient(Player connection, String type, String nickname, String captainName) {
        this.connection = connection;
        this.type = type;
        this.nickname = nickname;
        this.captainName=captainName;
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

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
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

    public String getRoundAnswers() {
        return roundAnswers;
    }

    public void setRoundAnswers(String roundAnswers) {
        this.roundAnswers = roundAnswers;
    }
}
