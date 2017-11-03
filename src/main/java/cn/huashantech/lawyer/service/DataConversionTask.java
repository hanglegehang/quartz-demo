package cn.huashantech.lawyer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lihang
 * @create 2017-11-01 下午11:15
 */
public class DataConversionTask {
    /**
     * 日志对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(DataConversionTask.class);

    public void run() {
        LOG.info("数据转换任务线程开始执行");
    }
}