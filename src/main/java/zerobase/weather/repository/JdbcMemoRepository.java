package zerobase.weather.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

// JDBC를 사용하는 Repository 예시
@Repository
public class JdbcMemoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "insert into memo values(?, ?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    public List findAll() {
        String sql = "select * from memo";
        // 조회할때는 query 사용
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    // Optional <- 혹시 모를 null값을 처리하기 쉽도록
    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }


    private RowMapper<Memo> memoRowMapper() {
        // ResultSet
        // {}
        return(rs, rowNum) -> new Memo(
            rs.getInt("id"),
            rs.getString("text")
        );
    }

}
