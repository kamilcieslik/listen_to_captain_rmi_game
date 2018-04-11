package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Captain extends Remote
{
    void receiveScore(int score) throws RemoteException;
    void receivePlayerList(List<String> players) throws RemoteException;
    void receivePlayer(String player) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
}
