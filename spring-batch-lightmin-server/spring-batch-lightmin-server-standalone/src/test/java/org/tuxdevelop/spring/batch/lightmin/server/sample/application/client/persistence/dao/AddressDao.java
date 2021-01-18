package org.tuxdevelop.spring.batch.lightmin.server.sample.application.client.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.tuxdevelop.spring.batch.lightmin.server.sample.application.client.domain.Address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Component
public class AddressDao {

    private static final String INSERT_STATEMENT =
            "INSERT INTO address (" +
                    "id," +
                    "street_line," +
                    " city_line)" +
                    "VALUES(" +
                    "?," +
                    "?," +
                    "?)";

    private static final String GET_BY_ID_QUERY =
            "SELECT * FROM address" +
                    " WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(final Address address) {
        jdbcTemplate.update(INSERT_STATEMENT, new Object[]{address.getId(), address.getStreetLine(), address
                .getCityLine()}, new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
    }

    public Address getAddressById(final Long id) {
        return jdbcTemplate.queryForObject(GET_BY_ID_QUERY,
                new Object[]{id},
                new int[]{Types.NUMERIC},
                new AddressRowMapper());
    }


    private static class AddressRowMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(final ResultSet resultSet, final int i) throws SQLException {

            final Address address = new Address();
            address.setId(resultSet.getLong("id"));
            address.setCityLine(resultSet.getString("city_line"));
            address.setStreetLine(resultSet.getString("street_line"));
            return address;

        }
    }

}
