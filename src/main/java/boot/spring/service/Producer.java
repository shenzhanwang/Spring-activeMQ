package boot.spring.service;

import javax.jms.Destination;

import boot.spring.po.Mail;

public interface Producer {
	public void sendMail(Destination des, Mail mail);
}
