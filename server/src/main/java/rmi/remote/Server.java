package rmi.remote;

import rmi.CaptainClient;
import rmi.PlayerClient;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote
{
    void addPlayer(Player player, String type, String name, String commander) throws RemoteException;
    void addCaptain(Captain commander, String name) throws RemoteException;
    void removePlayer(String name) throws RemoteException;
    void removeCaptain(String name, Boolean endOfGame) throws RemoteException;
    boolean isExistCaptainNickname(String captainNickname) throws RemoteException;
    boolean isExistPlayerNickname(String playerNickname) throws RemoteException;
    void sendCommand(String playerType, String command, String captainName) throws RemoteException;
    List<CaptainClient> getCaptains() throws RemoteException;
    void startRound(int roundTime, String captainNickname) throws RemoteException;
    void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws  RemoteException;
    void clearRoundAnswers(String captainNickname) throws RemoteException;
    void addPoint(String captainNickname, String playerNickname, Integer numberOfPoints) throws RemoteException;
    void finishTheGame(String captainNickname, List<PlayerClient> results) throws RemoteException;
}