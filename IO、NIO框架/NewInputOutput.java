/**
 * NIO引入了Channel、Buffer和Selector，
 * 就是想把这些信息具体化，
 * 让程序员有机会控制它们。
 *
 * 例如当我们调用write()往SendQ中写数据时，
 * 当一次写的数据超过SendQ长度时需要按照SendQ的长度进行分割，
 * 在这个过程中需要将用户空间数据和内核地址空间进行切换，
 * 而这个切换不是你可以控制的，
 * 但在Buffer中我们可以控制Buffer的容量、是否扩容以及如何扩容
 */

public class NewInputOutput {
    public void selector() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设置为非阻塞方式
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册监听的事件

        while (true) {
            Set selectedKeys = selector.selectedKeys();//去的所有key集合
            Iterator it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel1.accept();//接受到服务端的请求
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    it.remove();
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    while (true) {
                        buffer.clear();
                        int n = sc.read(buffer);//读取数据
                        if (n <= 0) {
                            break;
                        }
                        buffer.flip();
                    }
                    it.remove();
                }
            }

        }
    }
}
