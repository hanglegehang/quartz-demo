import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lihang
 * @create 2017-11-02 上午1:16
 */
public class pickNewsJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("在" + sdf.format(new Date()) + "扒取新闻");
    }

    public static void main(String args[]) throws SchedulerException {
        m1();
    }

    public static void m1() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(pickNewsJob.class)
                .withIdentity("job1", "jgroup1").build();

        SimpleTrigger simpleTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3, 2))
                .startNow()
                .build();

        //创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
    }

    public static void m2() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // ①获取调度器中所有的触发器组
        List<String> triggerGroups = scheduler.getTriggerGroupNames();
        // ②重新恢复在tgroup1组中，名为trigger1触发器的运行
        for (int i = 0; i < triggerGroups.size(); i++) {
            //这里使用了两次遍历，针对每一组触发器里的每一个触发器名，和每一个触发组名进行逐次匹配
            List<String> triggers = scheduler.getTriggerGroupNames();
            for (int j = 0; j < triggers.size(); j++) {
                System.out.println(triggers.get(j) + "" + triggerGroups.get(i));
                Trigger tg = scheduler.getTrigger(new TriggerKey(triggers
                        .get(j), triggerGroups.get(i)));
                // ②-1:根据名称判断
                if (tg instanceof SimpleTrigger && tg.getDescription().equals("jgroup1.DEFAULT")) {
                    //由于我们之前测试没有设置触发器所在组，所以默认为DEFAULT
                    // ②-1:恢复运行
                    scheduler.resumeJob(new JobKey(triggers.get(j),
                            triggerGroups.get(i)));
                }
            }
        }
        scheduler.start();
    }
}

