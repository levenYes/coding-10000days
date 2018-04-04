/**
 * 计算机的世界是从"Hello World!"开始的，
 * 这里我们也沿用惯例，
 * 首先生产者发送一条消息"Hello World!"至RabbitMQ中，
 * 之后由消费者消费。
 */

public class RabbitProducer {
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final Stirng ROUTING_KEY = "routingkey_demo";
    private static final Stirng QUEUE_NAME = "queue_demo";
    private static final Stirng IP_ADDRESS = "192.168.0.2";
    private static final int PORT = 5672;//RabbitMQ服务端默认端口号为5672

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("root123");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个type为direct、持久化的、非自动删除的交换器
        channel.exchangeDeclar(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由器绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送一条持久化的消息
        String message = "Hello World!";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        //关闭资源
        channel.close();
        connection.close();
    }
}

public class RabbitConsumer {
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "192.168.0.2";
    private static final int PORT = 5672;

    public static void main (String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses =new Address[] {
                new Address(IP_ADDRESS, PORT)
        };
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("root123");
        //这里的连接方式与生产者的demo略有不同，注意辨别区别
        Connection connection = factory.newConnection(addresses);//创建连接
        final Channel channel = connection.createChannel();//创建信道
        channel.basicQos(64);//设置客户端最多接收未被ack的消息的个数
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                throws IOException {
                System.out.println("recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        //等待回调函数执行完毕之后，关闭资源
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }
}