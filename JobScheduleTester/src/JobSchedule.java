import java.util.ArrayList;

/**
 *
 * @author sheff
 */
public class JobSchedule {
    
    private ArrayList<Job> jobs = new ArrayList<>();
    private int minCompletion = 0;
    private boolean newEdge = false;
    
    public Job addJob(int time) {
        Job job = new Job(time);
        jobs.add(job);
        
        if (minCompletion < job.time) minCompletion = job.time;
        
        return job;
    }
    
    public Job getJob(int index) {
        return jobs.get(index);
    }
    
    public int minCompletionTime() {
        
        if (!newEdge) return minCompletion;
        newEdge = false;
        
        ArrayList<Job> temp = new ArrayList<>();
        
        for (Job j : jobs) {
            j.tempInCount = j.inCount;
            j.onLoop = true;
            if (j.tempInCount <= 0) {
                temp.add(j);
                j.onLoop = false;
            }
        }
        
        int count = 0;
        while (!temp.isEmpty()) {
            Job current = temp.remove(0);
            count++;
            for (Job j : current.children) {
                int tempTime = current.startTime + current.time;
                if (j.startTime < tempTime) j.startTime = tempTime;
                tempTime = j.startTime + j.time;
                if (minCompletion < tempTime) minCompletion = tempTime;
                j.tempInCount--;
                if (j.tempInCount == 0) {
                    temp.add(j);
                    j.onLoop = false;
                }
            }
        }
        
        if (jobs.size() != count) {
            minCompletion = -1;
        }
        
        return minCompletion;
    }
    
    public class Job {
        String name;
        int time;
        int startTime = 0;
        int inCount = 0;
        boolean onLoop = false;
        ArrayList<Job> prereqs = new ArrayList<>();
        
        ArrayList<Job> children = new ArrayList<>();
        private int tempInCount = 0;
        
        /**
         * Constructs new job with the number and time
         * @param time
         */
        private Job(int time) {
            this.time = time;
        }

        
        public void requires(Job j) {
            this.prereqs.add(j);
            j.children.add(this);
            inCount++;
            newEdge = true;
        }
        
        public int getStartTime() {
            if(newEdge) minCompletionTime();
            if (onLoop) return -1;
            return startTime;
        }

    }
}
