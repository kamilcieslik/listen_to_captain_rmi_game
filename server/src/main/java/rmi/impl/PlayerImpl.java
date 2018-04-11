package rmi.impl;

import rmi.Player;

public class PlayerImpl {
    private Player connection;
    private CaptainImpl captain;
    private String type;
    private String nickname;

    public PlayerImpl(Player connection, String type, String nickname, CaptainImpl captain) {
        this.connection = connection;
        this.type = type;
        this.nickname = nickname;
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

    public CaptainImpl getCaptain() {
        return captain;
    }

    public void setCaptain(CaptainImpl captain) {
        this.captain = captain;
    }
}
