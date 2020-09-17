package com.atom.tool.sensitive;

import com.atom.tool.reflection.ReflectionUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 日志脱敏工具类
 *
 * @author Atom
 */
@Slf4j
public class DesensitizedUtils {

    private static final ConcurrentHashMap<String, String> MASK_STRATEGY = new ConcurrentHashMap<>();


    /**
     * {@link UserInfoVO}
     */
    static {
        /**
         * fixedPhone
         */
        registerStrategy("telephone", "fixedPhone");


        /**
         * mobilePhone
         */
        registerStrategy("mobileNumber", "mobilePhone");
        registerStrategy("mobilePhone", "mobilePhone");
        registerStrategy("bindMobileNum", "mobilePhone");
        registerStrategy("masterMobile", "mobilePhone");
        registerStrategy("spouseMobile", "mobilePhone");
        registerStrategy("phoneNumber", "mobilePhone");
        registerStrategy("masterBindPhone", "mobilePhone");
        registerStrategy("spouseBindPhone", "mobilePhone");
        registerStrategy("mobile", "mobilePhone");

        /**
         * idCardNum
         */
        registerStrategy("cardId", "idCardNum");
        registerStrategy("idCard", "idCardNum");
        registerStrategy("masterIdCard", "idCardNum");
        registerStrategy("spouseIdCard", "idCardNum");
        registerStrategy("idCard", "idCardNum");
        registerStrategy("spouseIdCard", "idCardNum");

        /**
         * email
         */
        registerStrategy("email", "email");

        /**
         * password
         */
        registerStrategy("password", "password");
    }


    /**
     * 注册掩码策略
     *
     * @param fieldForMask 需要掩码的字段名
     * @param methodName   具体掩码方法名
     */
    private static void registerStrategy(String fieldForMask, String methodName) {
        if (StringUtils.isNotBlank(fieldForMask)) {
            MASK_STRATEGY.put(fieldForMask, methodName);
        }
    }


    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
     *
     * @param fullName
     * @return
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * 【身份证号】显示前面4位和最后四位，其他隐藏。共计18位或者15位（携程规则）
     * 450481197804234431 -> 4504***********431
     * 44090119760311922X -> 4409***********22X
     *
     * @param id
     * @return
     */
    public static String idCardNum(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        // left=4504
        String left = StringUtils.left(id, 4);
        //right=431
        String right = StringUtils.right(id, 3);
        //starWithRight=***************431  15个*加后三位明文
        String starWithRight = StringUtils.leftPad(right, StringUtils.length(id), "*");
        //remove StringUtils.length(left) 个 * ， starWithRightWithoutLeft=***********431  得到11个*加后三位明文
        String starWithRightWithoutLeft = StringUtils.removeStart(starWithRight, StringUtils.repeat("*", StringUtils.length(left)));
        return left.concat(starWithRightWithoutLeft);
    }

    /**
     * 【固定电话】 显示后四位，其他隐藏，比如1234
     *
     * @param num
     * @return
     */
    public static String fixedPhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * 【手机号码】前三位，后四位，其他隐藏，比如135****6810
     *
     * @param num
     * @return
     */
    public static String mobilePhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address
     * @param sensitiveSize 敏感信息长度
     * @return
     */
    public static String address(String address, int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * 电子邮箱 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com>
     *
     * @param email
     * @return
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= 1) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*")
                    .concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    /**
     * 【银行卡号】使用微信/支付宝银行卡号掩码规则：银行卡号只保留后四位，其他用星号隐藏，每位1个星号，比如：***************3059
     *
     * @param cardNum
     * @return
     */
    public static String bankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        String right = StringUtils.right(cardNum, 4);
        return StringUtils.leftPad(right, cardNum.length(), "*");
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     * 密码可为空格(Windows系统允许设置空格为登录密码)，空格也算一个字符
     *
     * @param password
     * @return
     */
    public static String password(String password) {
        if (StringUtils.isEmpty(password)) {
            return "";
        }
        return StringUtils.repeat("*", password.length());
    }


