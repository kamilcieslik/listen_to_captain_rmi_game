package rmi;

import rmi.impl.PlayerImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Captain extends Remote
{
    void receiveScore(int score) throws RemoteException;
    void receivePlayerList(List<PlayerImpl> players) throws RemoteException;
    void receivePlayer(String player) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
}
