package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote
{
    void confirmConnection(String message) throws RemoteException;
    void receiveCommand(String command) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void startRound(int roundTime) throws RemoteException;
    void addOrSubtractPlayer(int value) throws RemoteException;
}
