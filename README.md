# learn-netty-with-kotlin

course info: https://www.bilibili.com/video/BV1Xz4y1X7m1

### 包说明
- org.adolf.netty.firstexample: netty实现HTTP编程
- org.adolf.netty.secondexample: netty实现socket编程
- org.adolf.netty.thirdexample: netty实现的聊天程序
- org.adolf.netty.fourthexample: netty实现心跳检测
- org.adolf.netty.fifthexample: netty实现websocket编程
- org.adolf.netty.sixthexample: netty使用protobuf协议实现通信
- org.adolf.nio： nio相关的类的使用，如Buffer、Channel、Selector、堆外内存等
- org.adolf.zerocopy： 使用nio实现零拷贝的例子

### 源码学习记录

https://www.notion.so/Netty-b6c166bf63ab4b87b8b6a0cbaccf80bb

详细流程参见上述地址，以下部分为简要记录每天的心得：
#### 2020.07.07
- 无参的NioEventLoopGroup()方法默认使用CPU逻辑核数×2的个数的线程，所以实践中一般指定为1
- NioEventLoopGroup()方法默认使用ThreadPerTaskExecutor执行器，其execute方法的行为是调用默认线程工厂获取一个新线程然后执行任务
- 默认线程工厂每次调用newThread方法都会创建新的线程，所以实际使用中应该避免使用这个线程工厂

--- 
#### 2020.07.08
跟踪了NioEventLoopGroup无参的构造过程，简要总结如下：

1. 由于没有指定线程个数，且没有指定系统参数`io.netty.eventLoopThreads`，因此默认线程数为CPU逻辑核数*2*倍，最小不能低于*1*;
2. 使用JDK提供的`SelectorProvider.provider()`方法，获取当前环境可用的**多路复用选择器**;
3. 默认的拒绝策略是直接抛异常;
4. 默认情况下`NioEventLoopGroup`中的事件执行器`NioEventLoop`集合，采用的是**轮流**算法，即轮流执行;
5. 默认情况下`NioEventLoop`中的`taskQueue`队列，`tailTaskQueue`队列和`maxPendingTasks`队列，最大长度为`Integer.MAX_VALUE`，最小不能低于*16*，可通过系统参数`io.netty.eventLoop.maxPendingTasks`指定.

---