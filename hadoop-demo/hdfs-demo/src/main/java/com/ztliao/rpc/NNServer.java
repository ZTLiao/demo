package com.ztliao.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/8/4 13:23
 * @description:
 */
public class NNServer implements RPCProtocol {
    @Override
    public void mkdirs(String path) {
        System.out.println("服务器接受到客户端请求" + path);
    }

    public static void main(String[] args) throws IOException {
        RPC.Server server = new RPC.Builder(new Configuration())
                .setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(RPCProtocol.class)
                .setInstance(new NNServer())
                .build();
        System.out.println("服务器开始工作");
        server.start();
    }
}
