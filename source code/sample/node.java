package sample;

public class node
{
    private String name;
    private double beginTime,endTime;

    public node()
    {
        name="";
        beginTime=endTime=0;
    }
    public node(String name,double beginTime,double endTime)
    {
        this.name=name;
        this.beginTime=beginTime;
        this.endTime=endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(double beginTime) {
        this.beginTime = beginTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        String s=Double.toString(beginTime);
        s+=":";
        s+=name;
        s+=":";
        s+=Double.toString(endTime);
        return s;
    }
}
