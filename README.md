# xmProject
小米训练营大作业

## 亮点

### 1.采用DDD架构
使用ddd思想和多模块架构，确保项目的可维护性和可拓展性。

### 2.代码亮点：
* mq消费者部分
通过定时任务查询电池数据后，通过mq进行发送，消费者接收到电池信号数据后，反序列化数据存入一个阻塞队列中，当队列中的数据达到设置的批处理数量时，进行批量处理。首先，通过预警模块批量解析电池信号数据。其次，将解析到的预警信息批量插入到数据库中，减少对数据库的IO次数
注意：批量解析数据 是在新建的一个后台线程中，不阻碍消费者线程消费数据。

具体代码位置：com.xiaomi.domain.warn.consumer.BatteryMsgConsumer


* 预警规则解析模块
使用策略工厂模式，获取不同的电池和预警规则的解析实现类，实现了规则的动态解析，并保证后期增加新型号的电池

* 电池信号查询
使用分布式锁保证了数据的一致性，设置数据缓存的时间在一个范围内随机，比如在10-15秒之间随机选择一个值，减小发生缓存雪崩的可能性
