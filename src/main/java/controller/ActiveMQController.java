package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public void produce(){
		for(int i=0;i<10;i++)
			producer.sendMail(new Mail("生产者","www",i));
	}
	
	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void topic(){
		for(int i=0;i<10;i++)
			topic.sendMail(new Mail("发布话题","zzz",i));
	}
	
	@RequestMapping("demo")
	public String demo(){
		return "demo";
	}
}
