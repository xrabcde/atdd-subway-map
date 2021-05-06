package wooteco.subway.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StationH2Dao implements StationRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public StationH2Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Station save(Station station) {
        String query = "INSERT INTO STATION (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, new String[]{"id"});
            ps.setString(1, station.getName());
            return ps;
        }, keyHolder);

        return new Station(Objects.requireNonNull(keyHolder.getKey()).longValue(), station.getName());
    }

    @Override
    public List<Station> findAll() {
        String query = "SELECT * FROM STATION";
        return jdbcTemplate.query(query, (rs, rn) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Station(id, name);
        });
    }

    @Override
    public Optional<Station> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
