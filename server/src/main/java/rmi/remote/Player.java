package rmi.remote;

import rmi.PlayerClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Player extends Remote
{
    void getCommand(String command) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void startRound(int roundTime) throws RemoteException;
    void updateNumberOfPlayers(int numberOfCaptainPlayers) throws RemoteException;
    void addPoints(Integer numberOfPoints) throws RemoteException;
    void endOfGame(List<PlayerClient> results) throws RemoteException;
}
