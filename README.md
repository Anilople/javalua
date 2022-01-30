# javalua

Use Java to write lua interpreter

学习 [自己动手实现Lua](https://book.douban.com/subject/30348061/)

环境：
* Java 11
* maven 3.6.3

为了简化代码，用了 lombok，如果运行测试失败，需要maven clean一下再跑

## ch02

如果luac指定了`-s`，那么行号表，局部变量表和Upvalue表，不会生成在chunk中，它们都是调试信息

书里用的lua版本是5.3.4 由于5.4的header发生变动，因此踩了坑，所以这里代码也改为实现5.3

截至commit [e354c6355e7f48e087678ec49e340ca0696725b1](https://github.com/lua/lua/tree/e354c6355e7f48e087678ec49e340ca0696725b1)

lua的官方实现中，文件如下

* lua.h 常量
* ldump.c 编码实现
* lundump.c 解码实现
* lobject.h 数据结构

本机也是little endian 小端

最终的效果是，可以读取官方编译器编译出来的chunk文件

## ch03

基于栈（Stack Based）的虚拟机：Java虚拟机、.NET CLR、Python虚拟机、Ruby YARV虚拟机

基于寄存器（Register Based）的虚拟机：安卓早期的Dalvik虚拟机，Lua虚拟机

Lua 5.0 之前是基于栈的虚拟机，5.0 开始改成了基于寄存器的虚拟机

指令集（Instruction Set）

* 定长（Fixed-width）指令集
* 变长（Variable-width）指令集

Lua用定长（Fixed-width）指令集，每条指令占4个字节，低6比特放操作码（Opcode），其余26比特放操作数（Operand）

Lua 5.3 有47条指令，6大类

* 常量加载
* 运算符
* 循环和跳转
* 函数调用
* 表操作
* Upvalue操作

4种编码模式（Mode）

* iABC：B+C+A
* iABx：Bx+A
* iAsBx：sBx+A
* iAx：Ax

| 操作数 | 占用的比特 | 介绍                             |
| ------ | ---------- | -------------------------------- |
| A      | 8          |                                  |
| B      | 9          |                                  |
| C      | 9          |                                  |
| Bx     | 18         |                                  |
| sBx    | 18         | 只有这个操作数被解释成有符号整数 |
| Ax     | 26         |                                  |

操作码（Opcode）只有6位，最多产生64条指令

Lua 5.3 有 47 条，从0到46

坑记录：

byte -128 转为 int 时，如果写成

```java
byte byteValue = -128;
int intValue = byteValue;
```

intValue也是-128，但是底层的bits 从 

1000 000 

变成 

1000 0000 0000 0000 0000 0000 0000 0111，

发生重大变化，和期望的 

0000 0000 0000 0000 0000 0000 1000 000 不一样，

不应该强转，应该用 Byte.toUnsignedInt 方法来转换

