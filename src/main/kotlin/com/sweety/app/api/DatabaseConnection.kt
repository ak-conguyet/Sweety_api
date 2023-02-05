package com.sweety.app.api

import java.sql.Connection
import java.sql.DriverManager

class DatabaseConnection {
    val connection: Connection = DriverManager.getConnection("jdbc:sqlserver://${DatabaseConfig.host}:${DatabaseConfig.port};serverName=${DatabaseConfig.serverName};databaseName=${DatabaseConfig.databaseName};trustServerCertificate=true;",DatabaseConfig.User,DatabaseConfig.Password)

}



