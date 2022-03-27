package com.mynote.vo;

/**
 * @author zhenyiBi
 * @date 2022/3/27
 */

import lombok.Getter;
import lombok.Setter;

/**
 * 封装返回结果的类
 *      状态码
 *          成功=2000，失败=5000
*       提示信息
 *      返回的对象(字符串、JavaBean、集合、Map等)
 */

@Getter
@Setter
public class ResultInfo<T> {

    private Integer code; //状态码 成功=2000，失败=5000
    private String msg; //提示信息
    private T result; //返回的对象(字符串、JavaBean、集合、Map等)
}
