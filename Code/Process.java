import java.util.Random;
public class Process {
	int pid;
	int waitTime=0;
	int turnTime=0;
	int user;
	int burstTime=0;
	int IOTime;
	long startTime;
	long endTime;
	int initialBurst;
	Random random=new Random();
	public Process(int bTime,int ioTime)
	{
		burstTime=bTime;
		IOTime=ioTime;
	}
	public Process()
	{
		burstTime=random.nextInt(100);
		initialBurst=burstTime;
		IOTime=random.nextInt(100);
		//pid=Integer.parseUnsignedInt(Integer.toHexString(random.nextInt()));
	}
	public int getInitialBurstTime()
	{
		return initialBurst;
	}
	public long getStartTime()
	{
		return startTime;
	}
	public void setStartTime(long st)
	{
		startTime=st;
	}
	public int getpid()
	{
		return pid;
	}
	public int getwaitTime()
	{
		return waitTime;
	}
	public void addWaitTime(int wait)
	{
		waitTime+=wait;
	}
	public void setWaitTime()
	{
		waitTime=0;
	}
	public void setBurstTime(int btime)
	{
		burstTime=btime;
	}
	public void addBurstTime(int btime)
	{
		burstTime+=btime;
	}
	public int getBurstTime()
	{
		return burstTime;
	}
	public int getUser()
	{
		return user;
	}
	public void setUser(int u)
	{
		user=u;
	}
	public int getTurnaroundTime()
	{
		return turnTime;
	}
	public void setTurnaroundTime(int t)
	{
		turnTime=t;
	}
}
