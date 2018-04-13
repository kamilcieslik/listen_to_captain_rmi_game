package rmi.remote.impl;

import javafx.controller.MainController;
import rmi.PlayerClient;
import rmi.remote.Captain;
import rmi.remote.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CaptainImpl extends UnicastRemoteObject implements Captain {
    private Server server;
    private MainController captainMainController;

    public CaptainImpl(String captainNickname, MainController mainController, Server server) throws RemoteException {
            this.captainMainController = mainController;
            this.server = server;
            this.server.addCaptain(this, captainNickname);
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void sendPlayerList(List<PlayerClient> players, Boolean playerHasBeenRemoved) {
        captainMainController.refreshTableView(players, playerHasBeenRemoved);
    }

    @Override
    public void lossConnectionWithServer() {
        captainMainController.exitFromApplication();
    }

    @Override
    public void addPlayerRoundAnswers(String playerAnswers, String playerNickname) {
        captainMainController.addPlayerRoundAnswers(playerAnswers, playerNickname);
    }
}
