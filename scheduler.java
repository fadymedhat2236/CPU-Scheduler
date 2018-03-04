package sample;

import java.util.*;

public class scheduler
{

    private List<process> processes,temp;
    private List<node> nodes;
    private double waitingTime,turnAroundTime;




    scheduler(Collection<process> l1)
    {
        processes=new LinkedList<process>();
        temp=new LinkedList<process>();

        for(process p : l1)
        {
            processes.add(p);
            process x=new process(p.getName(),p.getArrivalTime(),p.getBurstTime(),p.getPriority());
            temp.add(x);
        }
    }

    private void schedule(String s)
    {
        if(s=="SJF")
        {
            process temp[]=processes.toArray(new process[processes.size()]);

            for (int i = 0; i < temp.length - 1; i++)
            {
                boolean isSorted = true;
                for (int j = 0; j < temp.length - 1 - i; j++)
                {
                    if ((temp[j].getArrivalTime() > temp[j + 1].getArrivalTime()) ||
                            ( temp[j].getArrivalTime() == temp[j + 1].getArrivalTime() &&
                                    temp[j].getBurstTime() > temp[j + 1].getBurstTime()))
                    {
                        isSorted = false;
                        process p = temp[j];
                        temp[j] = temp[j + 1];
                        temp[j + 1] = p;
                    }
                }
                if (isSorted)
                    break;
            }
            processes=new ArrayList<process>(Arrays.asList(temp));
        }
        else if(s=="priority")
        {
            process temp[]=processes.toArray(new process[processes.size()]);

            for (int i = 0; i < temp.length - 1; i++) {
                boolean isSorted = true;
                for (int j = 0; j < temp.length - 1 - i; j++) {
                    if ((temp[j].getArrivalTime() > temp[j + 1].getArrivalTime()) ||
                            ( temp[j].getArrivalTime() == temp[j + 1].getArrivalTime() &&
                                    temp[j].getPriority() > temp[j + 1].getPriority()))
                    {
                        isSorted = false;
                        process p = temp[j];
                        temp[j] = temp[j + 1];
                        temp[j + 1] = p;
                    }
                }
                if (isSorted)
                    break;
            }
            processes=new ArrayList<process>(Arrays.asList(temp));
        }
        else
        {
            process temp[]=processes.toArray(new process[processes.size()]);

            for (int i = 0; i < temp.length - 1; i++) {
                boolean isSorted = true;
                for (int j = 0; j < temp.length - 1 - i; j++) {
                    if ((temp[j].getArrivalTime() > temp[j + 1].getArrivalTime()))
                    {
                        isSorted = false;
                        process p = temp[j];
                        temp[j] = temp[j + 1];
                        temp[j + 1] = p;
                    }
                }
                if (isSorted)
                    break;
            }
            processes=new ArrayList<process>(Arrays.asList(temp));
        }
    }

    public List<node> FIFOSort()
    {

        schedule("FIFO");

        int currentTime=0;
        nodes= new ArrayList<node>();
        while(!processes.isEmpty())
        {
            process p=processes.get(0);
            node n = new node();
            if(p.getArrivalTime()>currentTime)
            {
                n.setBeginTime(currentTime);
                n.setEndTime(p.getArrivalTime());
                n.setName("nothing");
                currentTime=p.getArrivalTime();
            }
            else
            {
                n.setBeginTime(currentTime);
                n.setEndTime(currentTime+p.getBurstTime());
                n.setName(p.getName());
                processes.remove(0);
                currentTime=currentTime+p.getBurstTime();
            }
            nodes.add(n);
        }

        for(process x:temp)
            processes.add(x);
        return nodes;
    }

