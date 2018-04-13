package rmi.remote;

import rmi.PlayerClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote
{
    void addCaptain(Captain commander, String name) throws RemoteException;
    void removeCaptain(String name, Boolean endOfGame) throws RemoteException;
    boolean isExistCaptainNickname(String captainNickname) throws RemoteException;
    void sendCommand(String playerType, String command, String captainName) throws RemoteException;
    void startRound(int roundTime, String captainNickname) throws RemoteException;
    void clearRoundAnswers(String captainNickname) throws RemoteException;
    void addPoint(String captainNickname, String playerNickname, Integer numberOfPoints) throws RemoteException;
    void finishTheGame(String captainNickname, List<PlayerClient> results) throws RemoteException;
}
