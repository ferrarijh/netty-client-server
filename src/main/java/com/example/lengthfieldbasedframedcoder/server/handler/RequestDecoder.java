package com.example.lengthfieldbasedframedcoder.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

public class RequestDecoder extends LengthFieldBasedFrameDecoder {

    private static final Charset charset = CharsetUtil.UTF_8;

    public RequestDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        CharSequence cs = buf.getCharSequence(offset, length, charset);
        return Integer.parseInt(cs.toString());
    }
}
