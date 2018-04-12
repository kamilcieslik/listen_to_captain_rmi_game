package rmi;

import rmi.impl.PlayerImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Player extends Remote
{
    void confirmConnection(String message) throws RemoteException;
    void receiveCommand(String command) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void startRound(int roundTime) throws RemoteException;
    void updateNumberOfPlayers(int numberOfCaptainPlayers) throws RemoteException;
    void addPoints(Integer numberOfPoints) throws RemoteException;
    void endOfGame(List<PlayerImpl> results);
}
