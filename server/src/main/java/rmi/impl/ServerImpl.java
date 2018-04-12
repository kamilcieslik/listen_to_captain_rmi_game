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
        this.serverMainController = mainController;
        captains = new HashMap<>();
        players = new HashMap<>();
    }

    @Override
    public void registerPlayer(Player connection, String type, String name, String commanderName) throws RemoteException {
        CaptainImpl connectedCommander = captains.get(commanderName);
        PlayerImpl player = new PlayerImpl(connection, type, name, commanderName);
        players.put(name, player);

        Integer numberOfCaptainPlayers = 0;
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(commanderName))
                numberOfCaptainPlayers++;

        for (Map.Entry<String, PlayerImpl> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(commanderName))
                entry.getValue().getConnection().updateNumberOfPlayers(numberOfCaptainPlayers);

        if (connectedCommander != null) {
            connectedCommander.incrementNumberOfPlayers();
            connectedCommander.getConnection().receivePlayerList(createPlayersList(player.getCaptainNickname()), false);
        }
        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
    }

    @Override
    public void registerCommander(Captain connection, String name) throws RemoteException {
        try {
            CaptainImpl commander = new CaptainImpl(connection, name);
            captains.put(name, commander);
            serverMainController.refreshCaptainsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePlayer(String name) throws RemoteException {
        PlayerImpl player = players.get(name);
        CaptainImpl connectedCommander = null;
        if (captains.containsKey(player.getCaptainNickname()))
            connectedCommander = captains.get(player.getCaptainNickname());
        players.remove(name);

        Integer numberOfCaptainPlayers = 0;
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(player.getCaptainNickname()))
                numberOfCaptainPlayers++;

        for (Map.Entry<String, PlayerImpl> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(player.getCaptainNickname()))
                entry.getValue().getConnection().updateNumberOfPlayers(numberOfCaptainPlayers);

        if (connectedCommander != null) {
            connectedCommander.decrementNumberOfPlayers();
            connectedCommander.getConnection().receivePlayerList(createPlayersList(player.getCaptainNickname()), true);
        }
        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
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
    public boolean isExistCaptainNickname(String captainNickname) throws RemoteException {
        return captains.containsKey(captainNickname);
    }

    @Override
    public boolean isExistPlayerNickname(String playerNickname) throws RemoteException {
        return players.containsKey(playerNickname);
    }


    @Override
    public void removeCommander(String name) throws RemoteException {
        List<PlayerImpl> captainsPlayers = createPlayersList(name);
        captains.remove(name);
        captainsPlayers.forEach(player -> {
            try {
                player.getConnection().lossConnectionWithServer();
                players.remove(player.getNickname());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
    }

    @Override
    public void broadcastScore(int score) throws RemoteException {
        System.out.println("Score changed by " + score);
        //commander.getConnection().receiveScore(score);
    }

    @Override
    public void broadcastCommand(SpaceCommand spaceCommand) throws RemoteException {

    }


    private List<PlayerImpl> createPlayersList(String captain) {
        List<PlayerImpl> captainPlayers = new ArrayList<>();

        try {
            for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
                if (entry.getValue().getCaptainNickname().equals(captain))
                    captainPlayers.add(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return captainPlayers;
    }

    public List<CaptainImpl> getCommanders() throws RemoteException {
        List<CaptainImpl> captainsWithNotStartedGame = new ArrayList<>();
        for (Map.Entry<String, CaptainImpl> entry : captains.entrySet()) {
            if (!entry.getValue().getActiveGame())
                captainsWithNotStartedGame.add(entry.getValue());
        }
        return captainsWithNotStartedGame;
    }

    @Override
    public void startRound(int roundTime, String captainNickname) throws RemoteException {
        captains.get(captainNickname).setActiveGame(true);
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
            if (entry.getValue().getCaptainNickname().equals(captainNickname))
                entry.getValue().getConnection().startRound(roundTime);
        }
    }

    @Override
    public void broadcastCommand(String playerType, String command, String captainName) throws RemoteException {
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
            if (entry.getValue().getType().equals(playerType) && entry.getValue().getCaptainNickname().equals(captainName))
                entry.getValue().getConnection().receiveCommand(command);
        }
    }

    public HashMap<String, PlayerImpl> getPlayers() {
        return players;
    }

    @Override
    public void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws RemoteException {
        players.get(playerNickname).setRoundAnswers(playerAnswers);
        captains.get(captainNickname).getConnection().addPlayerRoundAnswers(playerAnswers, playerNickname);
    }

    @Override
    public void clearRoundAnswers(String captainNickname) throws RemoteException {
        for (Map.Entry<String, PlayerImpl> entry : players.entrySet()) {
            if (entry.getValue().getCaptainNickname().equals(captainNickname))
                entry.getValue().setRoundAnswers("");
        }
        captains.get(captainNickname).getConnection().receivePlayerList(createPlayersList(captainNickname), false);
    }
}
