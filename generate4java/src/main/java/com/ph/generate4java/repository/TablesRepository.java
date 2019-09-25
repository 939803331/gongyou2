package com.ph.generate4java.repository;

import com.ph.generate4java.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @PACKAGE_NAME: com.ph.generate4java.responsitory
 * @NAME: TablesRepository
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 14:34
 **/
public interface TablesRepository extends JpaRepository<Tables, String> {

	@Query(nativeQuery = true, value = "select `TABLE_NAME`,`TABLE_COMMENT` from information_schema.TABLES where table_schema=:tableSchema and table_name = :tableName")
	Tables findByTableName(@Param("tableName") String table, @Param("tableSchema") String schema);
}
