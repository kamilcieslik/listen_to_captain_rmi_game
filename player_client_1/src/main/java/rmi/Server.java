package rmi;

import rmi.impl.CaptainImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface Server extends Remote
{
    void registerPlayer(Player player, String type, String name, String commander) throws RemoteException;
    void registerCommander(Captain commander, String name) throws RemoteException;
    void removePlayer(String name) throws RemoteException;
    void removeCommander(String name) throws RemoteException;
    void broadcastScore(int score) throws RemoteException;
    void broadcastCommand(SpaceCommand command) throws RemoteException;
    List<String> getListOfCommanders() throws RemoteException;
    boolean isExistCaptainNickname(String captainNickname) throws RemoteException;
    boolean isExistPlayerNickname(String playerNickname) throws RemoteException;
    List<CaptainImpl> getCommanders() throws RemoteException;

    void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws  RemoteException;
    void clearRoundAnswers(String captainNickname) throws RemoteException;
}
