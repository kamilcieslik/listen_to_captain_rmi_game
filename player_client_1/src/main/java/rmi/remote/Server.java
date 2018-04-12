package rmi.remote;

import rmi.CaptainClient;
import rmi.PlayerClient;
import rmi.remote.impl.PlayerImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote
{
    void registerPlayer(Player player, String type, String name, String commander) throws RemoteException;
    void registerCommander(Captain commander, String name) throws RemoteException;
    void removePlayer(String name) throws RemoteException;
    void removeCommander(String name, Boolean endOfGame) throws RemoteException;
    void broadcastScore(int score) throws RemoteException;
    List<String> getListOfCommanders() throws RemoteException;
    boolean isExistCaptainNickname(String captainNickname) throws RemoteException;
    boolean isExistPlayerNickname(String playerNickname) throws RemoteException;
    List<CaptainClient> getCommanders() throws RemoteException;

    void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws  RemoteException;
    void clearRoundAnswers(String captainNickname) throws RemoteException;
    void addPoint(String captainNickname, String playerNickname, Integer numberOfPoints) throws RemoteException;
    void finishTheGame(String captainNickname, List<PlayerClient> results) throws RemoteException;
}
