package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import po.Mail;
import service.Producer;
import service.impl.ProducerImpl;
import service.impl.TopicImpl;


@Controller
public class ActiveMQController {
	@Autowired
	ProducerImpl producer;
	@Autowired
	TopicImpl topic;
	@RequestMapping(value="/produce",produces = {"application/json;charset=UTF-8"})
	public String produce(){
		for(int i=0;i<10;i++)
			producer.sendMail(new Mail("1","www",i));
		return "a";
	}
	
	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
	public String topic(){
		for(int i=0;i<10;i++)
			topic.sendMail(new Mail("qqq","zzz",i));
		return "a";
	}
}
