package rmi.remote;

import rmi.PlayerClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Player extends Remote
{
    void confirmConnection(String message) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void updateNumberOfPlayers(int numberOfCaptainPlayers) throws RemoteException;
    void endOfGame(List<PlayerClient> results) throws RemoteException;

}
