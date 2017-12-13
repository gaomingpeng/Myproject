package com.gmp.common.activeMQ.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
@Component
public class TopicSender {
  @Autowired
  @Qualifier("jmsTopicTemplate")//通过@Qualifier修饰符来注入对应的bean
    private JmsTemplate jmsTemplate;


    public  void send(String topicName,String message){
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        };
        jmsTemplate.send(topicName,messageCreator);
    }
}
