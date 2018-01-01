package com.atecher.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**

 * 重命名Dao方法中和mapper中的方法名称

 */
public class RenameExampleMethodPlugin extends PluginAdapter {
    private String searchString;
    private String replaceString;
    private Pattern pattern;

    @Override
    public boolean validate(List<String> warnings) {
        searchString = properties.getProperty("searchString"); //$NON-NLS-1$

        replaceString = properties.getProperty("replaceString");
        boolean valid = stringHasValue(searchString)
                && stringHasValue(replaceString);
        if (valid) {
            pattern = Pattern.compile(searchString);
        }else{
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyRenameExampleMethodPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$

            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyRenameExampleMethodPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$

            }
        }
        return valid;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名countByEx方法和参数
        method.setName(pattern.matcher(introspectedTable.getCountByExampleStatementId()).replaceAll(replaceString));
        convertParam(method, introspectedTable);
        return super.clientCountByExampleMethodGenerated(method, interfaze, introspectedTable);
    }
    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {

        String attrId = pattern.matcher(introspectedTable.getCountByExampleStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapCountByExampleElementGenerated(answer, introspectedTable);
    }


    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名deleteByExample方法和参数
        method.setName(pattern.matcher(introspectedTable.getDeleteByExampleStatementId()).replaceAll(replaceString));
        convertParam(method, introspectedTable);
        return super.clientDeleteByExampleMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        String attrId = pattern.matcher(introspectedTable.getDeleteByExampleStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapDeleteByExampleElementGenerated(answer,introspectedTable);
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名selectByExample方法和参数
        method.setName(pattern.matcher(introspectedTable.getSelectByExampleStatementId()).replaceAll(replaceString));
        convertParam(method, introspectedTable);
        return true;
    }


    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        String attrId = pattern.matcher(introspectedTable.getSelectByExampleStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(answer, introspectedTable);
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(pattern.matcher(introspectedTable.getSelectByExampleWithBLOBsStatementId()).replaceAll(replaceString));
        convertParam(method, introspectedTable);
        return super.clientSelectByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute("id", pattern.matcher(introspectedTable.getSelectByExampleWithBLOBsStatementId()).replaceAll(replaceString)));
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(answer, introspectedTable);
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名updateByExampleSelective方法和参数

        method.setName(pattern.matcher(introspectedTable.getUpdateByExampleSelectiveStatementId()).replaceAll(replaceString));
        convertParamWithAnnotation(method, introspectedTable);
        return true;
    }



    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        String attrId = pattern.matcher(introspectedTable.getUpdateByExampleSelectiveStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapUpdateByExampleSelectiveElementGenerated(answer, introspectedTable);
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(introspectedTable.getUpdateByExampleWithBLOBsStatementId().replace(searchString, replaceString));
        convertParamWithAnnotation(method, introspectedTable);
        return super.clientUpdateByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        String attrId = pattern.matcher(introspectedTable.getUpdateByExampleWithBLOBsStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapUpdateByExampleWithBLOBsElementGenerated(answer,introspectedTable);
    }

    private void convertAttributes(XmlElement answer, String attrId) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute("id", attrId));
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(introspectedTable.getUpdateByExampleStatementId().replace(searchString, replaceString));
        convertParamWithAnnotation(method, introspectedTable);
        return super.clientUpdateByExampleWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }


    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        String attrId = pattern.matcher(introspectedTable.getUpdateByExampleStatementId()).replaceAll(replaceString);
        convertAttributes(answer, attrId);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(answer, introspectedTable);
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        //重命名SqlMap中Update_By_Example_Where_Clause中的example.oredCriteria

        if (attributes.get(0).getValue().equals(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId())) {
            List<Element> elementList = element.getElements();
            XmlElement element1=null;
            if(elementList!=null && !elementList.isEmpty()){
                for (Element  element2 : elementList) {
                    if(element2 instanceof XmlElement){
                        element1 = (XmlElement) element2;
                        if(element1.getName().equals("where")){
                            break;
                        }
                    }
                }
            }

            List<Element> element1Elements = element1.getElements();
            XmlElement element12 = (XmlElement) element1Elements.get(0);
            List<Attribute> attributes2 = element12.getAttributes();
            for (Attribute attribute : attributes2) {
                if (attribute.getName().equals("collection")) {
                    attributes2.remove(attribute);
                    break;
                }
            }
            attributes2.add(new Attribute("collection", "example.oredCriteria".replace(searchString.toLowerCase(), replaceString.toLowerCase())));
        }
        return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
    }

    private void convertParam(Method method, IntrospectedTable introspectedTable) {
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()), replaceString.toLowerCase()));
    }

    private void convertParamWithAnnotation(Method method, IntrospectedTable introspectedTable) {
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()),replaceString.toLowerCase(), "@Param(\"" + replaceString.toLowerCase() + "\")"));
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setExampleWhereClauseId(pattern.matcher(introspectedTable.getExampleWhereClauseId()).replaceAll(replaceString));
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(pattern.matcher(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId()).replaceAll(replaceString));
    }
}