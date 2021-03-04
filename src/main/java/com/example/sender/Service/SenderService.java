package com.example.sender.Service;


import com.example.sender.Config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SenderService {

    //消息发送到Exchange的发送确认
    private final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, b, s) -> {
        if(b){
            log.info("Message has been delivered to Exchange");
        }
        else{
            log.error("Message's delivering failed");
        }
    };

    //消息如果没有正确的Routing key使得Exchange可以发送对应消息到Queue的回调函数
    private final RabbitTemplate.ReturnsCallback returnsCallback =
            returnedMessage -> log.info(returnedMessage.getMessage() + " from " + returnedMessage.getExchange() + " which has " + returnedMessage.getRoutingKey() + " has failed");

    private RabbitTemplate amqpTemplate;

    @Autowired
    public SenderService(RabbitTemplate amqpTemplate){
        this.amqpTemplate = amqpTemplate;
        amqpTemplate.setConfirmCallback(confirmCallback);
        amqpTemplate.setReturnsCallback(returnsCallback);
    }

    public void D1_Sender(String string){

        amqpTemplate.convertAndSend(Constant.Direct_exchange, "Direct1", string, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();  //消息的全局唯一ID
                message.getMessageProperties().setMessageId(messageId);  //消息属性中写入消息ID
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);  //设置消息持久化
                return message;
            }
        });
    }

    public void D2_Sender(String string){

        amqpTemplate.convertAndSend(Constant.Direct_exchange,"Direct2", string, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        });
    }

    //错误发送测试
    public void D_dead_Sender(String string){
        amqpTemplate.convertAndSend(Constant.Direct_exchange,"Error", string);
    }

    public void Fanout_Sender(String string){
        amqpTemplate.convertAndSend(Constant.Fanout_exchange,"AAA",string, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        });
    }

    public void Linzhou_Sender(String string){
        amqpTemplate.convertAndSend(Constant.Topic_exchange,"I.Linzhou",string, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        });
    }

    public void Yue_Sender(String string){
        amqpTemplate.convertAndSend(Constant.Topic_exchange,"Yue.I",string,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        });
    }

    public void YL_Sender(String string){
        amqpTemplate.convertAndSend(Constant.Topic_exchange,"Yue.Linzhou",string,new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        });
    }

}
