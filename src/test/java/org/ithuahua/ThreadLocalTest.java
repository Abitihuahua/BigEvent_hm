package org.ithuahua;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
//        创建一个线程
        ThreadLocal tl = new ThreadLocal();
//        开启一个线程
        new Thread(()->{
            tl.set("huahua");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"蓝色线程").start();

        new Thread(()->{
            tl.set("lwj");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"绿色线程").start();
    }
}
