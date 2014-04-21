/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sheff
 */
public class JobScheduleTest {
    
    public JobScheduleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void correctMinTime() {
        //Andrews Test Graph
        JobSchedule as = new JobSchedule();
        
        
        
        JobSchedule.Job d = as.addJob(6); //d 1
        JobSchedule.Job e = as.addJob(7); //e 2
        JobSchedule.Job f = as.addJob(4); //f 3
        
        JobSchedule.Job c = as.addJob(5); //c 4
        
        
        
        JobSchedule.Job b = as.addJob(3); //b 5
        JobSchedule.Job a = as.addJob(2); //a 6
        
        JobSchedule.Job g = as.addJob(8); //G 0

        g.requires(d);
        g.requires(e);
        g.requires(f);
        
        d.requires(b);
        d.requires(e);
        
        e.requires(b);
        
        f.requires(c);
        
        c.requires(a);
        c.requires(b);
        
        b.requires(a);
        
        assertEquals(26, as.minCompletionTime());
        assertEquals(6, as.getJob(0).time);
    }
    
    @Test
    public void loopTest() {
        //Andrews Test Graph
        JobSchedule as = new JobSchedule();
        
        
        
        JobSchedule.Job d = as.addJob(6); //d 1
        JobSchedule.Job e = as.addJob(7); //e 2
        JobSchedule.Job f = as.addJob(4); //f 3
        
        JobSchedule.Job c = as.addJob(5); //c 4
        
        
        
        JobSchedule.Job b = as.addJob(3); //b 5
        JobSchedule.Job a = as.addJob(2); //a 6
        
        JobSchedule.Job g = as.addJob(8); //G 0

        g.requires(d);
        d.requires(g);
        g.requires(e);
        g.requires(f);
        
        d.requires(b);
        d.requires(e);
        
        e.requires(b);
        
        f.requires(c);
        
        c.requires(a);
        c.requires(b);
        
        b.requires(a);
        
        assertEquals(-1, as.minCompletionTime());
    }
    
    @Test
    public void testStartTimeMethod() {
        JobSchedule js =  new JobSchedule();
        
        JobSchedule.Job a = js.addJob(6);
        JobSchedule.Job b = js.addJob(6);
        
        b.requires(a);
        
        assertEquals(0, a.getStartTime());
        assertEquals(6, b.getStartTime());
    }
    
    @Test
    public void professorsTest() {
        JobSchedule schedule = new JobSchedule();
        schedule.addJob(8); //adds job 0 with time 8
        JobSchedule.Job j1 = schedule.addJob(3); //adds job 1 with time 3
        schedule.addJob(5); //adds job 2 with time 5
        assertEquals(8, schedule.minCompletionTime()); //should return 8, since job 0 takes time 8 to complete.
        /* Note it is not the min completion time of any job, but the earliest the entire set can complete. */
        schedule.getJob(0).requires(schedule.getJob(2)); //job 2 must precede job 0
        assertEquals(13, schedule.minCompletionTime()); //should return 13 (job 0 cannot start until time 5)
        schedule.getJob(0).requires(j1); //job 1 must precede job 0
        assertEquals(13, schedule.minCompletionTime()); //should return 13
        assertEquals(5, schedule.getJob(0).getStartTime()); //should return 5
        assertEquals(0, j1.getStartTime()); //should return 0
        assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0
        j1.requires(schedule.getJob(2)); //job 2 must precede job 1
        assertEquals(16, schedule.minCompletionTime()); //should return 16
        assertEquals(8, schedule.getJob(0).getStartTime()); //should return 8
        assertEquals(5, schedule.getJob(1).getStartTime()); //should return 5
        assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0
        schedule.getJob(1).requires(schedule.getJob(0)); //job 0 must precede job 1 (creates loop)
        assertEquals(-1, schedule.minCompletionTime()); //should return -1
        assertEquals(-1, schedule.getJob(0).getStartTime()); //should return -1
        assertEquals(-1, schedule.getJob(1).getStartTime()); //should return -1
        assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0 (no loops in prerequisites)
    }
}