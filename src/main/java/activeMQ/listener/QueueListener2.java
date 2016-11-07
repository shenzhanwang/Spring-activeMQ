package activeMQ.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import po.Mail;
public class QueueListener2 {
	@Transactional
	public void displayMail(Mail mail) {
		System.out.println("我是队列消费者2号，我从ActiveMQ队列myqueue中取出一条消息：");
		System.out.println(mail.toString());
		}
}
