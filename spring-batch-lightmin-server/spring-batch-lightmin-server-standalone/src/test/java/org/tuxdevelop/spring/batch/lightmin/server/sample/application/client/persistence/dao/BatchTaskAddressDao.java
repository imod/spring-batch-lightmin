package org.tuxdevelop.spring.batch.lightmin.server.sample.application.client.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.tuxdevelop.spring.batch.lightmin.server.sample.application.client.domain.BatchTaskAddress;

import java.sql.Types;

@Component
public class BatchTaskAddressDao {

    private static final String INSERT_STATEMENT =
            "INSERT INTO batch_task_address (" +
                    "processing_state," +
                    " street," +
                    " house_number," +
                    " zip_code," +
                    " city)" +
                    " VALUES (" +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?)";

    private static final String UPDATE_PROCESSING_STATE =
            "UPDATE batch_task_address SET processing_state = ?" +
                    " WHERE batch_task_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BatchTaskAddressDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(final BatchTaskAddress batchTaskAddress) {
        jdbcTemplate.update(INSERT_STATEMENT,
                new Object[]{
                        batchTaskAddress.getProcessingState(),
                        batchTaskAddress.getStreet(),
                        batchTaskAddress.getHouseNumber(),
                        batchTaskAddress.getZipCode(),
                        batchTaskAddress.getCity()},
                new int[]{
                        Types.NUMERIC,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR,
                        Types.VARCHAR});
    }

    public void updateProcessingState(final Long batchTaskId, final Long processingState) {
        jdbcTemplate.update(UPDATE_PROCESSING_STATE,
                new Object[]{
                        processingState,
                        batchTaskId},
                new int[]{
                        Types.NUMERIC,
                        Types.NUMERIC});
    }
}
