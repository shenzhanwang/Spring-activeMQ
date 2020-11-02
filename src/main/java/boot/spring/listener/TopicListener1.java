package boot.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;

@Component
public class TopicListener1 {
	
	@JmsListener(destination = "mytopic", containerFactory="jmsListenerContainerTopic")
	public void displayTopic(Mail msg) {
		System.out.println("consumer1从ActiveMQ的Topic：mytopic中取出一条消息：");
		System.out.println(msg);
	}
}
