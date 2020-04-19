package portail;


import java.io.IOException;

import controleur.Controller;
import lejos.hardware.sensor.EV3TouchSensor;


public class Capteur extends Thread {

    private final EV3TouchSensor capteur;
    private Controller           controller;
    private boolean              running;

    public Capteur(EV3TouchSensor capteur) {
        this.capteur = capteur;
        this.running = true;
    }

    public boolean getRunning() {
        return running;
    }

    @Override
    public void run() {
        try {
            this.contact();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void contact() throws InterruptedException, IOException {
        while (running) {
            int size = this.capteur.sampleSize();
            float[] sample = new float[size];
            capteur.fetchSample(sample, 0);
            if (sample[0] == 1.0) {
                this.controller.saveContact();
            }
        }
    }

    public boolean isContact() {
        int size = this.capteur.sampleSize();
        float[] sample = new float[size];
        capteur.fetchSample(sample, 0);
        if (sample[0] == 1.0) {
            return true;
        }
        return false;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void arret() {
        running = false;
    }
}
