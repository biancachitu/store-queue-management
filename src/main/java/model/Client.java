package model;

public class Client {
    private int arrivalTime;
    private int serviceTime;
    private int id;

    public Client(int arrivalTime, int serviceTime, int id){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getWaitingTime() {
        return this.serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString(){
        return "ʕ•ᴥ•ʔ [id: " + id + "; a: " + arrivalTime + "; s: " + serviceTime + "]";
    }

    public String toStringFile(){
        return "[id:" + id + ", a: " + arrivalTime + ", s: " + serviceTime + "]";
    }
}
