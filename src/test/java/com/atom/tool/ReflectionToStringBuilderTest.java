package com.atom.tool;

import com.atom.tool.testbean.TestUserVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

/**
 * @author Atom
 */
public class ReflectionToStringBuilderTest {

    @Test
    public void testReflectionToStringBuilder(){
        TestUserVO vo = new TestUserVO("zhangsan","123",8,"male");
        String s1 = ReflectionToStringBuilder.toString(vo, ToStringStyle.MULTI_LINE_STYLE);
        //ToStringStyle.SIMPLE_STYLE 设置toString显示样式
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
        //将password属性排出在toString方法之外
        String s2 = ReflectionToStringBuilder.toStringExclude(vo, "password");
        System.err.println(s1);
        System.err.println(s2);
    }
}
