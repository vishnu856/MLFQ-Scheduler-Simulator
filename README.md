# MLFQ-Scheduler-Simulator
This scheduler is used to schedule "pretend" processes, which have been created to represent the most basic form of any process.
It contains attributes such as starting time, waiting time and turn around time, specific to each process. 
The MLFQ utilizes multiple queues and allows processes to move between queues dynamically. In this approach, processes "find their own level" based on their CPU burst . Processes that have a larger CPU burst moves to a lower-priority queue.
Each of the processes in their own queues are scheduled in Round-Robin(RR) fashion.
This method allows you to test the efficiency of the MLFQ scheduler with different quanta values and number of queues. 
The number of processors can also be altered to suit your needs.

References:
[1] Hoganson K., Reducing MLFQ Scheduling Starvation with Feedback and Exponential Averaging, The Journal of Computing Sciences in Colleges, 25, (2), 196- 202, 2009.
