package org.rito.filesystem.interceptor;


import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;

/**
 * @author xigua
 * 自定义拦截器，实现HandlerInterceptor方法
 * 计算请求花费时间
 */
public class InterceptorCalculateRequestTime implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setParameter(request);
        printMemory(request);
        return true;
    }


    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView 如果执行过程中报错了那么在这里计算时间没有意义
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        printMemory(request);
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex       不论是否报错，最后都会走这个方法，时间以这里的为准，在前台页面渲染完之后执行
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        computeConsumeTime(request);
        printMemory(request);

    }

    private void computeConsumeTime(HttpServletRequest request) {
        String requestId = (String) request.getAttribute("requestId");
        long startTime = (long) request.getAttribute("requestStartTime");
        System.out.println("请求Id\t" + requestId + "\t共花费" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
    }

    private void printMemory(HttpServletRequest request) {
        long vmFree;
        long vmUse;
        long vmTotal;
        long vmMax;
        int byteToMb = 1024 * 1024;


        //输出虚拟机内存情况
        Runtime runtime = Runtime.getRuntime();
        vmTotal = runtime.totalMemory() / byteToMb;
        vmFree = runtime.freeMemory() / byteToMb;
        vmMax = runtime.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        // 操作系统级内存情况查询
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long physicalFree = operatingSystemMXBean.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = operatingSystemMXBean.getTotalPhysicalMemorySize() / byteToMb;
        long physicalUse = physicalTotal - physicalFree;

        System.out.println("时间" + new Timestamp(System.currentTimeMillis())
                + "请求Id：" + request.getAttribute("requestId") + "\t"
                + "请求地址：" + request.getRequestURI() + "\t"
                + "JVM已用空间" + vmUse + "MB\t"
                + "JVM空闲空间" + vmFree + "MB\t"
                + "JVM总空间" + vmTotal + "MB\t"
                + "JVM可用最大空间" + vmMax + "MB\t"
                + "操作系统物理内存已用的空间为：" + physicalFree + " MB\t"
                + "操作系统物理内存的空闲空间为：" + physicalUse + " MB\t"
                + "操作系统总物理内存：" + physicalTotal + " MB\t");

    }

    /**
     * @param request 设置请求参数
     */
    private void setParameter(HttpServletRequest request) {
        //规则：请求地址+时间戳确定唯一
        long Sc = System.currentTimeMillis();
        String requestId = request.getRequestURL() + String.valueOf(Sc);
        //设置一个请求ID、一个开始时间戳
        request.setAttribute("requestStartTime", Sc);
        request.setAttribute("requestId", requestId);
    }

}
