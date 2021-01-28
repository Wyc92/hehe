package com.wangyc.hehe.dao.provider;

import com.google.common.base.Joiner;
import com.wangyc.hehe.dao.annotation.*;
import com.wangyc.hehe.utils.JoinerSplitters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Describe: BaseProvider
 * Date: 2021/1/25
 * Time: 3:22 下午
 * Author: wangyc
 */
public class BaseProvider {
    private static final Joiner douHaoJoiner = JoinerSplitters.getJoiner(",");
    private static final Joiner andJoiner = JoinerSplitters.getJoiner(" and ");
    private static final Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");

    public String deleteByEntity(Object object) throws IllegalAccessException {
        String deleteSQL = getDeleteSQL(object.getClass());
        String whereSQL = getWhereSQL(object);
        return deleteSQL+whereSQL;
    }

    public String deleteByPrimaryKey(Object key,ProviderContext context) throws Exception {
        Class clazz = context.getMapperType();
        Object entity = getEntityByClassAndPrimaryKey(clazz, key);
        return deleteByEntity(entity);
    }


    protected String getDeleteSQL(Class clazz){
        return "delete from "+getDBTableName(clazz);
    }


    /**
     * 根据实体类获得多个结果
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String findByEntity(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        String selectSQL = getSelectSQL(clazz);
        String fromSQL = getFromSQL(clazz);
        String whereSQL = getWhereSQL(object);
        return selectSQL + " " + fromSQL + " " + whereSQL;
    }

    /**
     * 根据实体类获得一个结果
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String getByEntity(Object object) throws IllegalAccessException {
        String findByEntity = findByEntity(object);
        return findByEntity + " limit 1";
    }


    /**
     * 根据主键类获得一个结果
     * @param key
     * @param context
     * @return
     * @throws IllegalAccessException
     */
    public String getByPrimaryKey(Object key, ProviderContext context) throws Exception {
        Class clazz = context.getMapperType();
        Object entity = getEntityByClassAndPrimaryKey(clazz, key);
        String byEntity = getByEntity(entity);
        return byEntity;
    }


    /**
     * 根据clazz和primaryKey构建entity
     * @param clazz
     * @param primaryKey
     * @return
     * @throws Exception
     */
    protected Object getEntityByClassAndPrimaryKey(Class clazz,Object primaryKey)throws Exception {
        Object entity = clazz.newInstance();
        Field primaryField = getPrimaryField(clazz);
        primaryField.setAccessible(true);
        primaryField.set(entity,primaryKey);
        return entity;
    }

    /**
     * insert 忽略null
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String insertIgnoreNull(Object object) throws IllegalAccessException {
        return insertActurally(object, true);
    }

    /**
     * insert 不忽略null
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String insertNoIgnoreNull(Object object) throws IllegalAccessException {
        return insertActurally(object, false);
    }

    /**
     * update 忽略null
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String updateByPrimaryIgnoreNull(Object primary,Object object) throws Exception {
        return updateByPrimaryActurally(primary, object, true);
    }

    /**
     * update 不忽略null
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public String updateByPrimaryNoIgnoreNull(Object primary, Object object) throws Exception {
        return updateByPrimaryActurally(primary, object, false);
    }

    /**
     * update
     * @param primary
     * @param object
     * @param ignore
     * @return
     * @throws Exception
     */
    protected String updateByPrimaryActurally(Object primary, Object object, Boolean ignore) throws Exception {


        String updateSQL = getUpdateSQL(object.getClass());
        String setSQL = getSetSQL(object, ignore);
        //给主键设置值只用于where语句中，不用在set中 所以这段需要写在getSetSQL之后
        Field primaryField = getPrimaryField(object.getClass());
        primaryField.setAccessible(true);
        primaryField.set(object,primary);
        Object entityByClassAndPrimaryKey = getEntityByClassAndPrimaryKey(object.getClass(), primary);
        String whereSQL = getWhereSQL(entityByClassAndPrimaryKey);
        return updateSQL+" "+setSQL+" "+whereSQL;
    }

