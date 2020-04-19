package controleur;


import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import etat_porte.StateDoor;
import etat_porte.StateDoorBlocked;
import etat_porte.StateDoorClosed;
import etat_porte.StateDoorClosing;
import etat_porte.StateDoorOpened;
import etat_porte.StateDoorOpenedPartial;
import etat_porte.StateDoorOpening;
import etat_porte.StateDoorPartial;
import etat_porte.StateDoorPartialClosed;
import etat_porte.StatePartialClosing;
import etat_porte.StatePartialOpening;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import moteur.Moteur;
import portail.Capteur;


public class Controller {

    private StateDoor                  actualState;
    private final StateDoor            statePartialOpened;
    private final StateDoor            statePartialClosing;
    private final StateDoor            statePartialClosed;
    private final StateDoor            statePartialOpening;
    private final StateDoor            stateBlocked;
    private final StateDoor            stateClosed;
    private final StateDoor            stateClosing;
    private final StateDoor            stateOpened;
    private final StateDoor            stateOpening;
    private final StateDoor            statePartial;

    private final ArrayList<StateDoor> listState;

    public Capteur                     capteur_porte_ouverte;
    public Capteur                     capteur_porte_fermee;

    private final Moteur               moteur1;
    private final Moteur               moteur2;

    private final Port                 portSensorOpened2 = SensorPort.S1;
    private final Port                 portSensorClosed2 = SensorPort.S2;
    DataOutputStream                   out;

    public Controller(DataOutputStream out) {
        this.moteur1 = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
        this.moteur2 = new Moteur(new EV3LargeRegulatedMotor(MotorPort.B));
        this.out = out;
        RegulatedMotor T[] = { this.moteur1.getMoteur() };
        moteur2.getMoteur().synchronizeWith(T);

        this.actualState = new StateDoorClosed();

        this.capteur_porte_fermee = new Capteur(new EV3TouchSensor(portSensorClosed2));
        this.capteur_porte_fermee.setController(this);
        this.capteur_porte_fermee.start();
        this.capteur_porte_ouverte = new Capteur(new EV3TouchSensor(portSensorOpened2));
        this.capteur_porte_ouverte.setController(this);
        this.capteur_porte_ouverte.start();

        this.stateBlocked = new StateDoorBlocked();
        this.stateClosed = new StateDoorClosed();
        this.stateClosing = new StateDoorClosing();
        this.stateOpened = new StateDoorOpened();
        this.stateOpening = new StateDoorOpening();
        this.statePartial = new StateDoorPartial();
        this.statePartialClosing = new StatePartialClosing();
        this.statePartialOpening = new StatePartialOpening();
        this.statePartialOpened = new StateDoorOpenedPartial();
        this.statePartialClosed = new StateDoorPartialClosed();
        this.listState = new ArrayList<StateDoor>();
    }

    public void open() throws InterruptedException, IOException {
        if (actualState instanceof StateDoorClosed) {
            System.out.println("ouverture...");
            moteur2.getMoteur().startSynchronization();
            moteur1.tirer();
            moteur2.tirer();
            moteur2.getMoteur().endSynchronization();
            while (capteur_porte_fermee.isContact()) {
            }
            Thread.sleep(500);
            actualState = stateOpening;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorOpened) {
            this.close();
        } else if (actualState instanceof StateDoorOpening) {
            System.out.println("On met en pause");
            moteur2.getMoteur().startSynchronization();
            moteur1.arreter();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = statePartial;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorClosing) {
            System.out.println("On ne peux pas faire pause, car fermeture");
        } else if (actualState instanceof StateDoorPartial) {
            System.out.println("On reprend l'ouverture");
            moteur2.getMoteur().startSynchronization();
            moteur1.tirer();
            moteur2.tirer();
            moteur2.getMoteur().endSynchronization();
            actualState = stateOpening;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorOpenedPartial) {
            System.out.println("Ouvrir la prote droite");
            moteur1.tirer();
            actualState = stateOpened;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        }
    }

