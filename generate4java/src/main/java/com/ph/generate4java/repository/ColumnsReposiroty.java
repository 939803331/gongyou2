package com.ph.generate4java.repository;

import com.ph.generate4java.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @PACKAGE_NAME: com.ph.generate4java.repository
 * @NAME: ColumnsReposiroty
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 14:36
 **/
public interface ColumnsReposiroty extends JpaRepository<Columns, String> {

	@Query(nativeQuery = true, value = "select `COLUMN_NAME`,`DATA_TYPE`,`COLUMN_COMMENT`,`IS_NULLABLE` from information_schema.columns where table_schema=:tableSchema and table_name=:tableName order by `ORDINAL_POSITION` ASC ")
	List<Columns> findByTableName(@Param("tableName") String table, @Param("tableSchema") String schema);
}
