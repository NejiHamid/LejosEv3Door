/*
 * Copyright (c) 2020 by VIF (Vignon Informatique France)
 * Project : OutGateDoor
 * File : $RCSfile$
 * Created on 28 févr. 2020 by ane
 */
package test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import controleur.Controller;
import etat_porte.StateDoor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.RegulatedMotor;
import moteur.Moteur;
import portail.Capteur;


public class CaseTest {
    public Capteur         capteur_porte_ouverte;
    public Capteur         capteur_porte_fermee;
    private StateDoor      actualState;
    private StateDoor      statePartialOpened;
    private StateDoor      statePartialClosing;
    private StateDoor      statePartialClosed;
    private StateDoor      statePartialOpening;
    private StateDoor      stateBlocked;
    private StateDoor      stateClosed;
    private StateDoor      stateClosing;
    private StateDoor      stateOpened;
    private StateDoor      stateOpening;
    private StateDoor      statePartial;
    private Moteur         moteur1;
    private Moteur         moteur2;
    private RegulatedMotor regulatedMotor;
    private final Port     portSensorOpened2 = SensorPort.S1;
    private final Port     portSensorClosed2 = SensorPort.S2;

    @org.junit.Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testStateDoor() throws InterruptedException, IOException {
        Controller ctrl = new Controller(null);
        Moteur moteur = new Moteur(this.regulatedMotor);
        moteur.pousser();
        System.out.println(moteur);
        ctrl.setActualState(this.stateClosed);
        ctrl.open();
        assertTrue(ctrl.getActualState() == this.stateOpening);

    }
}
