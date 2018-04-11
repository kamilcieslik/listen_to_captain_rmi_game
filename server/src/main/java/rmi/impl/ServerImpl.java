package rmi.impl;

import javafx.controller.MainController;
import rmi.Captain;
import rmi.Player;
import rmi.Server;
import rmi.SpaceCommand;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private MainController serverMainController;

    private HashMap<String, CaptainImpl> captains;
    private HashMap<String, PlayerImpl> players;

    public ServerImpl(MainController mainController) throws RemoteException {
        this.serverMainController=mainController;
        captains=new HashMap<>();
        players = new HashMap<>();
    }

    @Override
    public void registerPlayer(Player connection, String type, String name, String commanderName) throws RemoteException {
        System.out.println("Player " + name + type + " has connected.");
        CaptainImpl connectedCommander = captains.get(commanderName);
        PlayerImpl player = new PlayerImpl(connection, type, name, connectedCommander);
        players.put(name, player);
        if (connectedCommander != null) {
            connectedCommander.getConnection().receivePlayerList(createPlayersList(commanderName));
        } else {
            System.out.println("Error, commander is null.");
        }

        serverMainController.refreshPlayersList();
    }

    @Override
    public void registerCommander(Captain connection, String name) throws RemoteException {
        try {
            System.out.println("Commander " + name + " has connected.");
            CaptainImpl commander = new CaptainImpl(connection, name);
            commander.getConnection().receiveScore(2137);
            captains.put(name, commander);
            serverMainController.refreshCaptainsList();

            System.out.println("elo");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removePlayer(String name) throws RemoteException {
        PlayerImpl player = players.get(name);
        CaptainImpl commander = player.getCaptain();
        players.remove(name);
        if (commander != null) {
            commander.getConnection().receivePlayerList(createPlayersList(commander.getName()));
        } else {
            System.out.println("Error, commander is null.");
        }

        serverMainController.refreshPlayersList();
    }

    @Override
    public List<String> getListOfCommanders() throws RemoteException {
        List<String> commandersNames = new ArrayList<>();
        for (Map.Entry<String, CaptainImpl> entry : captains.entrySet()) {
            commandersNames.add(entry.getValue().getName());
        }
        return commandersNames;
    }

    @Override
    public boolean isExistCaptainNickname(String captainNickname) throws RemoteException{
        return captains.containsKey(captainNickname);
    }

    @Override
    public boolean isExistPlayerNickname(String playerNickname)throws RemoteException {
        return players.containsKey(playerNickname);
    }

    @Override
    public void removeCommander(String name) {
        captains.remove(name);
        serverMainController.refreshCaptainsList();
    }

    @Override
    public void broadcastScore(int score) throws RemoteException {
        System.out.println("Score changed by " + score);
        //commander.getConnection().receiveScore(score);
    }

    @Override
    public void broadcastCommand(SpaceCommand spaceCommand) throws RemoteException {
        System.out.println("Sending " + spaceCommand.getType() + " command.");
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
            if (entry.getValue().getType() == spaceCommand.getType())
                entry.getValue().getConnection().receiveCommand(spaceCommand);
        }
    }


    private List<PlayerImpl> createPlayersList(String captainNickname) {
        List<PlayerImpl> captainPlayers = new ArrayList<>();

        for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
            if (entry.getValue().getCaptain().getName().equals(captainNickname))
                captainPlayers.add(entry.getValue());
        }

        return captainPlayers;
    }

    public HashMap<String, CaptainImpl> getCommanders() {
        return captains;
    }

    public HashMap<String, PlayerImpl> getPlayers() {
        return players;
    }
}
