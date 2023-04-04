package com.qicong.os.common.freemarker;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
*User：祁大聪
*标签模型父类
**/
@Component
public interface FreemarkerTagModel extends TemplateDirectiveModel {
	
    @Override
    default void execute(Environment environment,Map params,
                         TemplateModel[] model, TemplateDirectiveBody body) throws TemplateException, IOException {
    	//子类实现getParams
        Object paramObj = getParams(params);
        if(null != paramObj){
            FreeMarkerModel fmModel = (FreeMarkerModel)paramObj;
            //把得到的值 wrap 输出前端
            TemplateModel templateModel = new BeansWrapperBuilder(Configuration.VERSION_2_3_30).build().wrap(fmModel.getValue());

            //类似于页面中：<#assign fmModel.getName() = value >
            environment.setVariable(fmModel.getName(), templateModel);
            body.render(environment.getOut());
        }

    }
    
    /**
     * 委派下去让子类实现，并且返回加工后的返回值
     * 可返回业务对象，或者集合
     * @param params
     * @return
     */
    Object getParams(Map params);
    
    /**
     * 获取 String 参数
     * @param params   前端提交来的参数<Key,Value>
     * @param key      key
     * @return
     */
    default String getString(Map params,String key){
        Object element = params.get(key);
        String value;
        if(element instanceof SimpleScalar){
            value = ((SimpleScalar) element).getAsString();
        }else{
            value = element.toString();
        }
        return value;
    }
    
}

