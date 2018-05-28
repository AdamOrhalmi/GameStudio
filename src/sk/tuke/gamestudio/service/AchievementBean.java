package sk.tuke.gamestudio.service;


import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(mappedName = "jms/achievementQueue")
public class AchievementBean implements MessageListener {

    @Override
    public void onMessage(Message message){
        if(message instanceof TextMessage){

            try {
                System.out.println(">>>>>>>>>"+((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }


        }

    }


}
