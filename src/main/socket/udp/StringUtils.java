package main.socket.udp;

import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static String fromBytes(byte[] buff) {
        int fimString = 0;
        while (fimString < buff.length && buff[fimString] != 0) {
            fimString++;
        }

        int offset = 0;

        return new String(buff, offset, fimString, StandardCharsets.UTF_8);
    }
}
