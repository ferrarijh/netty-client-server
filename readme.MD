# Netty Server Demo

* `Bootstrap`: Binds port and accepts incoming connection.

## Components
### Channel
4 states of channel lifecycle:
1) ChannelUnregistered - Channel created but not registered to EventLoop
2) ChannelRegistered - Channel registered to EventLoop
3) ChannelActive - Channel connected to remote peer, ready to transmit data
4) ChannelInactive - Channel not connected to remote peer

## Classes

### Class `ChannelPipeline`
Alignment of set of ChannelHandlers.

### Class `ByteToMessageDecoder`
* ByteToMessageDecoder calls `decode()` method with an internally maintained cumulative buffer whenever new data is received.
* If decode() adds an object to out, it means the decoder decoded a message successfully. 
ByteToMessageDecoder will discard the read part of the cumulative buffer. 
Please remember that you don't need to decode multiple messages. 
ByteToMessageDecoder will keep calling the decode() method until it adds nothing to out.

### Class `ReplayingDecoder`
* `decode()`
: may be called multiple times to decode a single message.

### Class `Unpooled`
Creates a new ByteBuf by allocating new space or by wrapping or copying existing byte arrays, byte buffers and a string.

### Class `ChannelHandlerContext`
Enables `ChannelHandler` to interact with its `ChannelPipeline` and its other handlers.

* `ChannelFuture write(object msg)`
: Request to write a message via its `ChannelHandlerContext` through the `ChannelPipeline`.

* `void channelRead(Object msg)`
: A Channel received a message. 
This will result in having the ChannelInboundHandler.channelRead(ChannelHandlerContext, Object) method 
called of the next ChannelInboundHandler contained in thes ChannelPipeline of the Channel.

* `void channelReadComplete()`
: Triggers an ChannelInboundHandler.channelReadComplete(ChannelHandlerContext) event to the next ChannelInboundHandler in the ChannelPipeline.

`ChannelInboundHandler.channelReadComplete(ChannelHandlerContext)`
: Invoked when the last message read by the current read operation has been consumed by channelRead(ChannelHandlerContext, Object). 
If ChannelOption.AUTO_READ is off, no further attempt to read an inbound data from the current Channel will be made until ChannelHandlerContext.read() is called.

### Boostrap
Manages configurations and operation of an app. 
Integrates components such as event loop group, channel, address, handlers, etc.

## Issues

### `no manifest attribute in jar`
Specify main class in `pom.xml`.

### `NoClassDefFoundError`
Create fat-jar or uber-jar. (Issued when dependent classes are missing)

# Glossaries
### binding