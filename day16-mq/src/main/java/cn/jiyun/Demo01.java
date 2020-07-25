package cn.jiyun;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class Demo01 {

	// 点对点模式，生产者 消费者
	// 软件端口号：8161
	// 客户端端口号：61616
	// redis：6379
	// mysql：3306
	// tomcat默认：8080
	// http默认：80
	
	@Test
	public void producer() throws Exception {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

		// 通过连接工厂创建连接
		Connection connection = connectionFactory.createConnection();

		// 启动连接
		connection.start();

		// 创建session，参数1：是否启动事务，参数2：消息确认模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 创建消息队列  -- 点对点消费模式
		Queue queue = session.createQueue("h1903c-01");// 参数是队列的名字

		// 创建消息生产者
		MessageProducer producer = session.createProducer(queue);// 参数是队列，也就是生产的消息放在这个队列中

		// 创建消息
		TextMessage textMessage = session.createTextMessage("我是钻石王老五！!!!");

		// 发送消息
		producer.send(textMessage);

		// 关闭资源
		producer.close();
		session.close();
		connection.close();

	}
	
	/**
	 * 消息消费者
	 * @throws Exception 
	 */
	@Test
	public void consumer() throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		//创建连接
		Connection connection = connectionFactory.createConnection();
		
		//启动连接
		connection.start();
		
		//创建session，参数1：是否启动事务，参数2：消息确认模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建消息队列
		Queue queue = session.createQueue("h1903c-01");//和要接收的消息队列一样
		
		//创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		
		//监听消息--被java后台，持续执行，知道程序结束为止
		consumer.setMessageListener(new MessageListener() {
			
			// 当监听到有消息进入queue，这个方法就会被consumer对象调用
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("消费者接收到的消息是：" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
        //等待键盘输入，如果没有这句话是接收不到消息的
		System.in.read();
		
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
	/**
	 * 发布订阅生产者
	 * @throws Exception 
	 */
	@Test
	public void producer1() throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		//创建连接
		Connection connection = connectionFactory.createConnection();
		
		//启动连接
		connection.start();
		
		//创建session，参数1：是否启动事务，参数2：消息确认模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建主题对象 ，发布订阅模式的容器
		Topic topic = session.createTopic("h1903c-02");
		
		//创建消息生产者
		MessageProducer producer = session.createProducer(topic);
		
		//创建消息
		TextMessage textMessage = session.createTextMessage("hello activemq!!! 主题订阅！");
		
		//发送消息
		producer.send(textMessage);
		
		//关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	/**
	 * 消费者
	 * @throws Exception 
	 */
	@Test
	public void consumer2() throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		
		//创建连接
		Connection connection = connectionFactory.createConnection();
		
		//启动连接
		connection.start();
		
		//获取session对象，参数1：是否启动事务，参数2：消息确认模式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建主题对象
		Topic topic = session.createTopic("h1903c-02");
		
		//创建消费者
		MessageConsumer consumer = session.createConsumer(topic);
		
		//监听消息
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("消费者接收到的消息是：" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		//等待键盘输入
		System.in.read();
		
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

}
