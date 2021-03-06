package tech.netty;

import tech.netty.threadmodel.NioEventLoop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        NioServerSocketChannel serverSocketChannel = new NioServerSocketChannel();
        NioEventLoop eventLoop = new NioEventLoop(10, new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        });
        serverSocketChannel.setEventLoop(eventLoop);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //wait for connection
        int taskNum = 9;
//        for (int i = 0; i < taskNum; i++) {
//            eventLoop.execute(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 100; j++) {
//                        System.out.println(j);
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
        Thread.sleep(1000000);
    }
}
