package com.coder.lesson.utils;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @类名 CoderUtil
 * @描述 coder专属工具类
 * @创建人 张全蛋
 * @创建日期 2020/2/21 19:17
 * @版本 1.0
 **/
public class CoderUtil {

    /**
     * 校验crud操作是否成功，不成功则抛出异常
     *
     * @param count crud操作的行数
     */
    public static void crudIsSuccess(int count) {
        if (count == 0) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
    }


    /**
     * @param isHave 为true是带分隔符的uuid，为false没有分隔符
     * @return
     */
    public static String getUUID(boolean isHave) {
        String uuid;
        if (isHave) {
            uuid = UUID.randomUUID().toString();
        } else {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
        }
        return uuid;
    }

    /**
     * 集合非空验证
     *
     * @param arr
     * @return
     */
    public static boolean validArrayIsEmpty(List<?> arr) {
        if (arr == null || arr.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 从token中获取userId
     *
     * @param request
     * @return
     */
    public static String getUserId(HttpServletRequest request) {
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(Constant.REFRESH_TOKEN);
        }
        String userId = JwtTokenUtil.getUserId(token);
        return userId;
    }
}