    /**
     * insert
     * @param object
     * @param ignore
     * @return
     * @throws IllegalAccessException
     */
    protected String insertActurally(Object object,Boolean ignore) throws IllegalAccessException {
        String insertSQL = getInsertSQL(object,ignore);
        String valuesSQL = getValuesSQL(object,ignore);
        return insertSQL+" "+valuesSQL;
    }
    /**
     * select 子句
     * @param clazz
     * @return
     */
    protected String getSelectSQL(Class clazz) {
        List<Field> allFields = getAllFields(clazz);
        List<String> dbFieldNames = allFields.stream().map(field -> getDBFieldName(field) + " as " + field.getName()).collect(Collectors.toList());
        String selectSql = douHaoJoiner.join(dbFieldNames);
        return "select " + selectSql;
    }

    /**
     * from 子句
     * @param clazz
     * @return
     */
    protected String getFromSQL(Class clazz) {
        return " from  " + getDBTableName(clazz);
    }

    /**
     * where 子句
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    protected String getWhereSQL(Object object) throws IllegalAccessException {
        List<Field> allNonNullFields = getAllNonNullFields(object);
        List<String> everyField = allNonNullFields.stream().map(field -> getDBFieldName(field) + " = #{" + field.getName() + "} ").collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(everyField) ? " where " +andJoiner.join(everyField): "";
    }

    /**
     * insert子句
     * @param object
     * @param ignoreNull
     * @return
     */
    protected String getInsertSQL(Object object,Boolean ignoreNull) throws IllegalAccessException {
        String dbTableName = getDBTableName(object.getClass());
        List<Field> everyField = ignoreNull?getAllNonNullFields(object):getAllFields(object.getClass());
        List<String> everyFieldName = everyField.stream().map(field -> getDBFieldName(field)).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(everyField) ? "insert into " +dbTableName+"("+douHaoJoiner.join(everyFieldName)+")": "";
    }

    /**
     * values子句
     * @param object
     * @param ignoreNull
     * @return
     * @throws IllegalAccessException
     */
    protected String getValuesSQL(Object object,Boolean ignoreNull) throws IllegalAccessException{
        List<Field> allNonNullFields = ignoreNull?getAllNonNullFields(object):getAllFields(object.getClass());
        List<String> everyField = allNonNullFields.stream().map(field -> " #{" + field.getName() + "} ").collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(everyField) ? " values (" +douHaoJoiner.join(everyField)+")": "";
    }

    /**
     * update 子句
     * @param clazz
     * @return
     */
    protected String getUpdateSQL(Class clazz){
        String dbTableName = getDBTableName(clazz);
        return "update "+dbTableName;
    }

    /**
     * set子句
     * @param object
     * @param ignoreNull
     * @return
     * @throws IllegalAccessException
     */
    protected String getSetSQL(Object object,Boolean ignoreNull) throws IllegalAccessException {
        List<Field> allNonNullFields = ignoreNull?getAllNonNullFields(object):getAllFields(object.getClass());
        List<String> everyField = allNonNullFields.stream().map(field -> getDBFieldName(field) + " = #{" + field.getName() + "} ").collect(Collectors.toList());
        return "set "+ douHaoJoiner.join(everyField);
    }

    /**
     * 获取所有的非静态 未标注Ignore的字段
     * @param clazz
     * @return
     */
    protected List<Field> getAllFields(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> collect = Arrays.stream(declaredFields)
                .filter(field -> null == field.getAnnotation(Ignore.class))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取主键
     * @param clazz
     * @return
     */
    protected Field getPrimaryField(Class clazz) {
        List<Field> collect = getAllFields(clazz).stream()
                .filter(field -> null != field.getAnnotation(Primary.class)).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(collect)?collect.get(0):null;
    }

    /**
     * 获得所有非空的 非静态 未标注Igore的字段
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    protected List<Field> getAllNonNullFields(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        List<Field> allFields = getAllFields(clazz);
        allFields.forEach(field -> field.setAccessible(true));
        Iterator<Field> iterator = allFields.iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            if (null == field.get(object)) {
                iterator.remove();
            }
        }
        return allFields;
    }

    /**
     * 获得数据库字段名
     *  如果有Column注解、则取注解
     *  没有Column注解、则驼峰转下划线
     * @param field
     * @return
     */
    protected String getDBFieldName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        return null!=annotation?annotation.value():this.camel2Underline(field.getName());
    }

    /**
     * 获得表名
     *  如果有Table注解、则取注解
     *  没有Table注解、则驼峰转下划线
     * @param clazz
     * @return
     */
    protected String getDBTableName(Class clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        return null!=table?table.value():this.camel2Underline(clazz.getSimpleName());
    }


    /**
     * 驼峰转下划线
     * @param line
     * @return
     */
    protected String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}
