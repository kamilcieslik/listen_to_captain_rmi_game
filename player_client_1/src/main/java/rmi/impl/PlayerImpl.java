package rmi.impl;

import javafx.application.Platform;
import javafx.controller.MainController;
import rmi.Player;
import rmi.Server;
import rmi.SpaceCommand;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PlayerImpl extends UnicastRemoteObject implements Player, Serializable {
    private static final long serialVersionUID = 1L;

    private Server server;
    private MainController playerMainController;

    public PlayerImpl(String nickname, String type, String captainNickname, MainController mainController, Server server) throws RemoteException {
        try {
            this.playerMainController = mainController;
            this.server = server;
            this.server.registerPlayer(this, type, nickname, captainNickname);
        } catch (RemoteException ex) {
            System.out.println("Server RemoteException.");
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void confirmConnection(String message) throws RemoteException {

    }

    @Override
    public void receiveCommand(String command) throws RemoteException {
        playerMainController.getPlayerBeanType_1().setStringPropertyCaptainCommand(command);
        //String text = spaceCommand.getType().name() + " " + spaceCommand.getParameters().toString();
        //controller.textField.setText(text);
        //controller.CurrentCommand = spaceCommand;
    }

    @Override
    public void lossConnectionWithServer() throws RemoteException {
        playerMainController.exitFromApplication();
    }

    @Override
    public void startRound(int roundTime) throws RemoteException {
        playerMainController.startCountdownToTheEndOfRound(roundTime);
    }

    @Override
    public void updateNumberOfPlayers(int numberOfCaptainPlayers) throws RemoteException {
        Platform.runLater(()-> playerMainController.getPlayerBeanType_1().setIntegerPropertyNumberOfPlayers(numberOfCaptainPlayers));
    }

    @Override
    public void addPoints(Integer numberOfPoints) throws RemoteException {
        Platform.runLater(()-> playerMainController.getPlayerBeanType_1()
                .setIntegerPropertyNumberOfPoints(playerMainController.getPlayerBeanType_1()
                        .getIntegerPropertyNumberOfPoints()+numberOfPoints));
    }

    @Override
    public void endOfGame(List<PlayerImpl> results) {
        playerMainController.endOfGame(results);
    }
}
