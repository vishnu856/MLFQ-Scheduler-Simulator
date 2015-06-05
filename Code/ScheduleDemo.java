import java.util.Random;


public class ScheduleDemo 
{
	static int numProcess;
	Process p;
	Scheduler s;
	Random random=new Random();
	public ScheduleDemo(Scheduler s)
	{
		this.s=s;
	}
	public synchronized void start() throws Exception
	{
		for(int i=0;i<numProcess;++i)
		{
			p=new Process();
			p.setStartTime(System.currentTimeMillis());
			s.addProcess(p);							//Add new process to scheduler.
			System.out.println("New process "+p+" added with burst time:"+p.getBurstTime());
		}	
	}
	public static void main(String[] args) throws Exception
	{
		ScheduleDemo s2;
		int quantum=10;
		int cpuCount=4;
		int num=Integer.parseInt(args[0]);				//Get number of processes.
		ScheduleDemo.numProcess=num;
		s2=new ScheduleDemo(new Scheduler(quantum,cpuCount));
		s2.start();
	}
}
