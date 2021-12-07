package com.slin.study.buildsrc.site;

import org.gradle.api.Action;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.Nested;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description: 嵌套对象扩展
 * <p>
 * 嵌套对象扩展：
 * <p>
 * 1. 跟其他方式一样，先定义一个获取嵌套对象的getter，并添加`Nested`注解；
 * <p>
 * 2. 然后还需要创建一个与对象名称一样的方法，参数为Action,泛型参数为该对象类型或者其父类，然后方法将对象传入action执行即可
 * <p>
 */
abstract public class SiteExtension {

    abstract public RegularFileProperty getOutputDir();

    @Nested
    abstract public CustomData getCustomData();

    public void customData(Action<? super CustomData> action) {
        // 这里其实是将customData属性传递到gradle中给它的其他属性赋值，相当于kotlin中的with方法
        action.execute(getCustomData());
    }
}