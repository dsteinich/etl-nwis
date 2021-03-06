package gov.acwi.wqp.etl.projectMLWeighting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import gov.acwi.wqp.etl.NwisBaseFlowIT;

public class TransformProjectMLWeightingIT extends NwisBaseFlowIT {

    public static final String TABLE_NAME = "'prj_ml_weighting_swap_nwis'";
    public static final String EXPECTED_DATABASE_QUERY_ANALYZE = BASE_EXPECTED_DATABASE_QUERY_ANALYZE + TABLE_NAME;
    public static final String EXPECTED_DATABASE_QUERY_PRIMARY_KEY = BASE_EXPECTED_DATABASE_QUERY_PRIMARY_KEY
            + EQUALS_QUERY + TABLE_NAME;
    public static final String EXPECTED_DATABASE_QUERY_FOREIGN_KEY = BASE_EXPECTED_DATABASE_QUERY_FOREIGN_KEY
            + EQUALS_QUERY + TABLE_NAME;

    @Autowired
    @Qualifier("projectMLWeightingFlow")
    private Flow projectMLWeightingFlow;

    @Test
    @ExpectedDatabase(
            value="classpath:/testResult/wqp/projectMLWeighting/indexes/all.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
            table=EXPECTED_DATABASE_TABLE_CHECK_INDEX,
            query=BASE_EXPECTED_DATABASE_QUERY_CHECK_INDEX + TABLE_NAME)
    @ExpectedDatabase(
            connection=CONNECTION_INFORMATION_SCHEMA,
            value="classpath:/testResult/wqp/projectMLWeighting/create.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
            table=EXPECTED_DATABASE_TABLE_CHECK_TABLE,
            query=BASE_EXPECTED_DATABASE_QUERY_CHECK_TABLE + TABLE_NAME)
    @ExpectedDatabase(
            value="classpath:/testResult/wqp/projectMLWeighting/projectMLWeighting.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED)
    @ExpectedDatabase(
            value="classpath:/testResult/wqp/analyze/projectMLWeighting.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
            table=EXPECTED_DATABASE_TABLE_CHECK_ANALYZE,
            query=EXPECTED_DATABASE_QUERY_ANALYZE)
    @ExpectedDatabase(
            value="classpath:/testResult/wqp/projectMLWeighting/primaryKey.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
            table=EXPECTED_DATABASE_TABLE_CHECK_PRIMARY_KEY,
            query=EXPECTED_DATABASE_QUERY_PRIMARY_KEY)
    @ExpectedDatabase(
            value="classpath:/testResult/wqp/projectMLWeighting/foreignKey.xml",
            assertionMode=DatabaseAssertionMode.NON_STRICT_UNORDERED,
            table=EXPECTED_DATABASE_TABLE_CHECK_FOREIGN_KEY,
            query=EXPECTED_DATABASE_QUERY_FOREIGN_KEY)
    public void projectMLWeightingFlowTest() {
        Job projectMLWeightingFlowTest = jobBuilderFactory.get("projectMLWeightingFlowTest")
                .start(projectMLWeightingFlow)
                .build()
                .build();
        jobLauncherTestUtils.setJob(projectMLWeightingFlowTest);
        jdbcTemplate.execute("select add_monitoring_location_primary_key('nwis', 'wqp', 'station')");
        jdbcTemplate.execute("select add_project_data_primary_key('nwis', 'wqp', 'project_data')");
        try {
            JobExecution jobExecution = jobLauncherTestUtils.launchJob(testJobParameters);
            assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }

}