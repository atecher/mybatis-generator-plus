package com.atecher.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * A MyBatis Generator plugin to use Lombok's @Data annoation
 * instead of getters and setters
 */
public class LombokPlugin extends PluginAdapter {

    private String override;

    /**
     * LombokPlugin contructor
     */
    public LombokPlugin() {
    }

    /**
     * @param warnings
     * @return always true
     */
    public boolean validate(List<String> warnings) {
        this.override = this.properties.getProperty("override");
        return true;
    }

    /**
     * Intercepts base record class generation
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        return true;
    }

    /**
     * Intercepts primary key class generation
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        return true;
    }

    /**
     * Intercepts "record with blob" class generation
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addDataAnnotation(topLevelClass);
        return true;
    }

    /**
     * Prevents all getters from being generated.
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    /**
     * Prevents all setters from being generated
     * See SimpleModelGenerator
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,TopLevelClass topLevelClass,IntrospectedColumn introspectedColumn,IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * Adds the @Data lombok import and annotation to the class
     *
     * @param topLevelClass
     */
    protected void addDataAnnotation(TopLevelClass topLevelClass) {

        addAnnotation(topLevelClass,"data");
        if(override!=null){
            for (String annotationName : override.split(",")) {
                addAnnotation(topLevelClass,annotationName);
            }
        }
    }

    private void addAnnotation(TopLevelClass topLevelClass,String annotationName){
        switch (annotationName){
            case "data":
                topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
                topLevelClass.addAnnotation("@Data");
                break;
            case "equalsAndHashCode":
                topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.EqualsAndHashCode"));
                topLevelClass.addAnnotation("@EqualsAndHashCode");
                break;
            case "toString":
                topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.ToString"));
                topLevelClass.addAnnotation("@ToString");
                break;
        }
    }

}
