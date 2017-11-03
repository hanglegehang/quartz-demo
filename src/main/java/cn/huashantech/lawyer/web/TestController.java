package cn.huashantech.lawyer.web;

import cn.huashantech.lawyer.service.DynamicTask.Main;
import cn.huashantech.lawyer.service.DynamicTask.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lihang
 * @create 2017-11-02 上午12:05
 */
@Controller
public class TestController {

    @Autowired
    private Main main;

    @RequestMapping("list")
    @ResponseBody
    public List<ScheduleJob> list() throws SchedulerException {
        return main.getRunScheduleJobs();
    }

    @RequestMapping("init")
    @ResponseBody
    public String init() {
        main.init();
        return "init success";
    }

    @RequestMapping("stop")
    @ResponseBody
    public String stop() throws SchedulerException {
        main.stop();
        return "stop success";
    }

    @RequestMapping("resume")
    @ResponseBody
    public String resume() throws SchedulerException {
        main.resume();
        return "resume success";
    }
}
