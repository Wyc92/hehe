package com.wangyc.hehe.dao.provider.test;

import com.wangyc.hehe.dao.annotation.Column;
import com.wangyc.hehe.dao.annotation.Ignore;
import com.wangyc.hehe.dao.annotation.Primary;
import com.wangyc.hehe.dao.annotation.Table;
import com.wangyc.hehe.dao.provider.BaseProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Describe: BaseProviderTest
 * Date: 2021/1/25
 * Time: 3:33 下午
 * Author: wangyc
 */
public class BaseProviderTest {

    public static BaseProvider baseProvider = new BaseProvider();


    @Test
    void getDBTableName() {
        try {
            Method getDBTableName = baseProvider.getClass().getDeclaredMethod("getDBTableName", Class.class);
            getDBTableName.setAccessible(true);
            String strA = (String) getDBTableName.invoke(baseProvider, TeaC132cHeerA.class);
            String strB = (String) getDBTableName.invoke(baseProvider, TeaC132cHeerB.class);
            assert strEqual("tea_c132c_heer_a",strA);
            assert strEqual("tt",strB);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void getDBFieldName() {
        try {
            Method getDBFieldName = baseProvider.getClass().getDeclaredMethod("getDBFieldName", Field.class);
            getDBFieldName.setAccessible(true);
            Field abcDefD = TeaC132cHeerA.class.getDeclaredField("abcDefD");
            Field AbcDefDe = TeaC132cHeerA.class.getDeclaredField("AbcDefDe");
            Field abc = TeaC132cHeerA.class.getDeclaredField("abc");
            String strA = (String) getDBFieldName.invoke(baseProvider, abcDefD);
            String strB = (String) getDBFieldName.invoke(baseProvider, AbcDefDe);
            String strC = (String) getDBFieldName.invoke(baseProvider, abc);
            assert strEqual("abc_def_d",strA);
            assert strEqual("abc_def_de",strB);
            assert strEqual("aaa",strC);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void camel2Underline() {
        try {
            Method camel2Underline = baseProvider.getClass().getDeclaredMethod("camel2Underline", String.class);
            camel2Underline.setAccessible(true);
            String str = (String) camel2Underline.invoke(baseProvider, "ABcdE123DdwdwAAAApP");
            assert strEqual(str,"a_bcd_e123_ddwdw_a_a_a_ap_p");
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void getPrimaryField() {
        try {
            Method getPrimaryField = baseProvider.getClass().getDeclaredMethod("getPrimaryField", Class.class);
            getPrimaryField.setAccessible(true);


            Field fieldb = (Field) getPrimaryField.invoke(baseProvider, TeaC132cHeerB.class);
            assert TeaC132cHeerB.class.getDeclaredField("id").equals(fieldb);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void getAllFields() {
        try {
            Method getAllFields = baseProvider.getClass().getDeclaredMethod("getAllFields", Class.class);
            getAllFields.setAccessible(true);
            List list = (List) getAllFields.invoke(baseProvider, TeaC132cHeerA.class);
            List allFieldsC = (List) getAllFields.invoke(baseProvider, TeaC132cHeerC.class);
            assert 3 == allFieldsC.size();
            assert 3 == list.size();

        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void getAllNonNullFields() {
        try {
            Method getAllNonNullFields = baseProvider.getClass().getDeclaredMethod("getAllNonNullFields", Object.class);
            getAllNonNullFields.setAccessible(true);
            List list = (List) getAllNonNullFields.invoke(baseProvider, TeaC132cHeerA.builder().build());
            List allFieldsC = (List) getAllNonNullFields.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            List allFieldsD = (List) getAllNonNullFields.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            List allFieldsE = (List) getAllNonNullFields.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            List allFieldsF = (List) getAllNonNullFields.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());
            assert 0 == list.size();
            assert 1 == allFieldsC.size();
            assert 1 == allFieldsD.size();
            assert 1 == allFieldsE.size();
            assert 2 == allFieldsF.size();
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void getWhereSQL() {
        try {
            Method getWhereSQL = baseProvider.getClass().getDeclaredMethod("getWhereSQL", Object.class);
            getWhereSQL.setAccessible(true);
            String a = (String) getWhereSQL.invoke(baseProvider, TeaC132cHeerA.builder().build());
            String b = (String) getWhereSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            String c = (String) getWhereSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            String d = (String) getWhereSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String e = (String) getWhereSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert StringUtils.isEmpty(a);
            assert strEqual(" where aaa = #{abc} ",b);
            assert strEqual(" where aaa = #{abc} ",c);
            assert strEqual(" where aaa = #{abc} ",d);
            assert strEqual(" where abc_def_d = #{abcDefD}  and aaa = #{abc} ",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }


    @Test
    void getFromSQL() {
        try {
            Method getFromSQL = baseProvider.getClass().getDeclaredMethod("getFromSQL", Class.class);
            getFromSQL.setAccessible(true);
            String a = (String) getFromSQL.invoke(baseProvider, TeaC132cHeerA.class);
            String b = (String) getFromSQL.invoke(baseProvider, TeaC132cHeerB.class);
            assert strEqual(" from  tea_c132c_heer_a",a);
            assert strEqual(" from  tt",b);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void getSelectSQL() {
        try {
            Method getSelectSQL = baseProvider.getClass().getDeclaredMethod("getSelectSQL", Class.class);
            getSelectSQL.setAccessible(true);
            String a = (String) getSelectSQL.invoke(baseProvider, TeaC132cHeerA.class);
            String b = (String) getSelectSQL.invoke(baseProvider, TeaC132cHeerB.class);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc",a);
            assert strEqual("select id as id",b);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void findByEntity() {
        try {
            Method findByEntity = baseProvider.getClass().getDeclaredMethod("findByEntity", Object.class);
            findByEntity.setAccessible(true);
            String a = (String) findByEntity.invoke(baseProvider, TeaC132cHeerA.builder().build());
            String b = (String) findByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            String c = (String) findByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            String d = (String) findByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String e = (String) findByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a ",a);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where aaa = #{abc} ",b);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where aaa = #{abc} ",c);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where aaa = #{abc} ",d);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where abc_def_d = #{abcDefD}  and aaa = #{abc} ",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void getByEntity() {
        try {
            Method getByEntity = baseProvider.getClass().getDeclaredMethod("getByEntity", Object.class);
            getByEntity.setAccessible(true);
            String a = (String) getByEntity.invoke(baseProvider, TeaC132cHeerA.builder().build());
            String b = (String) getByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            String c = (String) getByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            String d = (String) getByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String e = (String) getByEntity.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc from  tea_c132c_heer_a  limit 1",a);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc from  tea_c132c_heer_a  where aaa = #{abc}  limit 1",b);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where aaa = #{abc}  limit 1",c);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc    from  tea_c132c_heer_a  where aaa = #{abc}  limit 1",d);
            assert strEqual("select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where abc_def_d = #{abcDefD}  and aaa = #{abc}  limit 1",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void getByPrimaryKey() {
        try {
            /**
             Method getByPrimaryKey = baseProvider.getClass().getDeclaredMethod("getByPrimaryKey", Object.class,Class.class);
             getByPrimaryKey.setAccessible(true);
             String a = (String) getByPrimaryKey.invoke(baseProvider, 1L,TeaC132cHeerA.class);
             String b = (String) getByPrimaryKey.invoke(baseProvider, null,TeaC132cHeerA.class);
             assert "select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  where aaa = #{abc}  limit 1",a);
             assert "select abc_def_d as abcDefD,abc_def_de as AbcDefDe,aaa as abc  from  tea_c132c_heer_a  limit 1",b);
             **/
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void getSetSQL() {
        try {
            Method getSetSQL = baseProvider.getClass().getDeclaredMethod("getSetSQL", Object.class, Boolean.class);
            getSetSQL.setAccessible(true);
            String a = (String) getSetSQL.invoke(baseProvider, TeaC132cHeerA.builder().build(), true);
            String b = (String) getSetSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build(), true);
            String c = (String) getSetSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build(), false);
            String d = (String) getSetSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build(), true);
            String e = (String) getSetSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build(), false);

            assert strEqual("set ",a);
            assert strEqual("set aaa = #{abc} ",b);
            assert strEqual("set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc} ",c);
            assert strEqual("set aaa = #{abc} ",d);
            assert strEqual("set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc} ",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }


    @Test
    void getUpdateSQL() {
        try {
            Method getUpdateSQL = baseProvider.getClass().getDeclaredMethod("getUpdateSQL", Class.class);
            getUpdateSQL.setAccessible(true);
            String strA = (String) getUpdateSQL.invoke(baseProvider, TeaC132cHeerA.class);
            String strB = (String) getUpdateSQL.invoke(baseProvider, TeaC132cHeerB.class);
            assert strEqual("update tea_c132c_heer_a",strA);
            assert strEqual("update tt",strB);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void getValuesSQL() {
        try {
            Method getValuesSQL = baseProvider.getClass().getDeclaredMethod("getValuesSQL", Object.class, Boolean.class);
            getValuesSQL.setAccessible(true);
            String a = (String) getValuesSQL.invoke(baseProvider, TeaC132cHeerA.builder().build(), true);
            String b = (String) getValuesSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build(), true);
            String c = (String) getValuesSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build(), false);
            String d = (String) getValuesSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build(), true);
            String e = (String) getValuesSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build(), false);

            assert strEqual("",a);
            assert strEqual(" values ( #{abc} )",b);
            assert strEqual(" values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",c);
            assert strEqual(" values ( #{abc} )",d);
            assert strEqual(" values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void getInsertSQL() {
        try {
            Method getInsertSQL = baseProvider.getClass().getDeclaredMethod("getInsertSQL", Object.class, Boolean.class);
            getInsertSQL.setAccessible(true);
            String a = (String) getInsertSQL.invoke(baseProvider, TeaC132cHeerA.builder().build(), true);
            String b = (String) getInsertSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build(), true);
            String c = (String) getInsertSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build(), false);
            String d = (String) getInsertSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build(), true);
            String e = (String) getInsertSQL.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build(), false);

            assert strEqual("",a);
            assert strEqual("insert into tea_c132c_heer_a(aaa)",b);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)",c);
            assert strEqual("insert into tea_c132c_heer_a(aaa)",d);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    void insertActurally() {
        try {
            Method insertActurally = baseProvider.getClass().getDeclaredMethod("insertActurally", Object.class, Boolean.class);
            insertActurally.setAccessible(true);
            String a = (String) insertActurally.invoke(baseProvider, TeaC132cHeerA.builder().build(), true);
            String b = (String) insertActurally.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build(), true);
            String c = (String) insertActurally.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build(), false);
            String d = (String) insertActurally.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build(), true);
            String e = (String) insertActurally.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build(), false);

            assert strEqual(" ",a);
            assert strEqual("insert into tea_c132c_heer_a(aaa)  values ( #{abc} )",b);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",c);
            assert strEqual("insert into tea_c132c_heer_a(aaa)  values ( #{abc} )",d);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void insertIgnoreNull() {
        try {
            Method insertIgnoreNull = baseProvider.getClass().getDeclaredMethod("insertIgnoreNull", Object.class);
            insertIgnoreNull.setAccessible(true);
            String a = (String) insertIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().build());
            String b = (String) insertIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            String c = (String) insertIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            String d = (String) insertIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String e = (String) insertIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual(" ",a);
            assert strEqual("insert into tea_c132c_heer_a(aaa)  values ( #{abc} )",b);
            assert strEqual("insert into tea_c132c_heer_a(aaa)  values ( #{abc} )",c);
            assert strEqual("insert into tea_c132c_heer_a(aaa)  values ( #{abc} )",d);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,aaa)  values ( #{abcDefD} , #{abc} )",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void insertNoIgnoreNull() {
        try {
            Method insertNoIgnoreNull = baseProvider.getClass().getDeclaredMethod("insertNoIgnoreNull", Object.class);
            insertNoIgnoreNull.setAccessible(true);
            String a = (String) insertNoIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().build());
            String b = (String) insertNoIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(0L).build());
            String c = (String) insertNoIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).build());
            String d = (String) insertNoIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String e = (String) insertNoIgnoreNull.invoke(baseProvider, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",a);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",b);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",c);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",d);
            assert strEqual("insert into tea_c132c_heer_a(abc_def_d,abc_def_de,aaa)  values ( #{abcDefD} , #{AbcDefDe} , #{abc} )",e);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    void updateByPrimaryActurally() {
        try {
            Method updateByPrimaryActurally = baseProvider.getClass().getDeclaredMethod("updateByPrimaryActurally", Object.class, Object.class, Boolean.class);
            updateByPrimaryActurally.setAccessible(true);
            String a = (String) updateByPrimaryActurally.invoke(baseProvider, 1L, TeaC132cHeerA.builder().build(), true);
            String b = (String) updateByPrimaryActurally.invoke(baseProvider, 2L, TeaC132cHeerA.builder().AbcDefDe(0L).build(), true);
            String c = (String) updateByPrimaryActurally.invoke(baseProvider, 3L, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build(), true);
            String d = (String) updateByPrimaryActurally.invoke(baseProvider, 4L, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build(), false);

            assert strEqual("update tea_c132c_heer_a set   where aaa = #{abc} ",a);
            assert strEqual("update tea_c132c_heer_a set abc_def_de = #{AbcDefDe}   where aaa = #{abc} ",b);
            assert strEqual("update tea_c132c_heer_a set aaa = #{abc}   where aaa = #{abc} ",c);
            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc}   where aaa = #{abc} ",d);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
    @Test
    void updateByPrimaryIgnoreNull() {
        try {
            Method updateByPrimaryIgnoreNull = baseProvider.getClass().getDeclaredMethod("updateByPrimaryIgnoreNull", Object.class, Object.class);
            updateByPrimaryIgnoreNull.setAccessible(true);
            String a = (String) updateByPrimaryIgnoreNull.invoke(baseProvider, 1L, TeaC132cHeerA.builder().build());
            String b = (String) updateByPrimaryIgnoreNull.invoke(baseProvider, 2L, TeaC132cHeerA.builder().AbcDefDe(0L).build());
            String c = (String) updateByPrimaryIgnoreNull.invoke(baseProvider, 3L, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String d = (String) updateByPrimaryIgnoreNull.invoke(baseProvider, 4L, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual("update tea_c132c_heer_a set   where aaa = #{abc} ",a);
            assert strEqual("update tea_c132c_heer_a set abc_def_de = #{AbcDefDe}   where aaa = #{abc} ",b);
            assert strEqual("update tea_c132c_heer_a set aaa = #{abc}   where aaa = #{abc} ",c);
            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,aaa = #{abc}   where aaa = #{abc} ",d);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }


    @Test
    void updateByPrimaryNoIgnoreNull() {
        try {
            Method updateByPrimaryNoIgnoreNull = baseProvider.getClass().getDeclaredMethod("updateByPrimaryNoIgnoreNull", Object.class, Object.class);
            updateByPrimaryNoIgnoreNull.setAccessible(true);
            String a = (String) updateByPrimaryNoIgnoreNull.invoke(baseProvider, 1L, TeaC132cHeerA.builder().build());
            String b = (String) updateByPrimaryNoIgnoreNull.invoke(baseProvider, 2L, TeaC132cHeerA.builder().AbcDefDe(0L).build());
            String c = (String) updateByPrimaryNoIgnoreNull.invoke(baseProvider, 3L, TeaC132cHeerA.builder().abc(1L).abcDefD(null).build());
            String d = (String) updateByPrimaryNoIgnoreNull.invoke(baseProvider, 4L, TeaC132cHeerA.builder().abc(1L).abcDefD("sdwd").build());

            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc}   where aaa = #{abc} ",a);
            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc}   where aaa = #{abc} ",b);
            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc}   where aaa = #{abc} ",c);
            assert strEqual("update tea_c132c_heer_a set abc_def_d = #{abcDefD} ,abc_def_de = #{AbcDefDe} ,aaa = #{abc}   where aaa = #{abc} ",d);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
    @Test
    void getEntityByClassAndPrimaryKey(){
        try {
            Method getEntityByClassAndPrimaryKey = baseProvider.getClass().getDeclaredMethod("getEntityByClassAndPrimaryKey", Class.class, Object.class);
            getEntityByClassAndPrimaryKey.setAccessible(true);
            TeaC132cHeerA a = (TeaC132cHeerA) getEntityByClassAndPrimaryKey.invoke(baseProvider, TeaC132cHeerA.class, 1L);
            assert  a.equals(TeaC132cHeerA.builder().abc(1L).build());


            TeaC132cHeerA b = (TeaC132cHeerA) getEntityByClassAndPrimaryKey.invoke(baseProvider, TeaC132cHeerA.class, 1L);
            assert  !b.equals(TeaC132cHeerA.builder().abc(10L).build());
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
    private static boolean strEqual(String str1,String str2){
        return trimStr(str1).equals(trimStr(str2));
    }

    private static String trimStr(String str){
        return str.trim().replaceAll(" +", " ");
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeaC132cHeerA {
        private String abcDefD;
        private Long AbcDefDe;
        @Column("aaa")
        @Primary
        private Long abc;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeaC132cHeerA that = (TeaC132cHeerA) o;
            return Objects.equals(abcDefD, that.abcDefD) && Objects.equals(AbcDefDe, that.AbcDefDe) && Objects.equals(abc, that.abc);
        }

        @Override
        public int hashCode() {
            return Objects.hash(abcDefD, AbcDefDe, abc);
        }
    }

    @Table("tt")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeaC132cHeerB {
        @Primary
        private Long id;
    }

    public static class TeaC132cHeerC {
        private Long abcDefD;
        private Long AbcDefDe;
        @Column("aaa")
        private Long abc;

        private static Long a;
        @Ignore
        private String c;
    }
}