    public void close() throws InterruptedException, IOException {
        if (actualState instanceof StateDoorClosed) {
            System.out.println("on ne peut pas fermer car deja ferme");
        } else if (actualState instanceof StateDoorOpened) {
            System.out.println("on peut fermer");
            actualState.getState();
            moteur2.getMoteur().startSynchronization();
            moteur1.pousser();
            moteur2.pousser();
            moteur2.getMoteur().endSynchronization();

            while (capteur_porte_ouverte.isContact()) {
            }
            Thread.sleep(500);

            actualState = stateClosing;

            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();

        } else if (actualState instanceof StateDoorOpenedPartial) {
            System.out.println("on peut fermer la prorte gauche");
            actualState.getState();
            moteur2.getMoteur().startSynchronization();
            moteur2.pousser();
            moteur2.getMoteur().endSynchronization();

            while (capteur_porte_ouverte.isContact()) {
            }
            Thread.sleep(500);
            actualState = stateClosing;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();

        } else if (actualState instanceof StateDoorOpening) {
            System.out.println("Impossible de fermer car ouverture");
        } else if (actualState instanceof StateDoorClosing) {
            System.out.println("Etat pause");
            moteur2.getMoteur().startSynchronization();
            moteur1.arreter();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = statePartial;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorPartial) {
            System.out.println("Reprendre la fermeture");
            moteur2.getMoteur().startSynchronization();
            moteur1.pousser();
            moteur2.pousser();
            moteur2.getMoteur().endSynchronization();
            actualState = stateClosing;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        }
    }

    public void contact() throws InterruptedException, IOException {
        if (actualState instanceof StateDoorClosing) {
            System.out.println("contact porte ferme");
            moteur2.getMoteur().startSynchronization();
            moteur1.arreter();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = stateClosed;
            System.out.println("porte fermee");
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorOpening) {
            System.out.println("contact porte ouverte");
            actualState.getState();
            moteur2.getMoteur().startSynchronization();
            moteur1.arreter();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = stateOpened;
            System.out.println("porte ouverte");
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StatePartialClosing) {
            System.out.println("contact porte gauche fermee");
            actualState.getState();
            moteur2.getMoteur().startSynchronization();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = statePartialClosed;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StatePartialOpening) {
            System.out.println("contact porte gauche ouverte");
            actualState.getState();
            moteur2.getMoteur().startSynchronization();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = statePartialOpened;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        }
    }

    public void displayListSate() {
        for (int i = 0; i < listState.size(); i++) {
            listState.get(i).getState();
        }
    }

    public void openPartial() throws InterruptedException, IOException {
        if (actualState instanceof StateDoorClosed) {
            System.out.println("ouverture partielle");
            moteur2.getMoteur().startSynchronization();
            moteur2.tirer();
            moteur2.getMoteur().endSynchronization();
            while (capteur_porte_fermee.isContact()) {
            }
            Thread.sleep(500);
            actualState = statePartialOpening;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorOpenedPartial) {
            this.close();
        } else if (actualState instanceof StatePartialOpening) {
            System.out.println("On met en pause");
            moteur2.getMoteur().startSynchronization();
            moteur2.arreter();
            moteur2.getMoteur().endSynchronization();
            actualState = statePartial;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorClosing) {
            System.out.println("On ne peux pas faire pause, car fermeture");
        } else if (actualState instanceof StateDoorPartial) {
            System.out.println("On reprend l'ouverture");
            moteur2.getMoteur().startSynchronization();
            moteur2.tirer();
            moteur2.getMoteur().endSynchronization();
            actualState = stateOpening;
            saveState(actualState);
            this.out.write(this.getActualState(actualState));
            this.out.flush();
        } else if (actualState instanceof StateDoorOpened) {
            System.out.println("Porte totalement d�ja ouverte");
        }
    }

    public StateDoor getActualState() {
        return actualState;
    }

    public void setActualState(StateDoor actualState) {
        this.actualState = actualState;
    }

    public void saveContact() throws InterruptedException, IOException {
        contact();
    }

    public void saveState(StateDoor e) {
        listState.add(e);
    }

    public Byte getActualState(StateDoor actualState) {
        if (actualState instanceof StateDoorOpened) {
            return 1;
        } else if (actualState instanceof StateDoorClosed) {
            return 2;
        } else if (actualState instanceof StateDoorOpening) {
            return 3;
        } else if (actualState instanceof StateDoorClosing) {
            return 4;
        } else if (actualState instanceof StateDoorOpenedPartial) {
            return 5;
        } else if (actualState instanceof StateDoorPartialClosed) {
            return 6;
        } else if (actualState instanceof StatePartialOpening) {
            return 7;
        } else if (actualState instanceof StatePartialClosing) {
            return 8;
        } else if (actualState instanceof StateDoorBlocked) {
            return 9;
        } else {
            return 0;
        }

    }
}
