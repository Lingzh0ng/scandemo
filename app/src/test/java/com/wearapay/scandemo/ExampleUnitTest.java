package com.wearapay.scandemo;

import com.wearapay.scandemo.utils.ByteUtil;
import java.nio.charset.Charset;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit Dest, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
    String a =
        "010C600003000060310031FFF10210703C028002C08A3119621797391000072509000000000000000055880000231654191115231200010041303239383030303133383438323930343431313136303030313536260000000000000001459F2608DEAE676D1C4734119F2701809F101307020103A0A806010A01000000000098F01F529F3704AA0FBE659F360203499505008004E0009A031611159C01009F02060000000055885F2A02015682027C009F1A0201569F03060000000000009F3303E0E1C89F34034203009F3501229F1E0831323334353637388408A0000003330101019F090200309F41040000000300123030303030313030303137380013220000000006004244454432463843";
    String c2 = "9F2608DEAE676D1C"
        + "4734119F2701809F"
        + "101307020103A0A8"
        + "06010A0100000000"
        + "0098F01F529F3704"
        + "AA0FBE659F360203"
        + "499505008004E000"
        + "9A031611159C0100"
        + "9F0206000000005588";
    String c =
        "600003000060310031FFF10200702406C000C09A31196217973910000725090000000000000000558800002323120510000100063239383030303133383438323930343431313136303030313536086BDB118D7AC25E260000000000000001459F2608DEAE676D1C4734119F2701809F101307020103A0A806010A01000000000098F01F529F3704AA0FBE659F360203499505008004E0009A031611159C01009F02060000000055885F2A02015682027C009F1A0201569F03060000000000009F3303E0E1C89F34034203009F3501229F1E0831323334353637388408A0000003330101019F090200309F41040000000300123030303030313030303137380013220000000006004643423245374432";
    String d = "9F26 08 DEAE676D1C473411"
        + "9F27 01 80"
        + "9F10 13 07020103A0A806010A01000000000098F01F52"
        + "9F37 04 AA0FBE65"
        + "9F36 02 0349"
        + "95 05 008004E000"
        + "9A 03 161115"
        + "9C 01 00"
        + "9F02 06 000000005588"
        + "5F2A 02 0156 "
        + "82 02 7C00 "
        + "9F1A 02 0156 "
        + "9F03 06 000000000000"
        + "9F33 03 E0E1C8 "
        + "9F34 03 420300"
        + "9F35 01 22"
        + "9F1E 08 3132333435363738"
        + "84 08 A000000333010101"
        + "9F09 02 0030"
        + "9F41 04 00000003";

    String f = "9F26 08 DEAE676D1C473411"
        + "9F27 01 80"
        + "9F10 13 07020103A0A806010A01000000000098F01F52"
        + "9F37 04 AA0FBE65"
        + "9F36 02 0349"
        + "95   05 008004E000"
        + "9A   03 161115"
        + "9C   01 00"
        + "9F02 06 000000005588"
        + "5F2A 02 0156"
        + "82   02 7C00"
        + "9F1A 02 0156"
        + "9F03 06 000000000000"
        + "9F33 03 E0E1C8"
        + "9F34 03 420300"
        + "9F35 01 22"
        + "9F1E 08 3132333435363738"
        + "84   08 A000000333010101"
        + "9F09 02 0030"
        + "9F41 04 00000003";
    System.out.println(a);
    System.out.println(d);
    System.out.println("a --------" + a.length());
    System.out.println(d.replace(" ", ""));
    System.out.println("d --------" + d.replace(" ", "").length());
    System.out.println("f --------" + f.replace(" ", "").length());
  }

  @Test public void dosT() {
    String a =
        "DE8C98114554C59B233C06067161CD11D2B91CC57756D05B85A057BF0000000000000000C9BE2D04DE8C98114554C59B233C06067161CD11D2B91CC5";
    byte[] bytes = ByteUtil.hexString2Bytes(a);
    System.out.println(ByteUtil.bytes2HexString(bytes));
    //System.out.println(bytes);
    String UTF = new String(bytes, Charset.forName("UTF-8"));
    String gbk = new String(bytes, Charset.forName("GBK"));
    String iso = new String(bytes, Charset.forName("iso-8859-1"));
    String ASCII = new String(bytes, Charset.forName("ASCII"));
    System.out.println(UTF);
    System.out.println(gbk);
    System.out.println(iso);
    System.out.println(ASCII);
  }
}