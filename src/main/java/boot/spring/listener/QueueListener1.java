package boot.spring.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;

@Component
public class QueueListener1 {
	
	@JmsListener(destination = "myqueue", containerFactory = "jmsListenerContainerQueue")
	public void displayMail(Mail mail) {
		System.out.println("listen1从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail.toString());
	}
}