    /**
     * mask special fields for special  object
     *
     * @param target
     * @param fields
     * @return
     */
    public static <T> T maskSpecialField(T target, List<String> fields) {
        if (Objects.isNull(target) /*|| CollectionUtils.isEmpty(fields)*/) {
            return target;
        }
        if (target instanceof Map) {
            return (T) maskForMap((Map) target, fields);
        }
        if (target instanceof List) {
            return (T) maskForList((List) target, fields);
        }
        if (target.getClass().isArray()) {
            return (T) maskForArray((Object[]) target, fields);
        }

        return maskForObject(target, fields);
    }

    private static Object maskForList(List targetList, List<String> fields) {

        if (CollectionUtils.isEmpty(targetList)) {
            return targetList;
        }
        List newTargetList = null;
        if (targetList instanceof ArrayList) {
            newTargetList = new ArrayList();
        } else if (targetList instanceof LinkedList) {
            newTargetList = new LinkedList();
        }

        for (Object target : targetList) {
            Object newObj = target;
            if (Objects.nonNull(target) && target instanceof String) {
                newObj = chooseMaskStrategy((String) target, fields);
            } else if (Objects.nonNull(target) && !(target instanceof String)) {
                newObj = maskSpecialField(target, fields);
            }
            newTargetList.add(newObj);
        }
        return newTargetList;
    }

    private static <T> T maskForObject(T target, List<String> fields) {
        try {
            final Class clazz = target.getClass();
            final T newObject = (T) clazz.newInstance();
            BeanUtils.copyProperties(target, newObject);
            List<Field> fieldList = ReflectionUtil.getAllFieldList(clazz);
            for (Field field : fieldList) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                String fieldName = field.getName();
//                Object originalFieldVal = PropertyUtils.getProperty(newObject, fieldName);
                Object originalFieldVal = field.get(newObject);

                if (/*fields.contains(fieldName) &&*/ Objects.nonNull(originalFieldVal)) {
                    originalFieldVal = chooseMaskStrategy(fieldName, originalFieldVal);
                }
                field.set(newObject, originalFieldVal);

            }
            return newObject;
        } catch (Exception e) {
            log.warn("maskSpecialField error=[{}]", Throwables.getStackTraceAsString(e));
        }
        return target;
    }

    private static Object maskForArray(Object[] target, List<String> fields) {
        if (ArrayUtils.isEmpty(target)) {
            return target;
        }

        Object[] arr = new Object[target.length];
        for (int i = 0; i < target.length; i++) {
            Object innerTarget = target[i];
            if (Objects.nonNull(innerTarget) && innerTarget instanceof String) {
                arr[i] = chooseMaskStrategy((String) innerTarget, fields);
            } else if (Objects.nonNull(innerTarget) && !(innerTarget instanceof String)) {
                arr[i] = maskSpecialField(innerTarget, fields);
            }
        }

        return arr;
    }

    private static Map<String, Object> maskForMap(Map<String, Object> target, List<String> fields) {
        if (MapUtils.isEmpty(target)) {
            return target;
        }
        Map newMap = Maps.newHashMapWithExpectedSize(10);
        for (String key : target.keySet()) {
            Object originalFieldVal = target.get(key);
            if (Objects.nonNull(originalFieldVal) && originalFieldVal instanceof String /*&& fields.contains(key)*/) {
                originalFieldVal = chooseMaskStrategy(key, originalFieldVal);
            } else if (Objects.nonNull(originalFieldVal) && !(originalFieldVal instanceof String)) {
                originalFieldVal = maskSpecialField(originalFieldVal, fields);
            }
            newMap.put(key, originalFieldVal);
        }
        return newMap;
    }


    /**
     * choose strategy for special field
     *
     * @param fieldName
     * @param originalFieldVal
     * @return
     */
    private static Object chooseMaskStrategy(String fieldName, Object originalFieldVal) {
        String strategyMethodName = MASK_STRATEGY.get(fieldName);
        if (StringUtils.isBlank(strategyMethodName)) {
            return originalFieldVal;
        }
        try {
            Method strategyMethod = org.springframework.util.ReflectionUtils.findMethod(DesensitizedUtils.class, strategyMethodName, String.class);
            originalFieldVal = strategyMethod.invoke(DesensitizedUtils.class, originalFieldVal);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn("chooseMaskStrategy invoke method [{}] error=[{}]", strategyMethodName, Throwables.getStackTraceAsString(e));
        }
        return originalFieldVal;
    }


}
