package cn.ajsgn.common.generator.test.main;

import java.sql.SQLException;

import cn.ajsgn.common.generator.core.Generator;
import cn.ajsgn.common.generator.db.TableConfig;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        mysqlTest();
    }

    static void mysqlTest() throws ClassNotFoundException, SQLException {

        Generator generator = new Generator("jdbc:mysql://192.168.1.11:3306/crawler?useEncoding=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false", "deve", "jfds@801", "com.mysql.jdbc.Driver");

//
//        for (TableConfig tableConfig : ModelStatic.account){
//            generator.addTable(tableConfig);
//        }
//        for (TableConfig tableConfig : ModelStatic.asset){
//            generator.addTable(tableConfig);
//        }
//        for (TableConfig tableConfig : ModelStatic.activity){
//            generator.addTable(tableConfig);
//        }
//        for (TableConfig tableConfig : ModelStatic.agreement){
//            generator.addTable(tableConfig);
//        }
//        for (TableConfig tableConfig : ModelStatic.assistant){
//            generator.addTable(tableConfig);
//        }
//        for (TableConfig tableConfig : ModelStatic.payment){
//            generator.addTable(tableConfig);
//        }
        for (TableConfig tableConfig : ModelStatic.enrolling){
            generator.addTable(tableConfig);
        }
        generator.generator().stream().forEach(table -> table.generatorAll());
    }

}
