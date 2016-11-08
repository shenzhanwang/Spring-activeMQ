package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public void produce(@ModelAttribute("mail")Mail mail){
			System.out.println("向队列myquene添加一条消息:"+mail.toString());
			producer.sendMail(mail);
	}
	
	@RequestMapping(value="/topic",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public void topic(@ModelAttribute("mail")Mail mail){
		System.out.println("向话题mytopic发布一条消息:"+mail.toString());
		topic.sendMail(mail);
	}
	
	@RequestMapping("demo")
	public String demo(){
		return "demo";
	}
}
