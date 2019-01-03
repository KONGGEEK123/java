package com.example.demo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;
import sun.rmi.runtime.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@RestController
@RequestMapping("task")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void timer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8); // 控制时
        calendar.set(Calendar.MINUTE, 40);    // 控制分
        calendar.set(Calendar.SECOND, 0);    // 控制秒

        Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    System.out.println("-------设定要指定任务--------");
                    timingTaskMethod();
                 }
//            }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
            }, time, 1000 * 60);// 这里设定将延时每天固定执行
        }
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=2940cc34e76eb626ddb2d717758ea10731311933ecaf5c21d39bbe53895b9931";
    public void timingTaskMethod() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"今日关键词：福字\"}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        try {
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result= EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public boolean isOpen = false;
    @RequestMapping("sendkeyword")
    public String sendKeyword() {
        System.out.println("2222222");
        if (!isOpen) {
            System.out.println("3333333");
            timer();
            isOpen = true;
            return "定时器已经启动";
        }else {
            System.out.println("4444444");
            return "你已经启动了，请勿重新启动";
        }
    }
}


