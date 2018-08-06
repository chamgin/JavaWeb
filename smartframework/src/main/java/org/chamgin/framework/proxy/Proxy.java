package org.chamgin.framework.proxy;

public interface Proxy {
    //执行链式代理，多个代理顺序执行
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
