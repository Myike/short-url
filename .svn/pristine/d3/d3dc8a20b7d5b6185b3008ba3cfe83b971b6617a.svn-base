package cn.com.yunyoutianxia.tour.controller;

import cn.com.yunyoutianxia.commons.core.util.StringUtils;
import cn.com.yunyoutianxia.tour.service.MainService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhiwutu
 * @Date: 2019/5/7 14:49
 * @Description:
 */
@Controller
public class MainController {

    @Resource
    private MainService mainService;

    /**
     * @Description:  转换成短网址
     *
     * @MethodName   getShortUrl
     * @author      zhiwutu
     * @param originUrl
     * @return      java.lang.Object
     * @date        2019/5/8 16:22
     */
    @CrossOrigin(origins = "${profile.short.url.allow.host}")
    @RequestMapping(value = "/get/short/url")
    @ResponseBody
    public Object getShortUrl(String originUrl) {
        JSONObject result = new JSONObject();
        if(StringUtils.isEmpty(originUrl)) {
            result.put("msg", "参数错误！原路径不能为空！");
            return result;
        }
        String shortCode = mainService.getShortCode(originUrl);
        result.put("shortCode", shortCode);
        result.put("originUrl", originUrl);
        return result;
    }

    /**
     * @Description:  301重定向至原页面
     *
     * @MethodName   pageVisit
     * @author      zhiwutu
     * @param response
     * @param code
     * @return      void
     * @date        2019/5/8 16:23
     */
    @RequestMapping("/{code}")
    public void pageVisit(HttpServletResponse response, @PathVariable("code") String code) {
        if(StringUtils.isNotEmpty(code)) {
            String originUrl = mainService.getOriginUrl(code);
            response.setStatus(301);
            response.setHeader("Location", originUrl);
        }
    }


}
