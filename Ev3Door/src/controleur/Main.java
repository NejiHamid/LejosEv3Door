package controleur;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.hardware.Bluetooth;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;


public class Main {
    private static DataOutputStream out;
    private static DataInputStream  in;
    private static BTConnection     BTConnect;
    private static int              commande = 0;
    private static boolean          stop_app;

    public static void main(String[] args) throws IOException, InterruptedException {
        stop_app = true;
        connect();
        Controller ctrl = new Controller(out);
        out.write(ctrl.getActualState(ctrl.getActualState()));
        out.flush();
        while (stop_app) {
            try {
                commande = in.readByte();
                System.out.println(commande);
                switch (commande) {
                    // Ouvrerture totale
                    case 1:
                        ctrl.open();
                        break;
                    // ouverture partielle
                    case 2:
                        ctrl.openPartial();

                        break;
                    // Quitter
                    case 3:
                        stop_app = false;
                        // TimeUnit.SECONDS.sleep(15);
                        ctrl.close();
                        while (!ctrl.capteur_porte_fermee.isContact()) {
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException ioe) {
                System.out.println("IO Exception readInt");
            }
            System.out.println(ctrl.getActualState());
        }
        ctrl.capteur_porte_fermee.arret();
        ctrl.capteur_porte_ouverte.arret();
        out.close();
        in.close();
        System.out.println("Bye bye DOUDOU CMD:" + commande);
        System.exit(1);

    }

    public static void connect() {
        System.out.println("En attente !!");
        BTConnector BTconnector = (BTConnector) Bluetooth.getNXTCommConnector();
        BTConnect = (BTConnection) BTconnector.waitForConnection(30000, NXTConnection.RAW);
        out = BTConnect.openDataOutputStream();
        in = BTConnect.openDataInputStream();
    }
}