package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
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
    void broadcastCommand(String playerType, String command, String captainName) throws RemoteException;

    void startRound(int roundTime, String captainNickname) throws RemoteException;
}
