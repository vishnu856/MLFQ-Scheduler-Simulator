
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Vector;

public class Scheduler implements Runnable
{
	//The queues
	private LinkedBlockingQueue<Process> queue1=new LinkedBlockingQueue<Process>();
	private LinkedBlockingQueue<Process> queue2=new LinkedBlockingQueue<Process>();
	private LinkedBlockingQueue<Process> queue3=new LinkedBlockingQueue<Process>();
	
	//Denotes levels
	private Vector<LinkedBlockingQueue<Process>> stages=new Vector<LinkedBlockingQueue<Process>>();
	
	//Process Queue
	private Vector<Process> procQ=new Vector<Process>();
	
	//Time quantum for all queues
	private int quantum;
	
	private Thread[] processors;
	private boolean running=false;
	static int count=0;
	public Scheduler(int q,int cpuCount)
	{
		running=false;
		//Create "processors"
		processors=new Thread[cpuCount];
		for(int i=0;i<cpuCount;++i)
		{
			processors[i]=new Thread(this);
			processors[i].start();
		}
		quantum=q;
		stages.add(queue3);
		stages.add(queue2);
		stages.add(queue1);
		running=true;
	}

	public void addProcess(Process p) throws Exception
	{
		stages.elementAt(0).put(p);
		procQ.add(p);
	}

	public synchronized void run()
	{
		try
		{
			while(true)
			{
				if(running==true)
				{
					step1();
					running=check();
					try
					{
						Thread.sleep(100);
					}
					catch(Exception e)
					{}
				}
			}
		}
		catch(Exception e)
		{}
	}

	public synchronized boolean check()
	{
		Process p;
		//System.out.println("Entering check.");
		for(int i=0;i<=stages.size();--i)
		{
			p=stages.elementAt(i).peek();
			if(p!=null)
				return true;
		}
		return false;
	}

	public long totalTurnAroundTime()
	{
		long total=0;
		for(int i=0;i<procQ.size()-1;++i)
			total+=procQ.elementAt(i).getTurnaroundTime();
		return total;
	}

	public int totalWaitTime()
	{
		int total=0;
		for(int i=0;i<procQ.size()-1;++i)
			total+=procQ.elementAt(i).getwaitTime();
		return total;
	}

	public void step1()
	{
		int q=quantum;
		int usedTime;
		Process p;
		//System.out.println("Entered step");
		for(int i=stages.size()-1;i>=0 ;--i)
		{
			p=stages.get(i).peek();
			if(p==null)
			{
				//System.out.println("Queue "+i+": Empty");
				continue;
			}
			//System.out.println("Queue "+i+": "+p+" Burst time:"+p.getBurstTime());
			if(q<p.getBurstTime())
			{
				usedTime=q;
				p.addBurstTime(-1*usedTime);
				if(i==stages.size()-1)
				{
					if(p.getBurstTime()==0)
					{
						++count;
						p.setTurnaroundTime(p.getwaitTime()+p.getBurstTime());
						System.out.println("No:"+count+" Finished process "+p+" with turn AroundTime:"+p.getTurnaroundTime());
						stages.get(stages.size()-1).poll();
						procQ.remove(p);
					}
				}
				else
				{
					stages.get(i).poll();
					stages.get(i+1).offer(p);
				}
			}
			else
			{
				usedTime=p.getBurstTime();
				p.addBurstTime(-1*usedTime);
				p.setTurnaroundTime(p.getwaitTime()+p.getInitialBurstTime());
				++count;
				System.out.println("No:"+count+" Finished process "+p+" with turn AroundTime:"+p.getTurnaroundTime());
				stages.get(i).poll();
				procQ.remove(p);
				i=stages.size()-1;
			}
			for(Process pr:stages.get(i))
			{
				if(pr!=p)
					pr.addWaitTime(usedTime);
				for(int j=0;j<procQ.size()-1;++j)
				{
					Process p1=procQ.elementAt(j);
					if(p1.getpid()==pr.getpid())
						procQ.set(i,pr);
				}
			}
		}
		//To schedule least-priority queue and to synchronize all processes in lowest priority queue.
		step2();
	}

	public synchronized void step2() 
	{
		int q=quantum;
		int usedTime;
		//System.out.println("Entered step 2");
		for(Process p:stages.elementAt(2))
		{
			while(p.getBurstTime()!=0)
			{
				usedTime=q<p.getBurstTime()?q:p.getBurstTime();
				p.addBurstTime(-1*usedTime);
				if(p.getBurstTime()<0)
					p.setBurstTime(0);
				try
				{
					Thread.sleep(100);
				}
				catch(Exception e)
				{}
			}
		}
	}
	
}
