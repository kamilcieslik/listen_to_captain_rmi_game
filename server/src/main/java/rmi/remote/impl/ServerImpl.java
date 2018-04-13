package rmi.remote.impl;

import javafx.controller.MainController;
import rmi.CaptainClient;
import rmi.PlayerClient;
import rmi.remote.Captain;
import rmi.remote.Player;
import rmi.remote.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private MainController serverMainController;
    private HashMap<String, CaptainClient> captains;
    private HashMap<String, PlayerClient> players;

    public ServerImpl(MainController mainController) throws RemoteException {
        this.serverMainController = mainController;
        captains = new HashMap<>();
        players = new HashMap<>();
    }

    @Override
    public void addPlayer(Player connection, String type, String name, String commanderName) throws RemoteException {
        CaptainClient connectedCommander = captains.get(commanderName);
        PlayerClient player = new PlayerClient(connection, type, name, commanderName);
        players.put(name, player);

        Integer numberOfCaptainPlayers = 0;
        for (Map.Entry<String, PlayerClient> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(commanderName))
                numberOfCaptainPlayers++;

        for (Map.Entry<String, PlayerClient> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(commanderName))
                entry.getValue().getConnection().updateNumberOfPlayers(numberOfCaptainPlayers);

        if (connectedCommander != null) {
            connectedCommander.incrementNumberOfPlayers();
            connectedCommander.getConnection().sendPlayerList(createPlayersList(player.getCaptainNickname()), false);
        }
        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
    }

    @Override
    public void addCaptain(Captain connection, String name) {
        try {
            CaptainClient commander = new CaptainClient(connection, name);
            captains.put(name, commander);
            serverMainController.refreshCaptainsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePlayer(String name) throws RemoteException {
        PlayerClient player = players.get(name);
        CaptainClient connectedCommander = null;
        if (captains.containsKey(player.getCaptainNickname()))
            connectedCommander = captains.get(player.getCaptainNickname());
        players.remove(name);

        Integer numberOfCaptainPlayers = 0;
        for (Map.Entry<String, PlayerClient> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(player.getCaptainNickname()))
                numberOfCaptainPlayers++;

        for (Map.Entry<String, PlayerClient> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(player.getCaptainNickname()))
                entry.getValue().getConnection().updateNumberOfPlayers(numberOfCaptainPlayers);

        if (connectedCommander != null) {
            connectedCommander.decrementNumberOfPlayers();
            connectedCommander.getConnection().sendPlayerList(createPlayersList(player.getCaptainNickname()), true);
        }
        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
    }

    @Override
    public boolean isExistCaptainNickname(String captainNickname) {
        return captains.containsKey(captainNickname);
    }

    @Override
    public boolean isExistPlayerNickname(String playerNickname) {
        return players.containsKey(playerNickname);
    }

    @Override
    public void removeCaptain(String name, Boolean endOfGame) throws RemoteException {
        List<PlayerClient> captainsPlayers = createPlayersList(name);
        captains.remove(name);
        captainsPlayers.forEach(player -> {
            try {
                if (!endOfGame)
                    player.getConnection().lossConnectionWithServer();
                players.remove(player.getNickname());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        serverMainController.refreshPlayersList();
        serverMainController.refreshCaptainsList();
    }

    private List<PlayerClient> createPlayersList(String captain) {
        List<PlayerClient> captainPlayers = new ArrayList<>();
        try {
            for (Map.Entry<String, PlayerClient> entry : players.entrySet()) {
                if (entry.getValue().getCaptainNickname().equals(captain))
                    captainPlayers.add(entry.getValue());
            }
        } catch (Exception ignored) {

        }
        return captainPlayers;
    }

    @Override
    public List<CaptainClient> getCaptains() {
        List<CaptainClient> captainsWithNotStartedGame = new ArrayList<>();
        for (Map.Entry<String, CaptainClient> entry : captains.entrySet()) {
            if (!entry.getValue().getActiveGame())
                captainsWithNotStartedGame.add(entry.getValue());
        }
        return captainsWithNotStartedGame;
    }

    @Override
    public void startRound(int roundTime, String captainNickname) throws RemoteException {
        captains.get(captainNickname).setActiveGame(true);
        for (Map.Entry<String, PlayerClient> entry : players.entrySet()) {
            if (entry.getValue().getCaptainNickname().equals(captainNickname))
                entry.getValue().getConnection().startRound(roundTime);
        }
    }

    @Override
    public void sendCommand(String playerType, String command, String captainName) throws RemoteException {
        for (Map.Entry<String, PlayerClient> entry : players.entrySet()) {
            if (entry.getValue().getType().equals(playerType) && entry.getValue().getCaptainNickname().equals(captainName))
                entry.getValue().getConnection().getCommand(command);
        }
    }

    public HashMap<String, PlayerClient> getPlayers() {
        return players;
    }

    @Override
    public void sendPlayerAnswer(String playerAnswers, String playerNickname, String captainNickname) throws RemoteException {
        players.get(playerNickname).setRoundAnswers(playerAnswers);
        captains.get(captainNickname).getConnection().addPlayerRoundAnswers(playerAnswers, playerNickname);
    }

    @Override
    public void clearRoundAnswers(String captainNickname) throws RemoteException {
        for (Map.Entry<String, PlayerClient> entry : players.entrySet()) {
            if (entry.getValue().getCaptainNickname().equals(captainNickname))
                entry.getValue().setRoundAnswers("");
        }
        captains.get(captainNickname).getConnection().sendPlayerList(createPlayersList(captainNickname), false);
    }

    @Override
    public void addPoint(String captainNickname, String playerNickname, Integer numberOfPoints) throws RemoteException {
        players.get(playerNickname).addPoint(numberOfPoints);
        players.get(playerNickname).getConnection().addPoints(numberOfPoints);
        serverMainController.refreshPlayersList();
        captains.get(captainNickname).getConnection().sendPlayerList(createPlayersList(captainNickname), false);
    }

    @Override
    public void finishTheGame(String captainNickname, List<PlayerClient> results) throws RemoteException {
        for (Map.Entry<String, PlayerClient> entry : players.entrySet())
            if (entry.getValue().getCaptainNickname().equals(captainNickname))
                entry.getValue().getConnection().endOfGame(results);

        removeCaptain(captainNickname, true);
    }
}
