package model;

import sim.Simulation;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{
    private final BlockingQueue<Client> queue;
    private final AtomicInteger qTime = new AtomicInteger(0);

    public Queue(){
        queue = new LinkedBlockingQueue<>();
        qTime.set(0);
    }

    public void addClient(Client newClient){
        queue.add(newClient);
        qTime.set(qTime.intValue()+ newClient.getWaitingTime());
    }

    @Override
    public String toString(){
        String str = "";
        for(Client c : queue){
            str += c.toString() + "      ";
        }
        return str;
    }

    public String toStringFile(){
        String str = "";
        for(Client c : queue){
            str += c.toStringFile()+";";
        }
        return str;
    }

    public boolean hasClient(Client c){
        for(Client client:queue){
            if(client.equals(c)){
                return true;
            }
        }
        return false;
    }

    public int getWaitingTime(){
        return qTime.intValue();
    }

    @Override
    public void run() {
        int simTime = 0;
        while(simTime < Simulation.maxTime){
            Client c;
            c = queue.peek();
            if(c != null){
                try {
                    Thread.sleep(1000 * c.getWaitingTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.remove();
                qTime.set(qTime.intValue() - c.getWaitingTime());
            }
        }
    }
    public int getSize(){
        return queue.size();
    }
}
