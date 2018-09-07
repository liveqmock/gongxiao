package com.yhglobal.gongxiao.demo;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 过滤器demo
 *
 * @author 葛灿
 */
public class MyFilter implements ValueFilter {

    // BigDecimal格式化板式 如 #0.00
    private String bigDecimalFormat;

    /**
     * 如果是BigDecimal类型,使用FastJson序列化时格式做修改
     * 同理可通过instanceof对其他类型,如日期格式,做样式过滤
     *
     * @param object 对象
     * @param name   对象的字段的名称
     * @param value  对象的字段的值
     */
    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null && value instanceof BigDecimal) {
            String format = new DecimalFormat(bigDecimalFormat).format(value);
            return new DecimalFormat(bigDecimalFormat).format(value);
        }
        return value;
    }

    /**
     * 有参数的构造方法,同理可以创造一个无参数构造函数、多参数构造
     * 但是bigDecimalFormat由本方法内部制定,不是外部调用者通过参数制定
     *
     * @param bigDecimalFormat BigDecimal格式化板式
     */
    public MyFilter(String bigDecimalFormat) {
        super();
        this.bigDecimalFormat = bigDecimalFormat;
    }
}