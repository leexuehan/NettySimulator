package common;

import threadmodel.NioEventLoop;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

public class NioServerSocketChannel {
    private NioEventLoop eventLoop;
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();

    //java nio channel
    private ServerSocketChannel ch;


    public NioServerSocketChannel() {
        SelectorProvider provider = DEFAULT_SELECTOR_PROVIDER;
        ch = newChannel(provider);
        try {
            ch.configureBlocking(false);
        } catch (IOException e) {
            throw new ChannelException("failed to enter non-blocking mode", e);
        }

    }

    private ServerSocketChannel newChannel(SelectorProvider provider) {
        try {
            return provider.openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("open server socket channel exception");
        }
    }

    public void bind(final SocketAddress localAddress) throws IOException {
        //register
        eventLoop.register(this);
        this.ch.bind(localAddress, 1024);
    }

    public void setEventLoop(NioEventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    public void doRegister(NioEventLoop nioEventLoop) throws ClosedChannelException {
        javaChannel().register(nioEventLoop.getSelector(), 0, this);
        System.out.println("register finished");
    }

    private ServerSocketChannel javaChannel() {
        return ch;
    }
}
