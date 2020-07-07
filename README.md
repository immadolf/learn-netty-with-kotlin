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
- 无参的NioEventLoopGroup()方法默认使用CPU逻辑核数×2的个数的线程，所以实践中一般指定为1
- NioEventLoopGroup()方法默认使用ThreadPerTaskExecutor执行器，其execute方法的行为是调用默认线程工厂获取一个新线程然后执行任务
- 默认线程工厂每次调用newThread方法都会创建新的线程，所以实际使用中应该避免使用这个线程工厂