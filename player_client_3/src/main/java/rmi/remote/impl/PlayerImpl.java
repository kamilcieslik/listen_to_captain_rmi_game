package rmi.remote.impl;

import javafx.application.Platform;
import javafx.controller.MainController;
import rmi.PlayerClient;
import rmi.remote.Player;
import rmi.remote.Server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PlayerImpl extends UnicastRemoteObject implements Player, Serializable {
    private static final long serialVersionUID = 1L;

    private Server server;
    private MainController playerMainController;

    public PlayerImpl(String nickname, String type, String captainNickname, MainController mainController, Server server) throws RemoteException {
            this.playerMainController = mainController;
            this.server = server;
            this.server.addPlayer(this, type, nickname, captainNickname);
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void getCommand(String command) {
        playerMainController.getPlayerBeanType_3().setStringPropertyCaptainCommand(command);
    }

    @Override
    public void lossConnectionWithServer() {
        playerMainController.exitFromApplication();
    }

    @Override
    public void startRound(int roundTime) {
        playerMainController.startCountdownToTheEndOfRound(roundTime);
    }

    @Override
    public void updateNumberOfPlayers(int numberOfCaptainPlayers) {
        Platform.runLater(()-> playerMainController.getPlayerBeanType_3().setIntegerPropertyNumberOfPlayers(numberOfCaptainPlayers));
    }

    @Override
    public void addPoints(Integer numberOfPoints) {
        Platform.runLater(()-> playerMainController.getPlayerBeanType_3()
                .setIntegerPropertyNumberOfPoints(playerMainController.getPlayerBeanType_3()
                        .getIntegerPropertyNumberOfPoints()+numberOfPoints));
    }

    @Override
    public void endOfGame(List<PlayerClient> results) {
        playerMainController.endOfGame(results);
    }
}
