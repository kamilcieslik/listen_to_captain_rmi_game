package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote
{
    void confirmConnection(String message) throws RemoteException;
    void receiveCommand(SpaceCommand command) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
}
