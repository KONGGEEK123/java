package com.example.demo;

import net.minidev.json.JSONObject;
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

import java.lang.reflect.Array;
import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("task")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public void timer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20); // 控制时
        calendar.set(Calendar.MINUTE, 0);    // 控制分
        calendar.set(Calendar.SECOND, 0);    // 控制秒

        Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    System.out.println("-------设定要指定任务--------");
                    timingTaskMethod();
                 }
            }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
//            }, time, 1000 * 60);// 这里设定将延时每天固定执行
        }
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=393688554aa42eac442d84f8adb723bd4b6c01aa778ae4f3c11a457122572247";
//    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=2940cc34e76eb626ddb2d717758ea10731311933ecaf5c21d39bbe53895b9931";
    public void timingTaskMethod() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        Map map = new HashMap();
        map.put("msgtype","text");

        Map map1 = new HashMap();
        map1.put("content",keyword());

        map.put("text",map1);

        JSONObject json =new JSONObject(map);
        String textMsg = json.toJSONString();
        System.out.println(textMsg);
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

    public String keyword() {
        String[] strings = {
                "零食柜","动感单车","豫姐的皇冠","公司专利墙","历代眼镜展柜",
                "视氪logo","路由器","打印机","任意一台加湿器",
                "产品运营部部门牌", "市场部部门牌", "人力行政部部门牌", "智能硬件组部门牌",
                "移动互联网事业组部门牌", "日历", "小柔的QQ企鹅玩偶", "云霞的电热水壶",
                "要么出众要么出局字帖", "创新字帖", "愿景字帖", "使命字帖", "仙人球",
                "铃铛", "加湿器", "剪刀", "尺子", "同时按下KR字母的键盘", "在logo前比爱心",
                "圣诞树上的星星", "投影幕布"
        };
        int i = (int) (Math.random() * strings.length - 1);
        String string = strings[i];
        string = "今日关键词：" + string + "!";
        return string;
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


