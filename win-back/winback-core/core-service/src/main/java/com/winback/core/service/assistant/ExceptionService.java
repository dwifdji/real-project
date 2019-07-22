package com.winback.core.service.assistant;

/**
 * @author xdrodger
 * @Title: ExceptionService
 * @ProjectName winback
 * @Description:
 * @date 2019/3/7 16:26
 */
public interface ExceptionService {

    void processEnqueueException(String queue, String message, String exception);

    void processDequeueException(String queue, String message, String exception);

    void processGlobalException(String message);

    void processTryCatchException(Integer id, Exception e);
}
