package cn.com.yunyoutianxia.tour.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zhiwutu
 * @Date: 2019/5/10 10:45
 * @Description:  请求主机拦截器
 */
public class WebHandlerInterceptor implements HandlerInterceptor {

    @Value("${profile.short.url.allow.host}")
    private String allowOrigin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String callback = request.getParameter("callback");
        String referer = request.getHeader("Referer");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if(!"OPTIONS".equals(method) && "/get/short/url".equals(requestURI)) {
            boolean flag =  false;
            if(!StringUtils.isEmpty(referer)) {
                for(String host: allowOrigin.split(",")) {
                    //允许的主机
                    if(referer.contains(host)) {
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag) {
                JSONObject result = new JSONObject();
                result.put("error", "403");
                result.put("msg", "不允许的源！请从原网站访问!");
                String jsonString = JSON.toJSONString(result, SerializerFeature.BrowserCompatible);
                responseJson(response,jsonString, callback);
                return false;
            }
        }
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
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(!StringUtils.isEmpty(callback) ? callback+"("+json+");" : json);
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
