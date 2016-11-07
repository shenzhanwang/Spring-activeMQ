package activeMQ.listener;

import org.springframework.transaction.annotation.Transactional;

import po.Mail;

public class TopicListener2 {
	@Transactional
	public void displayTopic(Mail mail) {
		System.out.println("我是话题订阅者2号，我从ActiveMQ的Topic：mytopic中取出一条消息：");
		System.out.println(mail.toString());
		}
}
