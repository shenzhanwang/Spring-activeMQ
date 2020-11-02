package boot.spring.service.impl;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.po.Mail;
import boot.spring.service.Producer;

@Service("producer")
public class ProducerImpl implements Producer{
	
	@Autowired
    public JmsMessagingTemplate jmsMessagingTemplate;
	

	@Override
	public void sendMail(Destination des, Mail mail) {
		jmsMessagingTemplate.convertAndSend(des, mail);
	}
	

}
