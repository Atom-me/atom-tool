package com.atom.tool;

import com.alibaba.fastjson.JSON;
import com.atom.tool.sensitive.DesensitizedUtils;
import com.atom.tool.testbean.TestEmployeesInfo;
import org.junit.Test;

import java.util.*;

/**
 * 脱敏工具测试
 */
public class DesensitizedUtilsTest {


    /**
     * 中文姓名脱敏测试：李先生 → 李**
     */
    @Test
    public void testChineseNameString() {
        System.err.println(DesensitizedUtils.chineseName("李先生"));
    }

    /**
     * 身份证号脱敏测试：33010419800905945X → **************945X
     */
    @Test
    public void testIdCardNum() {
        System.err.println(DesensitizedUtils.idCardNum("33010419800905945X"));
    }

    /**
     * 固定电话脱敏测试：01086323500 → *******3500
     */
    @Test
    public void testFixedPhone() {
        System.err.println(DesensitizedUtils.fixedPhone("01086323500"));
    }

    /**
     * 手机号码脱敏测试：13579246810 → 135****6810
     */
    @Test
    public void testMobilePhone() {
        System.err.println(DesensitizedUtils.mobilePhone("13579246810"));
    }

    /**
     * 地址脱敏测试：北京市海淀区丹棱街6号丹棱SOHO → 北京市海淀区丹棱街********
     */
    @Test
    public void testAddress() {
        System.err.println(DesensitizedUtils.address("北京市海淀区丹棱街6号丹棱SOHO", 8));
    }

    /**
     * 电子邮箱脱敏测试：abcde@126.com → a****@126.com
     */
    @Test
    public void testEmail() {
        System.err.println(DesensitizedUtils.email("abcdefg@126.com"));
    }

    /**
     * 银行卡号脱敏测试：6224121206590423059 → ***************3059
     */
    @Test
    public void testBankCard() {
        System.err.println(DesensitizedUtils.bankCard("6224121206590423059"));
    }

    /**
     * 密码脱敏测试：123456 → ******
     */
    @Test
    public void testPassword() {
        System.err.println(DesensitizedUtils.password("123456"));
        System.err.println(DesensitizedUtils.password(" "));
    }


    /**
     * 测试简单map
     */
    @Test
    public void testMaskSpecialFieldForMap() {
        Map map = new HashMap();
        map.put("mobile", "18888888888");
        map.put("name", "atom");
        map.put("idCard", "412222222222222229");
        Object mobile = DesensitizedUtils.maskSpecialField(map, Arrays.asList("mobile"));
        System.err.println(mobile);

    }


    /**
     * 测试复杂对象
     */
    @Test
    public void testMaskSpecialFieldForList() {

        List arrayList = new ArrayList();
        TestEmployeesInfo emp001 = new TestEmployeesInfo();
        emp001.setCn("cnvis783");
        emp001.setStaffNameEn("1");
        emp001.setStaffName("张三");
        emp001.setMobile("13333333334");

        TestEmployeesInfo emp002 = new TestEmployeesInfo();
        emp002.setCn("cnvis784");
        emp002.setStaffNameEn("2");
        emp002.setStaffName("张四");
        emp002.setMobile("13333333333");

        arrayList.add(emp001);
        arrayList.add(emp002);

        //simple list
        System.err.println(JSON.toJSONString(DesensitizedUtils.maskSpecialField(arrayList, null)));

    }


    /**
     * 测试 list
     */
    @Test
    public void testMaskSpecialFieldForComplexObject() {
        Map map = new HashMap();
        map.put("mobile", "18888888888");
        map.put("name", "atom");
        map.put("idCard", "412222222222222229");
        //simple map
        System.err.println(DesensitizedUtils.maskSpecialField(map, Arrays.asList("mobile")));

        List arrayList = new ArrayList();
        TestEmployeesInfo emp001 = new TestEmployeesInfo();
        emp001.setCn("cnvis783");
        emp001.setStaffNameEn("1");
        emp001.setStaffName("张三");
        emp001.setMobile("13333333334");

        TestEmployeesInfo emp002 = new TestEmployeesInfo();
        emp002.setCn("cnvis784");
        emp002.setStaffNameEn("2");
        emp002.setStaffName("张四");
        emp002.setMobile("13333333333");

        arrayList.add(emp001);
        arrayList.add(emp002);

        //simple list
        System.err.println(JSON.toJSONString(DesensitizedUtils.maskSpecialField(arrayList, null)));

        map.put("xxx", arrayList);

        //map contains list
        System.err.println(JSON.toJSONString(DesensitizedUtils.maskSpecialField(map, null)));

        List aList = new ArrayList();
        aList.add(map);
        aList.add(emp001);

        aList.add(arrayList);
        aList.add(map);

        // list contains map and simple object  and another list
        System.err.println(JSON.toJSONString(DesensitizedUtils.maskSpecialField(aList, /*Arrays.asList("mobile", "idCard")*/null)));

    }


}
