package businessLogic;

import model.Client;
import model.Queue;
import java.util.List;

public class TimeStrategy {
    public void addTask(List<Queue> queues, Client c) {
        int minTime = 200;
        for(Queue i : queues){
            if(i.getWaitingTime() < minTime){
                minTime = i.getWaitingTime();
            }
        }
        for(Queue i : queues){
            if(i.getWaitingTime() == minTime){
                i.addClient(c);
                break;
            }
        }
    }
}

