package com.example.sender.Config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {
    @Bean(Constant.Queue1)
    public Queue Queue_1(){
        return QueueBuilder.durable(Constant.Queue1).deadLetterExchange(Constant.Dead_exchange).deadLetterRoutingKey("dlk").build();
    }

    @Bean(Constant.Queue2)
    public Queue Queue_2(){
        return QueueBuilder.durable(Constant.Queue2).deadLetterExchange(Constant.Dead_exchange).deadLetterRoutingKey("dlk").build();
    }

    @Bean(Constant.Queue3)
    public Queue Queue_3(){
        return QueueBuilder.durable(Constant.Queue3).deadLetterExchange(Constant.Dead_exchange).deadLetterRoutingKey("dlk").build();
    }

    @Bean(Constant.Queue4)
    public Queue Queue_4(){
        return QueueBuilder.durable(Constant.Queue4).deadLetterExchange(Constant.Dead_exchange).deadLetterRoutingKey("dlk").build();
    }

    //数据全部开启持久化，防止数据丢失
    @Bean(Constant.Fanout_exchange)
    public Exchange get_fanout(){
        return ExchangeBuilder.fanoutExchange(Constant.Fanout_exchange).durable(true).build();
    }

    @Bean(Constant.Direct_exchange)
    public Exchange get_direct(){
        return ExchangeBuilder.directExchange(Constant.Direct_exchange).durable(true).build();
    }

    @Bean(Constant.Topic_exchange)
    public Exchange get_topic(){
        return ExchangeBuilder.topicExchange(Constant.Topic_exchange).durable(true).build();
    }


    @Bean
    public Binding D_binding1(@Qualifier(Constant.Queue1) Queue queue, @Qualifier(Constant.Direct_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("Direct1").noargs();
    }

    @Bean
    public Binding D_binding2(@Qualifier(Constant.Queue2) Queue queue, @Qualifier(Constant.Direct_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("Direct2").noargs();
    }

    @Bean
    public Binding T_binding1(@Qualifier(Constant.Queue1) Queue queue, @Qualifier(Constant.Topic_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("#.Linzhou").noargs();

    }

    @Bean
    public Binding T_binding2(@Qualifier(Constant.Queue2) Queue queue, @Qualifier(Constant.Topic_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("Yue.#").noargs();

    }

    @Bean
    public Binding T_binding3(@Qualifier(Constant.Queue3) Queue queue, @Qualifier(Constant.Topic_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("#.#").noargs();
    }

    @Bean
    public Binding F_binding1(@Qualifier(Constant.Queue1) Queue queue, @Qualifier(Constant.Fanout_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding F_binding2(@Qualifier(Constant.Queue2) Queue queue, @Qualifier(Constant.Fanout_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding F_binding3(@Qualifier(Constant.Queue3) Queue queue, @Qualifier(Constant.Fanout_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }

    @Bean
    public Binding F_binding4(@Qualifier(Constant.Queue4) Queue queue, @Qualifier(Constant.Fanout_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }


    @Bean(Constant.Retry_Exchange)              //设置重试交换机和队列
    public Exchange Retry_Exchange(){
        return ExchangeBuilder.directExchange(Constant.Retry_Exchange).durable(true).build();
    }

    @Bean(Constant.Retry_Queue)
    public Queue Retry_Queue(){
        return QueueBuilder.durable(Constant.Retry_Queue).build();
    }

    @Bean
    public Binding Retry_binding(@Qualifier(Constant.Retry_Queue) Queue queue, @Qualifier(Constant.Retry_Exchange) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("retry").noargs();
    }


    @Bean(Constant.Dead_exchange) //设置死信交换机
    public Exchange Dead_Exchange(){
        return ExchangeBuilder.directExchange(Constant.Dead_exchange).durable(true).build();
    }

    @Bean(Constant.Dead_queue)//设置死信队列
    public Queue Dead_Queue(){
        return QueueBuilder.durable(Constant.Dead_queue).build();
    }

    @Bean
    public Binding bindingDead(@Qualifier(Constant.Dead_queue) Queue queue, @Qualifier(Constant.Dead_exchange) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("dlk").noargs();
    }

}
