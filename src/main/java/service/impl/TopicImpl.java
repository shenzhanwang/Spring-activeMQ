package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import po.Mail;
import service.Producer;
@Transactional
@Service("topic")
public class TopicImpl implements Producer{
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTopicTemplate = jmsTemplate;
	}
	public void sendMail(Mail mail) {
		jmsTopicTemplate.convertAndSend(mail);
	}

}
