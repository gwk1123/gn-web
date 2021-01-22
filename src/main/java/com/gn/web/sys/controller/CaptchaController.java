package com.gn.web.sys.controller;

import com.gn.web.common.constant.Constants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.sign.Base64;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.common.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController
{
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public CommonResult getCode(HttpServletResponse response) throws IOException
    {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        redisCache.saveString(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try
        {
            CommonResult ajax = CommonResult.success("操作成功");
            ajax.put("uuid", uuid);
            ajax.put("img", Base64.encode(stream.toByteArray()));
            return ajax;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return CommonResult.error(e.getMessage());
        }
        finally
        {
            stream.close();
        }
    }
}
