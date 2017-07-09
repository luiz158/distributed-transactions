package com.example.axonexample.api;

import com.example.axonexample.model.OverdraftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class MoneyTransferRequestListener implements MessageListener {

    @Autowired
    private MoneyTransferAPI moneyTransferAPI;

    @Override
    public void onMessage(Message message) {
        try {
            // Handle received payment request
            if (message instanceof MapMessage) {
                MapMessage map = (MapMessage) message;

                moneyTransferAPI.doTransfer(
                        map.getString("from"), map.getString("to"), map.getInt("amount"));
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (OverdraftException e) {
            // TODO handle overdraft
            e.printStackTrace();
        }
    }
}