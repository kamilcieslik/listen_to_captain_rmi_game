package rmi.remote;

import rmi.CaptainClient;
import rmi.PlayerClient;
import rmi.remote.impl.PlayerImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote
{
    void addPlayer(Player player, String type, String name, String commander) throws RemoteException;
    void removePlayer(String name) throws RemoteException;
    boolean isExistPlayerNickname(String playerNickname) throws RemoteException;
    List<CaptainClient> getCaptains() throws RemoteException;
    void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws  RemoteException;
}
