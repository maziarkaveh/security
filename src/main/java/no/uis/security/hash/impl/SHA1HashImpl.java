package no.uis.security.hash.impl;

import no.uis.security.common.utils.LogicalUtils;
import no.uis.security.hash.AbstractHashService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 03.11.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SHA1HashImpl extends AbstractHashService {
    private static final int H1 = 0x67452301;
    private static final int H2 = 0xefcdab89;
    private static final int H3 = 0x98badcfe;
    private static final int H4 = 0x10325476;
    private static final int H5 = 0xc3d2e1f0;
    private static final int Y1 = 0x5a827999;
    private static final int Y2 = 0x6ed9eba1;
    private static final int Y3 = 0x8f1bbcdc;
    private static final int Y4 = 0xca62c1d6;

    @Override
    public String hash(byte[] content) {
        int[] blocks = new int[(((content.length + 8) >> 6) + 1) * 16];
        int i;
        for (i = 0; i < content.length; i++) {
            blocks[i >> 2] |= content[i] << (24 - (i % 4) * 8);
        }
        blocks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blocks[blocks.length - 1] = content.length * 8;
        int[] w = new int[80];
        int a = H1;
        int b = H2;
        int c = H3;
        int d = H4;
        int e = H5;
        for (i = 0; i < blocks.length; i += 16) {
            int lastA = a;
            int lastB = b;
            int lastC = c;
            int lastD = d;
            int lastE = e;
            for (int j = 0; j < 80; j++) {
                w[j] = (j < 16) ? blocks[i + j] : LogicalUtils.rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
                int y = (j < 20) ? Y1 + ((b & c) | ((~b) & d))
                        : (j < 40) ? Y2 + (b ^ c ^ d)
                        : (j < 60) ? Y3 + ((b & c) | (b & d) | (c & d))
                        : Y4 + (b ^ c ^ d);
                int t = LogicalUtils.rol(a, 5) + e + w[j] + y;
                e = d;
                d = c;
                c = LogicalUtils.rol(b, 30);
                b = a;
                a = t;
            }
            a = a + lastA;
            b = b + lastB;
            c = c + lastC;
            d = d + lastD;
            e = e + lastE;
        }
        return LogicalUtils.intArrayToStringHex(a, b, c, d, e);
    }
}
