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
        //耗时
        //最大内存
        //已分配内存
        //已分配内存中最大可用空间
        //最大可用内存
        printMemory(request);
        //不拦截true，拦截false
        return true;
    }

    private void printMemory(HttpServletRequest request) {
        long vmFree;
        long vmUse;
        long vmTotal;
        long vmMax;
        int byteToMb = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();
        vmTotal = runtime.totalMemory() / byteToMb;
        vmFree = runtime.freeMemory() / byteToMb;
        vmMax = runtime.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.print("时间" + new Timestamp(System.currentTimeMillis()));
        System.out.print("请求地址：" + request.getRequestURI() + "\t");
        System.out.print("JVM已用空间" + vmUse + "MB\t");
        System.out.print("JVM空闲空间" + vmFree + "MB\t");
        System.out.print("JVM总空间" + vmTotal + "MB\t");
        System.out.println("JVM可用最大空间" + vmMax + "MB\t");


        // 操作系统级内存情况查询
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String os = System.getProperty("os.name");
        long physicalFree = osmxb.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = osmxb.getTotalPhysicalMemorySize() / byteToMb;
        long physicalUse = physicalTotal - physicalFree;
        System.out.print("操作系统的版本：" + os + "\t");
        System.out.print("操作系统物理内存已用的空间为：" + physicalFree + " MB\t");
        System.out.print("操作系统物理内存的空闲空间为：" + physicalUse + " MB\t");
        System.out.println("操作系统总物理内存：" + physicalTotal + " MB\t");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("在请求处理之后调用");
        printMemory(request);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("在整个请求结束之后调用");
        printMemory(request);

    }
}
