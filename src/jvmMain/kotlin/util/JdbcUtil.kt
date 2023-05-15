package util

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

/**
 * 数据类， 获得单粒对象jdbcTemplate
 */
object JdbcUtil {

    val jdbcClient = getBean()

    /**
     * 获取bean
     */
    private fun getBean() :JdbcTemplate {
        //1创建数据源（连接池）dbcp
        val dataSource = BasicDataSource()
        //基本四项
        dataSource.driverClassName = "org.sqlite.JDBC"
        dataSource.url = "jdbc:sqlite:mydesktop.db"

        //2创建模板
        val jdbcTemplate = JdbcTemplate()
        jdbcTemplate.dataSource = dataSource

        return jdbcTemplate
    }

}