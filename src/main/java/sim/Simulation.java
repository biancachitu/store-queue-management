package sim;
import businessLogic.Scheduler;
import gui.UserInterface;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable, ActionListener {

    private Scheduler scheduler;
    private final UserInterface gui;
    private final List<Client> generatedClients;
    private String logfile = " ";
    public static int maxTime;
    private int nrClients;
    private int nrQs;
    private int peakTime;
    private int peakNr;

    static JFrame frame = new UserInterface("Honey Store");

    public Simulation(UserInterface gui) {
        generatedClients = new ArrayList<>();
        this.gui = gui;
    }

    public void init(int qs) {
        scheduler = new Scheduler(qs);
    }

    public void sorting() {
        generatedClients.sort((elem1, elem2) -> elem1.getArrivalTime() - (elem2.getArrivalTime()));
    }

    private void generateNRandomClients(int nrClients, int minA, int maxA, int minS, int maxS) {
        for (int i = 0; i < nrClients; i++) {
            int a = (int) (Math.random() * (maxA - minA + 1) + minA);
            int s = (int) (Math.random() * (maxS - minS + 1) + minS);
            int id = i;
            generatedClients.add(new Client(a, s, id));
        }
        this.sorting();
    }

    private void update(int simTime, List<Queue> qList){
        String[] s = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " "};
        for (int i = 0; i < qList.size(); i++) {
            s[i] = qList.get(i).toString();
            logfile = logfile + "Queue " + (i + 1) + ":" + qList.get(i).toStringFile() + "\n";
        }
        logfile = logfile + "\n\n\n";
        gui.setLabel(simTime + "");
        for(int i = 0; i < nrQs; i++){
            gui.setQfield(i, s[i]);
        }
        //peak time
        if(peakNr < scheduler.getNrClients()){
            peakTime = simTime;
            peakNr = scheduler.getNrClients();
        }
    }

    private void writeToFile(){
        try {
            FileWriter writer = new FileWriter("logfile.txt");
            writer.write(logfile);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private double calcAvgTime(int simTime, double avgWaitingTime, List<Queue> qs){
        for (Client c : generatedClients) {
            for (Queue queue : qs) {
                if (c.getArrivalTime() == simTime && queue.hasClient(c)) {
                    avgWaitingTime += queue.getWaitingTime();
                    break;
                }
            }
            if (c.getArrivalTime() > simTime) {
                logfile = logfile + c.toStringFile();
            }
        }
        return avgWaitingTime;
    }

    @Override
    public void run() {
        List<Queue> qList = scheduler.getQueues();
        int simTime = 0;
        double avgWaitingTime = 0;
        double serviceTime = 0;

        while (simTime < maxTime) {
            for (Client c : generatedClients) {
                if (c.getArrivalTime() == simTime) {
                    scheduler.addClient(c);
                    serviceTime += c.getWaitingTime();
                }
            }
            logfile = logfile + "Second " + simTime + "\nWaiting Clients: ";
            avgWaitingTime = calcAvgTime(simTime, avgWaitingTime, qList);
            logfile = logfile + "\n";
            update(simTime,qList);
            simTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeToFile();
        //calc average waiting and service time
        avgWaitingTime = avgWaitingTime / (double) nrClients;
        serviceTime = serviceTime / (double) nrClients;

        gui.setLabel("Average waiting time: " + avgWaitingTime + "   Average service time: " + serviceTime +"    Peak time: " + peakTime);
        logfile += "Average waiting time: " + avgWaitingTime + "\nAverage service time: " + serviceTime + "\n\n";
        gui.reset();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == gui.getsimBtn()){
            if(gui.getClients().equals("") || gui.getSim().equals("")|| gui.getQueues().equals("") || gui.getMaxA().equals("") || gui.getMinA().equals("")|| gui.getMaxS().equals("") || gui.getMinS().equals("")){
                gui.setOptionPane("Empty text boxes detected. Please fill them in.");
            }else{
                try{
                    nrClients = Integer.parseInt(gui.getClients());
                    nrQs = Integer.parseInt(gui.getQueues());
                    maxTime = Integer.parseInt(gui.getSim());
                    int maxA = Integer.parseInt(gui.getMaxA());
                    int minA = Integer.parseInt(gui.getMinA());
                    int maxS = Integer.parseInt(gui.getMaxS());
                    int minS = Integer.parseInt(gui.getMinS());

                    gui.actionPerformed();

                    if(validInput(maxA,minA,maxS,minS)){
                        generateNRandomClients(nrClients,minA,maxA,minS,maxS);
                        init(nrQs);

                        Thread t = new Thread(gui.simulation);
                        t.start();
                    }
                } catch (NumberFormatException exception){
                    gui.setOptionPane("Insert integer.");
                }
            }
        }
    }

    public boolean validInput(int maxA, int minA, int maxS, int minS) {
        if (nrClients > 1000 || nrClients < 1) {
            gui.setOptionPane("Number of clients has to be between 1 and 1000. Please input new data.");
            return false;
        } else if (nrQs < 1) {
            gui.setOptionPane("Number of queues has to be greater than 1. Please input new data.");
            return false;
        } else if (maxA + maxS > maxTime) {
            gui.setOptionPane("Maximum arrival time and maximum service time cant have a total over the simulation time. Please input new data.");
            return false;
        } else if (minA < 0 ) {
            gui.setOptionPane("Minimum arrival time cannot be smaller than 0. Please input new data.");
            return false;
        } else if (minS < 1){
            gui.setOptionPane("Minimum service time cannot be smaller than 1. Please input new data.");
            return false;
        } else if (maxS < 1) {
            gui.setOptionPane("Maximum service time cannot be smaller than 1. Please input new data.");
            return false;
        }else if (maxA < 1) {
            gui.setOptionPane("Maximum arrival time cannot be smaller than 1. Please input new data.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

