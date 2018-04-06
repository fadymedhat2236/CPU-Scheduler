package sample;

public class process
{
    private String name;
    private double arrivalTime,burstTime;
    private int priority;

    public process()
    {
        name="";
        arrivalTime=0;
        burstTime=0;
        priority=0;
    }

    public process(String name, double arrivalTime, double burstTime, int priority)
    {
        this.name=name;
        this.arrivalTime=arrivalTime;
        this.burstTime=burstTime;
        this.priority=priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString()
    {
        String s=name+" ";
        s+=Double.toString(arrivalTime)+" ";
        s+=Double.toString(burstTime)+" ";
        s+=Integer.toString(priority);
        return s;
    }
}
