package rmi.impl;

import javafx.controller.MainController;
import rmi.Player;
import rmi.Server;
import rmi.SpaceCommand;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void receiveCommand(SpaceCommand command) throws RemoteException {
        System.out.println("Received command " + command.getType() + " " + command.getParameters() + ".");
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
}
