package cn.com.yunyoutianxia.tour.service;

/**
 * @Author: zhiwutu
 * @Date: 2019/5/7 15:49
 * @Description:
 */
public interface MainService {


    /**
     * @Description:  获取短域名code并存入redis
     *
     * @MethodName   getShortCode
     * @author      zhiwutu
     * @param originUrl
     * @return      java.lang.String
     * @date        2019/5/8 16:08
     */
    String getShortCode(String originUrl);


    /**
     * @Description:  获取原长路径
     *
     * @MethodName   getOriginUrl
     * @author      zhiwutu
     * @param code
     * @return      java.lang.String
     * @date        2019/5/8 16:19
     */
    String getOriginUrl(String code);

}
