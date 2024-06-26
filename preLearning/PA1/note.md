## 必做题
### 尝试理解计算机如何计算
在看到上述例子之前, 你可能会觉得指令是一个既神秘又难以理解的概念. 不过当你看到对应的C代码时, 你就会发现指令做的事情竟然这么简单! 而且看上去还有点蠢, 你随手写一个for循环都要比这段C代码看上去更高级.

不过你也不妨站在计算机的角度来理解一下, 计算机究竟是怎么通过这种既简单又笨拙的方式来计算1+2+...+100的. 这种理解会使你建立"程序如何在计算机上运行"的最初原的认识.

- 计算机按照固定的流程运行指令, 取指, 译码, 执行(计算, 访存, 回写)
- 对于指令序列(纸带), 只需要根据指令读出来的结果执行指令即可

### 从状态机视角理解程序运行
以上一小节中1+2+...+100的指令序列为例, 尝试画出这个程序的状态机.

```assembly
    // PC: instruction    | // label: statement
    0: mov  r1, 0         |  pc0: r1 = 0;
    1: mov  r2, 0         |  pc1: r2 = 0;
    2: addi r2, r2, 1     |  pc2: r2 = r2 + 1;
    3: add  r1, r1, r2    |  pc3: r1 = r1 + r2;
    4: blt  r2, 100, 2    |  pc4: if (r2 < 100) goto pc2;   // branch if less than
    5: jmp 5              |  pc5: goto pc5;
```

这个程序比较简单, 需要更新的状态只包括PC和r1, r2这两个寄存器, 因此我们用一个三元组(PC, r1, r2)就可以表示程序的所有状态, 而无需画出内存的具体状态. 初始状态是(0, x, x), 此处的x表示未初始化. 程序PC=0处的指令是mov r1, 0, 执行完之后PC会指向下一条指令, 因此下一个状态是(1, 0, x). 如此类推, 我们可以画出执行前3条指令的状态转移过程:

(0, x, x) -> (1, 0, x) -> (2, 0, 0) -> (3, 0, 1)
请你尝试继续画出这个状态机, 其中程序中的循环只需要画出前两次循环和最后两次循环即可.

PC指向下一条要执行的指令的位置

| PC   | R1                     | R2   | 将要执行的指令                                       |
| ---- | ---------------------- | ---- | ---------------------------------------------------- |
| 0    | ?                      | ?    | mov r1, 0                                            |
| 1    | 0                      | ?    | mov r2, 0                                            |
| 2    | 0                      | 0    | add r2, r2, 1                                        |
| 3    | 0                      | 1    | add r1, r1, r2                                       |
| 4    | 1                      | 1    | blt r2, 100, 2 <br />(r2 = 1 < 100, set PC 2)        |
|      |                        |      | 第二次循环开始                                       |
| 2    | 1                      | 1    | addi r2, r2, 1                                       |
| 3    | 0                      | 2    | add r1, r1, r2                                       |
| 4    | 3                      | 2    | blt r2, 100, 2 <br />(r2 = 2 < 100, set PC  2)       |
|      |                        |      | …. a fewer microseconds later                        |
| 3    | 4753                   | 98   | add r1, r1, r2                                       |
| 4    | 4851                   | 98   | blt r2, 100, 2 <br />(r2 = 98 < 100, set PC 2)       |
|      |                        |      | 第99次循环开始                                       |
| 2    | 4851<br />(1+2+...+98) | 98   | add r2, r2, 1                                        |
| 3    | 4851                   | 99   | add r1, r1, r2                                       |
| 4    | 4950                   | 99   | blt r2, 100, 2 <br />(r2 = 99 < 100, set PC 2)       |
|      |                        |      | 第100次循环开始                                      |
| 2    | 4950<br />(1+2+...+99) | 99   | add r2, r2, 1                                        |
| 3    | 4950                   | 100  | add r1, r1, r2                                       |
| 4    | 5050                   | 100  | blt r2, 100, 2 <br />(r2 = 100, not < 100, set PC 5) |
| 5    | 5050                   | 100  | jmp 5                                                |
| 5    | 5050                   | 100  | jmp 5 (无限循环)                                     |

