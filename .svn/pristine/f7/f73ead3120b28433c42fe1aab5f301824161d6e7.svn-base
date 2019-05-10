package cn.com.yunyoutianxia.tour.config;

import cn.com.yunyoutianxia.commons.core.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zhiwutu
 * @Date: 2019/5/10 10:45
 * @Description:
 */
public class WebHandlerInterceptor implements HandlerInterceptor {

    @Value("${profile.short.url.allow.host}")
    private String allowOrigin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("允许的主机" + allowOrigin);
        String origin = request.getHeader("origin");
        String realIp = getRealIp(request);
        String callback = request.getParameter("callback");
        String referer = request.getHeader("Referer");
//        if(!allowOrigin.contains(realIp)) {
//            JSONObject result = new JSONObject();
//            result.put("error", "403");
//            result.put("msg", "不允许的源！请从原网站访问");
//            String jsonString = JSON.toJSONString(result, SerializerFeature.BrowserCompatible);
//            responseJson(response,jsonString, callback);
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void responseJson(HttpServletResponse response, String json, String callback){
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(StringUtils.isNotEmpty(callback) ? callback+"("+json+");" : json);
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(null != writer){
                writer.close();
            }
        }
    }


}
