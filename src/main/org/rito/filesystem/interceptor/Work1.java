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
 */
public class Work1 implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("在请求处理之前调用");
        System.out.println(request.getDateHeader("Date"));

        printMemory(request);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("在请求处理之后调用");
        computeConsumeTime(request);
        printMemory(request);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("在整个请求结束之后调用");
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

        //规则：请求地址+时间戳确定唯一
        long Sc = System.currentTimeMillis();
        String requestId = request.getRequestURL() + String.valueOf(Sc);
        //设置一个请求ID、一个开始时间戳
        request.setAttribute("requestStartTime", Sc);
        request.setAttribute("requestId", requestId);


        //输出虚拟机内存情况
        Runtime runtime = Runtime.getRuntime();
        vmTotal = runtime.totalMemory() / byteToMb;
        vmFree = runtime.freeMemory() / byteToMb;
        vmMax = runtime.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.println("时间" + new Timestamp(System.currentTimeMillis())
                + "请求地址：" + request.getRequestURI() + "\t"
                + "JVM已用空间" + vmUse + "MB\t"
                + "JVM空闲空间" + vmFree + "MB\t"
                + "JVM总空间" + vmTotal + "MB\t"
                + "JVM可用最大空间" + vmMax + "MB\t");


        // 操作系统级内存情况查询
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long physicalFree = operatingSystemMXBean.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = operatingSystemMXBean.getTotalPhysicalMemorySize() / byteToMb;
        long physicalUse = physicalTotal - physicalFree;
        System.out.println("操作系统物理内存已用的空间为：" + physicalFree + " MB\t"
                + "操作系统物理内存的空闲空间为：" + physicalUse + " MB\t"
                + "操作系统总物理内存：" + physicalTotal + " MB\t");
    }
}
