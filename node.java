package sample;

public class node
{
    private String name;
    private int beginTime,endTime;

    public node()
    {
        name="";
        beginTime=endTime=0;
    }
    public node(String name,int beginTime,int endTime)
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

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        String s=Integer.toString(beginTime);
        s+=":";
        s+=name;
        s+=":";
        s+=Integer.toString(endTime);
        return s;

    }
}
