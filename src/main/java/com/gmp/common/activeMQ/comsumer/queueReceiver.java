package com.gmp.common.activeMQ.comsumer;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
@Component
public class queueReceiver implements MessageListener {


    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage)message).getText()+"上线了！");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
