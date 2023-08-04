package com.ztliao.rpc;

/**
 * @author: liaozetao
 * @date: 2023/8/4 13:22
 * @description:
 */
public interface RPCProtocol {

    long versionID = 666;

    void mkdirs(String path);
}