    public List <node> SJFNonpreempitive()
    {
        this.schedule("SJF");

        int currentTime=0;
        nodes= new ArrayList<node>();

        while(!processes.isEmpty())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int shortestTime=processes.get(0).getBurstTime();
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        break;
                    }
                    else
                        {
                            if (processes.get(i).getBurstTime() < shortestTime)
                            {
                                shortestTime = processes.get(i).getBurstTime();
                                index = i;
                            }
                        }
                }

                    n.setName(processes.get(index).getName());
                    n.setBeginTime(currentTime);
                    n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                    currentTime=currentTime+processes.get(index).getBurstTime();
                    processes.remove(index);
            }
                nodes.add(n);
            }
        for(process x : temp)
            processes.add(x);

        return nodes;
        }


    public List <node> priorityNonpreempitive()
    {
        this.schedule("priority");

        int currentTime=0;
        nodes= new ArrayList<node>();

        while(!processes.isEmpty())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int shortestPriority=processes.get(0).getPriority();
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        break;
                    }
                    else
                    {
                        if (processes.get(i).getPriority() < shortestPriority)
                        {
                            shortestPriority = processes.get(i).getPriority();
                            index = i;
                        }
                    }
                }

                n.setName(processes.get(index).getName());
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                currentTime=currentTime+processes.get(index).getBurstTime();
                processes.remove(index);
            }
            nodes.add(n);
        }
        for(process x : temp)
            processes.add(x);

        return nodes;
    }


    public List <node> SJFPreempitive()
    {
        this.schedule("SJF");
        int currentTime=0;
        nodes= new ArrayList<node>();


        while(currentTime<processes.get(processes.size()-1).getArrivalTime())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int shortestTime=processes.get(0).getBurstTime();
                int nextIndex=1;
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        nextIndex=i;
                        break;
                    }
                    else
                    {
                        if (processes.get(i).getBurstTime() < shortestTime)
                        {
                            shortestTime = processes.get(i).getBurstTime();
                            index = i;
                        }
                    }
                }

                n.setName(processes.get(index).getName());
                n.setBeginTime(currentTime);


                if(index==processes.size()-1)
                {
                    n.setEndTime(currentTime+processes.get(index).getBurstTime());
                    currentTime=currentTime+processes.get(index).getBurstTime();
                    processes.remove(index);
                }
                else if(currentTime+processes.get(index).getBurstTime()<=processes.get(nextIndex).getArrivalTime())
                {
                    n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                    currentTime=currentTime+processes.get(index).getBurstTime();
                    processes.remove(index);
                }
                else
                {
                     n.setEndTime(processes.get(nextIndex).getArrivalTime());
                     currentTime = processes.get(nextIndex).getArrivalTime();
                     int x = processes.get(index).getBurstTime();
                     processes.get(index).setBurstTime(x - (n.getEndTime() - n.getBeginTime()));
                }


            }
            nodes.add(n);
        }

        while(!processes.isEmpty())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int shortestTime=processes.get(0).getBurstTime();
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        break;
                    }
                    else
                    {
                        if (processes.get(i).getBurstTime() < shortestTime)
                        {
                            shortestTime = processes.get(i).getBurstTime();
                            index = i;
                        }
                    }
                }

                n.setName(processes.get(index).getName());
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                currentTime=currentTime+processes.get(index).getBurstTime();
                processes.remove(index);
            }
            nodes.add(n);
        }



        for(process x : temp)
            processes.add(x);


        for(int i=0;i<nodes.size()-1;i++)
        {
            if(nodes.get(i).getName()==nodes.get(i+1).getName())
            {
                nodes.get(i).setEndTime(nodes.get(i+1).getEndTime());
                nodes.remove(i+1);
                i--;
            }
        }
        return nodes;

    }


    public List <node> priorityPreempitive()
    {
        this.schedule("priority");
        int currentTime=0;
        nodes= new ArrayList<node>();


        while(currentTime<processes.get(processes.size()-1).getArrivalTime())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int highestPriority=processes.get(0).getPriority();
                int nextIndex=1;
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        nextIndex=i;
                        break;
                    }
                    else
                    {
                        if (processes.get(i).getPriority() < highestPriority)
                        {
                            highestPriority = processes.get(i).getPriority();
                            index = i;
                        }
                    }
                }

                n.setName(processes.get(index).getName());
                n.setBeginTime(currentTime);

                if(index==processes.size()-1)
                {
                    n.setEndTime(currentTime+processes.get(index).getBurstTime());
                    currentTime=currentTime+processes.get(index).getBurstTime();
                    processes.remove(index);
                }
                else if(currentTime+processes.get(index).getBurstTime()<=processes.get(nextIndex).getArrivalTime())
                {
                    n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                    currentTime=currentTime+processes.get(index).getBurstTime();
                    processes.remove(index);
                }
                else
                {
                    n.setEndTime(processes.get(nextIndex).getArrivalTime());
                    currentTime = processes.get(nextIndex).getArrivalTime();
                    int x = processes.get(index).getBurstTime();
                    processes.get(index).setBurstTime(x - (n.getEndTime() - n.getBeginTime()));
                }


            }
            nodes.add(n);
        }

        while(!processes.isEmpty())
        {
            node n=new node();
            if(processes.get(0).getArrivalTime()>currentTime)
            {
                n.setName("nothing");
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(0).getArrivalTime());
                currentTime=processes.get(0).getArrivalTime();
            }
            else
            {
                int index=0;int shortestPriority=processes.get(0).getPriority();
                for(int i=1;i<processes.size();i++)
                {
                    if (processes.get(i).getArrivalTime() > currentTime)
                    {
                        break;
                    }
                    else
                    {
                        if (processes.get(i).getPriority() < shortestPriority)
                        {
                            shortestPriority = processes.get(i).getPriority();
                            index = i;
                        }
                    }
                }

                n.setName(processes.get(index).getName());
                n.setBeginTime(currentTime);
                n.setEndTime(processes.get(index).getBurstTime()+currentTime);
                currentTime=currentTime+processes.get(index).getBurstTime();
                processes.remove(index);
            }
            nodes.add(n);
        }


        for(process x : temp)
            processes.add(x);


        for(int i=0;i<nodes.size()-1;i++)
        {
            if(nodes.get(i).getName()==nodes.get(i+1).getName())
            {
                nodes.get(i).setEndTime(nodes.get(i+1).getEndTime());
                nodes.remove(i+1);
                i--;
            }
        }
        return nodes;

    }


    public List<node> roundRobin(int quantum)
    {
        this.schedule("FIFO");
        int currentTime=0;
        nodes= new ArrayList<node>();

        while(!processes.isEmpty())
        {
            node n=new node();
            process p=processes.get(0);
            if(currentTime<p.getArrivalTime())
            {
                n.setBeginTime(currentTime);
                n.setEndTime(p.getArrivalTime());
                n.setName("nothing");
                currentTime=p.getArrivalTime();
            }
            else
            {
                n.setName(p.getName());
                n.setBeginTime(currentTime);
                if(processes.get(0).getBurstTime()<=quantum)
                {
                    currentTime=currentTime+p.getBurstTime();
                    n.setEndTime(currentTime);
                    processes.remove(0);
                }
                else
                {
                    currentTime=currentTime+quantum;
                    n.setEndTime(currentTime);
                    int x=p.getBurstTime();
                    p.setBurstTime(x-quantum);
                    processes.remove(0);
                    processes.add(processes.size(),p);
                }
            }
            nodes.add(n);
        }

        for(process x:temp)
            processes.add(x);

        return nodes;
    }

    public double geAveragetWaitingTime()
    {
        waitingTime=0;
        for(process p: temp)
        {
            for(int i=nodes.size()-1;i>=0;i--)
            {
                if(nodes.get(i).getName()==p.getName())
                {
                    waitingTime=waitingTime+nodes.get(i).getEndTime()-p.getArrivalTime()-p.getBurstTime();
                    break;
                }
            }
        }
        return waitingTime/temp.size();
    }

    public double geAveragetTurnAroundTime()
    {
        turnAroundTime=0;
        for(process p: temp)
        {
            for(int i=nodes.size()-1;i>=0;i--)
            {
                if(nodes.get(i).getName()==p.getName())
                {
                    turnAroundTime=turnAroundTime+nodes.get(i).getEndTime()-p.getArrivalTime();
                    break;
                }
            }
        }
        return turnAroundTime/temp.size();
    }


}
