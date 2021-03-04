package com.example.sender.Controller;


import com.example.sender.Service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendController {
    @Autowired
    private SenderService senderService;

    @RequestMapping("/direct1_send")
    public String D1_send(){
        senderService.D1_Sender("D1_Send");
        //for(int i = 0;i<100000000;i++){System.out.println(1);}     //test 单实例多线程
        return "Direct1 Send Success";
    }

    @RequestMapping("/direct2_send")
    public String D2_send(){
        senderService.D2_Sender("D2_Send");
        return "Direct2 Send Success";
    }

    @RequestMapping("/fanout_send")
    public String F_send(){
        senderService.Fanout_Sender("Fanout_Send");
        return "Fanout send Success";
    }

    @RequestMapping("/topic1_send")
    public String T1_Send(){
        senderService.Linzhou_Sender("Topic1_Send");
        return "Topic1 send Success";
    }

    @RequestMapping("/topic2_send")
    public String T2_Send(){
        senderService.Yue_Sender("Topic2_Send");
        return "Topic2 send Success";
    }

    @RequestMapping("/topic3_send")
    public String T3_Send(){
        senderService.YL_Sender("Topic3_Send");
        return "Topic3 send Success";
    }

    @RequestMapping("/Error_send")
    public String Error_Send(){
        senderService.D_dead_Sender("Dead_Send");
        return "Dead_Message send Success";
    }
}
