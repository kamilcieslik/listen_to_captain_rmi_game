package rmi.remote;

import rmi.PlayerClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Captain extends Remote
{
    void receiveScore(int score) throws RemoteException;
    void receivePlayerList(List<PlayerClient> players, Boolean playerHasBeenRemoved) throws RemoteException;
    void receivePlayer(String player) throws RemoteException;
    void lossConnectionWithServer() throws RemoteException;
    void addPlayerRoundAnswers(String playerAnswers, String playerNickname) throws RemoteException;
}
