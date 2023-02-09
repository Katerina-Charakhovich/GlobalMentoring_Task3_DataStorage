package com.epam.datastorage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.*;

@Repository
public class FileRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_FILE_PROCEDURE = "CALL `data_storage`.`insert_file`(?, ?)";
    private static final String GET_FILE_PROCEDURE = "CALL `data_storage`.`get_file`(?)";
    //   private static final String GET_FILE_PROCEDURE = "? = CALL `data_storage`.`get_fileById`(?)";

    @Autowired
    public FileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> save(FileEntity fileEntity) {
        List paramList = new ArrayList();
        paramList.add(new SqlParameter(Types.VARCHAR));
        paramList.add(new SqlParameter(Types.BLOB));


        CallableStatementCreator callableStatementCreator = new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(INSERT_FILE_PROCEDURE);
                callableStatement.setString(1, fileEntity.getFileName());
                callableStatement.setBlob(2, new ByteArrayInputStream(fileEntity.getContent()));
                return callableStatement;
            }
        };

        return jdbcTemplate.call(callableStatementCreator, paramList);
    }

    public Optional<FileEntity> findById(Integer id) {
        List paramList = new ArrayList();
        paramList.add(new SqlParameter("id", Types.INTEGER));
        CallableStatementCreator callableStatementCreator = new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                CallableStatement callableStatement = connection.prepareCall(GET_FILE_PROCEDURE);
                callableStatement.setLong(1, id);
                return callableStatement;
            }
        };
        var out = jdbcTemplate.call(callableStatementCreator, paramList);
        List<FileEntity> fileEntities = mapFileEntities(out, 1);
        return fileEntities.size() > 0 ? ofNullable(fileEntities.get(0)) : null;
    }

    public List<FileEntity> mapFileEntities(Map<String, Object> out, int resultSetPosition) {
        List<FileEntity> fileEntities = new ArrayList<FileEntity>();
        List<Map<String, Object>> results = (List<Map<String, Object>>) out.get("#result-set-" + resultSetPosition);
        results.forEach(u -> {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId((Integer) u.get("id"));
            fileEntity.setFileName((String) u.get("fileName"));
            fileEntity.setContent((byte[]) u.get("content"));
            fileEntities.add(fileEntity);
        });
        return fileEntities;
    }
}
