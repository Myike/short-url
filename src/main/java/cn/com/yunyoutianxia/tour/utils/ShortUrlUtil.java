package cn.com.yunyoutianxia.tour.utils;
import cn.com.yunyoutianxia.commons.util.MD5;
import java.util.Random;


/**
 * @Author: zhiwutu
 * @Date: 2019/5/8 16:00
 * @Description: 短域名code工具类
 */
public class ShortUrlUtil {

    //62位字符库，顺序已打乱，最好不要改顺序
    private static final String CHARS = "YLZmtacRiVrB0p4F1wgoSzPUMTNWdnkGyb3D6EC7Jfe9KA2qXlj8uOhvxIHsQ5";
    private static final long SCALE = 62;

    //常用短码10进制起点
    public static final long LENGTH_1_NUM = 0;                 //1位短码区间起始值
    public static final long LENGTH_2_NUM = 62;                //2位短码区间起始值
    public static final long LENGTH_3_NUM = 3844;              //3位短码区间起始值
    public static final long LENGTH_4_NUM = 238328;            //4位短码区间起始值
    public static final long LENGTH_5_NUM = 14776336;          //5位短码区间起始值
    public static final long LENGTH_6_NUM = 916132832;         //6位短码区间起始值



    /**
     * @Description: 短域名生成算法-摘要算法
     *
     * 1、将长网址 md5 生成 32 位签名串,分为 4 段, 每段 8 个字节
     * 2、对这四段循环处理, 取 8 个字节, 将他看成 16 进制串与 0x3fffffff(30位1) 与操作, 即超过 30 位的忽略处理
     * 3、这 30 位分成 6 段, 每 5 位的数字作为字母表的索引取得特定字符, 依次进行获得 6 位字符串
     * 4、总的 md5 串可以获得 4 个 6 位串,取里面的任意一个就可作为这个长 url 的短 url 地址
     *
     * @MethodName   shortUrl
     * @author      zhiwutu
     * @param longUrl
     * @return      java.lang.String
     * @date        2019/5/8 16:04
     */
    public static String digestAlgorithm(String longUrl) {
        String encode = MD5.encryptPassword(longUrl);
        String[] restUrls = new String[4];
        for(int i = 0; i < 4; i++) {
            String substring = encode.substring(i * 8, i * 8 + 8);
            long  subLong = 0x3FFFFFFF & Long.parseLong(substring, 16);
            StringBuilder item = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & subLong;         // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                item.append(CHARS.charAt((int) index));    // 把取得的字符相加
                subLong = subLong >> 5;                    // 每次循环按位右移 5 位
            }
            restUrls[i] = item.toString();
        }
        return restUrls[0];
    }

    /**
     * @Description:  10进制-62进制，自定义进制
     *
     * @MethodName   encode62
     * @author      zhiwutu
     * @param number   10进制数字
     * @return      java.lang.String
     * @date        2019/5/9 15:09
     */
    public static String encode62(long number) {
        StringBuilder result=new StringBuilder();
        while(number > SCALE - 1) {
            result.append(CHARS.charAt((int) (number % SCALE)));
            number = number / SCALE;
        }
        result.append(CHARS.charAt((int) number));
        return result.reverse().toString();

    }

    /**
     * @Description:  62进制-10进制
     *
     * @MethodName   decode10
     * @author      zhiwutu
     * @param str62     必须是62进制字符
     * @return      long
     * @date        2019/5/9 15:08
     */
    public static long decode10(String str62) {
        str62 = str62.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str62.length(); i++) {
            index = CHARS.indexOf(str62.charAt(i));
            num += (long) (index * (Math.pow(SCALE, str62.length() - i - 1)));
        }
        return num;
    }

    /**
     * @Description:  洗牌算法-改变字符库的顺序
     *
     * @MethodName   changeCharsIndex
     * @author      zhiwutu
     * @param chars
     * @return      java.lang.String
     * @date        2019/5/9 15:01
     */
    public static String changeCharsIndex(String chars) {
        Random random = new Random();
        char[] charArray = chars.toCharArray();
        for(int i = charArray.length - 1; i > 0; i--) {
            int rand = random.nextInt(i+1);
            char temp = charArray[i];
            charArray[i] = charArray[rand];
            charArray[rand] = temp;
        }
        return String.valueOf(charArray);
    }


    public static void main(String[] args) {
        System.out.println(encode62(62));
        System.out.println(decode10(encode62(36526446)));
        System.out.println(decode10("wq"));
//		String newChars = changeCharsIndex(chars);
//		System.out.println(newChars);
//		System.out.println(newChars.length());
    }


}
