package businessLogic;

import model.Client;
import model.Queue;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Queue> queues;
    private final TimeStrategy strategy;

    public Scheduler(int maxNr){
        queues = new ArrayList<>();
        strategy = new TimeStrategy();
        for(int i = 0; i < maxNr; i++){
            Queue q = new Queue();
            queues.add(q);
            Thread t = new Thread(q);
            t.start();
        }
    }

    public void addClient(Client c){
        strategy.addTask(queues,c);
    }

    public int getNrClients(){
        int nrClients = 0;
        for(Queue q : queues){
            nrClients += q.getSize();
        }
        return nrClients;
    }

    public List<Queue> getQueues(){
        return queues;
    }
}
