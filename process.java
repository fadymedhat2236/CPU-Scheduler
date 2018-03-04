package sample;

public class process
{
    private String name;
    private int arrivalTime,burstTime,priority;

    public process()
    {
        name="";
        arrivalTime=0;
        burstTime=0;
        priority=0;
    }

    public process(String name, int arrivalTime, int burstTime, int priority)
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

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
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
        s+=Integer.toString(arrivalTime)+" ";
        s+=Integer.toString(burstTime)+" ";
        s+=Integer.toString(priority);
        return s;
    }
}
