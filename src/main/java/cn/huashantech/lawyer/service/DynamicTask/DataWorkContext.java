package cn.huashantech.lawyer.service.DynamicTask;

import cn.huashantech.lawyer.service.DynamicTask.ScheduleJob;

import java.util.*;

/**
 * @author lihang
 * @create 2017-11-01 下午11:17
 */
public class DataWorkContext {
    /** 计划任务map */
    private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

    static {
        for (int i = 0; i < 2; i++) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId("10001" + i);
            job.setJobName("data_import" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus("1");
            job.setCronExpression("0/5 * * * * ?");
            job.setDesc("数据导入任务");
            addJob(job);
        }
    }

    /**
     * 添加任务
     * @param scheduleJob
     */
    public static void addJob(ScheduleJob scheduleJob) {
        jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
    }

    public static List<ScheduleJob> getAllJob(){
        Iterator<Map.Entry<String, ScheduleJob>> entries = jobMap.entrySet().iterator();
        List<ScheduleJob> list = new ArrayList<>();
        while (entries.hasNext()) {
            Map.Entry<String, ScheduleJob> entry = entries.next();
            list.add(entry.getValue());
        }
        return list;
    }



}
