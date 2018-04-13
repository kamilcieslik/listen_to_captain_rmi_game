package rmi.remote;

import rmi.PlayerClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Captain extends Remote
{
    void sendPlayerList(List<PlayerClient> players, Boolean playerHasBeenRemoved) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void addPlayerRoundAnswers(String playerAnswers, String playerNickname) throws RemoteException;
}
