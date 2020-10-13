## Mqtt协议原理
是一种基于发布/订阅，建于TCP/IP协议，属于应用层协议。
## 优点
可以以极少的代码和有限的带宽，为连接远程设备提供实时可靠的消息服务。作为一种低开销、低带宽占用的即时通讯协议，使其在物联网、小型设备、移动应用等方面有较广泛的应用。（头部只占2字节）
## 参数说明
~~~java
//tcp://MQTT安装的服务器地址:MQTT定义的端口号，默认端口号均为1883
    public static final String HOST = "tcp://192.168.1.102:1883";
    //定义一个主题，客户端通过topic 进行发布和订阅
    public static final String TOPIC = "mtopic";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server11";

    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "stonegeek";   //账号
    private String passWord = "123456";      //密码
~~~

## jar包依赖
```xml
 <!--mqtt-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-integration</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-stream</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-mqtt</artifactId>
        </dependency>
```

## mqtt服务端
~~~java
package bsit.mqtt.demo.one_way;  

import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;  

import org.eclipse.paho.client.mqttv3.MqttClient;  
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;  
import org.eclipse.paho.client.mqttv3.MqttException;  
import org.eclipse.paho.client.mqttv3.MqttSecurityException;  
import org.eclipse.paho.client.mqttv3.MqttTopic;  
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Client {  

    public static final String HOST = "tcp://192.168.1.3:61613";  
    public static final String TOPIC = "toclient/124";  
    private static final String clientid = "client124";  
    private MqttClient client;  
    private MqttConnectOptions options;  
    private String userName = "admin";
    private String passWord = "password";

    private ScheduledExecutorService scheduler;  

    private void start() {  
        try {  
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存  
            client = new MqttClient(HOST, clientid, new MemoryPersistence());  
            // MQTT的连接设置  
            options = new MqttConnectOptions();  
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接  
            options.setCleanSession(true);  
            // 设置连接的用户名  
            options.setUserName(userName);  
            // 设置连接的密码  
            options.setPassword(passWord.toCharArray());  
            // 设置超时时间 单位为秒  
            options.setConnectionTimeout(10);  
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制  
            options.setKeepAliveInterval(20);  
            // 设置回调  
            client.setCallback(new PushCallback());  
            MqttTopic topic = client.getTopic(TOPIC);  
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息    
            options.setWill(topic, "close".getBytes(), 2, true);  

            client.connect(options);  
            //订阅消息  
            int[] Qos  = {1};  
            String[] topic1 = {TOPIC};  
            client.subscribe(topic1, Qos);  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    public static void main(String[] args) throws MqttException {     
        Client client = new Client();  
        client.start();  
    }  
}
~~~
## 客户端
~~~java
package bsit.mqtt.demo.one_way;  

import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;  

import org.eclipse.paho.client.mqttv3.MqttClient;  
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;  
import org.eclipse.paho.client.mqttv3.MqttException;  
import org.eclipse.paho.client.mqttv3.MqttSecurityException;  
import org.eclipse.paho.client.mqttv3.MqttTopic;  
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Client {  

    public static final String HOST = "tcp://192.168.1.3:61613";  
    public static final String TOPIC = "toclient/124";  
    private static final String clientid = "client124";  
    private MqttClient client;  
    private MqttConnectOptions options;  
    private String userName = "admin";
    private String passWord = "password";

    private ScheduledExecutorService scheduler;  

    private void start() {  
        try {  
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存  
            client = new MqttClient(HOST, clientid, new MemoryPersistence());  
            // MQTT的连接设置  
            options = new MqttConnectOptions();  
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接  
            options.setCleanSession(true);  
            // 设置连接的用户名  
            options.setUserName(userName);  
            // 设置连接的密码  
            options.setPassword(passWord.toCharArray());  
            // 设置超时时间 单位为秒  
            options.setConnectionTimeout(10);  
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制  
            options.setKeepAliveInterval(20);  
            // 设置回调  
            client.setCallback(new PushCallback());  
            MqttTopic topic = client.getTopic(TOPIC);  
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息    
            options.setWill(topic, "close".getBytes(), 2, true);  

            client.connect(options);  
            //订阅消息  
            int[] Qos  = {1};  
            String[] topic1 = {TOPIC};  
            client.subscribe(topic1, Qos);  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    public static void main(String[] args) throws MqttException {     
        Client client = new Client();  
        client.start();  
    }  
}

回调函数
package bsit.mqtt.demo.one_way;  

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;  
import org.eclipse.paho.client.mqttv3.MqttMessage;  

/**  
 * 发布消息的回调类  
 *   
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。  
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。  
 * 必须在回调类中实现三个方法：  
 *   
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。  
 *   
 *  public void connectionLost(Throwable cause)在断开连接时调用。  
 *   
 *  public void deliveryComplete(MqttDeliveryToken token))  
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。  
 *  由 MqttClient.connect 激活此回调。  
 *   
 */    
public class PushCallback implements MqttCallback {  

    public void connectionLost(Throwable cause) {  
        // 连接丢失后，一般在这里面进行重连  
        System.out.println("连接断开，可以做重连");  
    }  

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());  
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面  
        System.out.println("接收消息主题 : " + topic);  
        System.out.println("接收消息Qos : " + message.getQos());  
        System.out.println("接收消息内容 : " + new String(message.getPayload()));  
    }  
}
~~~

## docker拉取emqx
```xml
docker pull emqx/emqx
```
## docker启动emqx
```xml
docker run --rm -d --name emqx -p 18083:18083 -p 1883:1883 emqx/emqx:latest
```
