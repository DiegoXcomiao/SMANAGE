package org.manage.common.utils;

import org.manage.model.Message;
import org.manage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;import java.util.concurrent.Executors;

public class MyExecutor {

    ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    MessageService messageService;

    public void run1(Message message){
        executor.submit(()->{

            System.out.println("正在执行业务，请稍等");
            try {
                Thread.sleep(1000 * 60 * 5);
                //TODO 执行具体的业务逻辑
                String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
                System.out.println(verifyCode);
                Message message2 = new Message();
                message2.setUserCode(message.getUserCode());
                message2.setVerifyCode(verifyCode);
                message2.setMessage(message.getMessage());
                message2.setTelno(message.getTelno());
                message2.setMessageId(message.getMessageId());
                this.messageService = ApplicationContextProvider.getBean(MessageService.class);
                List<Message> message1 = messageService.getMessage(message2);
                if (message1.size() == 0) {
                    messageService.addMessage(message2);
                } else {
                    messageService.updateMessage(message2);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("业务执行完成");
        });
    }

    public void run2(Message message){
        executor.submit(()->{
            System.out.println("正在执行业务，请稍等");
            try {
                Thread.sleep(1000);
                //TODO 执行具体的业务逻辑
                String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
                System.out.println(verifyCode);
                Message message2 = new Message();
                message2.setUserCode(message.getUserCode());
                message2.setVerifyCode(verifyCode);
                message2.setMessage(message.getMessage());
                message2.setTelno(message.getTelno());
                message2.setMessageId(message.getMessageId());
                this.messageService = ApplicationContextProvider.getBean(MessageService.class);
                List<Message> message1 = messageService.getMessage(message2);
                if (message1.size() == 0) {
                    messageService.addMessage(message2);
                } else {
                    messageService.updateMessage(message2);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("业务执行完成");
        });
    }
}
