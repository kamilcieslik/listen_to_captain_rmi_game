package rmi.impl;

import rmi.Captain;

public class CaptainImpl {
    private Captain connection;
    public String name;

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
}
