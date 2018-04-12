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
        try {
            this.captainMainController = mainController;
            this.server = server;
            this.server.registerCommander(this, captainNickname);
        } catch (RemoteException ex) {
            System.out.println("Server RemoteException.");
            System.out.println(ex.getMessage());
        }
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void receiveScore(int score) throws RemoteException {

    }

    @Override
    public void receivePlayerList(List<PlayerClient> players, Boolean playerHasBeenRemoved) throws RemoteException {
        captainMainController.refreshTableView(players, playerHasBeenRemoved);
    }

    @Override
    public void receivePlayer(String player) throws RemoteException {

    }

    @Override
    public void lossConnectionWithServer() throws RemoteException {
        captainMainController.exitFromApplication();
    }

    @Override
    public void addPlayerRoundAnswers(String playerAnswers, String playerNickname) throws RemoteException {
        captainMainController.addPlayerRoundAnswers(playerAnswers, playerNickname);
    }
}
