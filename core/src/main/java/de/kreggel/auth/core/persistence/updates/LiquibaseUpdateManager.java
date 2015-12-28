package de.kreggel.auth.core.persistence.updates;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.google.common.base.Strings;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public final class LiquibaseUpdateManager{
    /**
     * The classpath path to the changelog.xml.
     *
     * Liquibase uses id+author+changelogPath as PK for the updates. So if this is changed, Liquibase will detect all
     * updates as new and will apply them.
     *
     * This path is overridden, though, via the fixed "logicalFilePath" attribute in the "databaseChangeLog" tag of
     * the changelog.xml. So if need be, this physical path may be changed.
     */
    private static final String CHANGELOG = "db/changelog.xml";

    private static final String EXISTING_CONTEXT = "existing";

    private static final String NEW_CONTEXT = "new";

    private final DataSource dataSource;
    //a table which must exist for sure for the database to be a valid fNs database
    private final String probeTableName;

    private boolean checkIfNewInstallation;

    public LiquibaseUpdateManager(DataSource dataSource) {
        this(dataSource, null, false);
    }

    public LiquibaseUpdateManager(DataSource dataSource, String probeTableName) {
        this(dataSource, probeTableName, true);
    }

    public LiquibaseUpdateManager(DataSource dataSource, String probeTableName, boolean checkIfNewInstallation) {
        this.dataSource = dataSource;
        this.probeTableName = probeTableName;
        this.checkIfNewInstallation = checkIfNewInstallation;
    }

    public LiquibaseUpdateManager performUpdate() {
        boolean newInstallation = false;
        if(checkIfNewInstallation) {
            newInstallation = isNewInstallation();
        }

        return performUpdate(dataSource, newInstallation);
    }

    private boolean isNewInstallation() {
        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource must not be null");
        }
        if (Strings.isNullOrEmpty(probeTableName)) {
            throw new IllegalArgumentException("probeTableName must not be null or empty");
        }

        // check for new installation based on core tables
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, probeTableName, null);
            return !tables.next();
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Throwable e) {
                    //ignore
                }
            }
        }
    }

    public LiquibaseUpdateManager performUpdate(DataSource dataSource, boolean isNewInstallation) {

        if (dataSource == null) {
            throw new IllegalArgumentException("dataSource must not be null");
        }

        try {
            Connection connection = dataSource.getConnection();

            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                            new JdbcConnection(connection));

            Liquibase liquibase =
                    new Liquibase(CHANGELOG, new ClassLoaderResourceAccessor(), database);

            Contexts contexts = new Contexts(isNewInstallation ? NEW_CONTEXT : EXISTING_CONTEXT);

            liquibase.update(contexts);

            if (isNewInstallation) {
                // mark "existing" changesets also as applied - but actually don't apply them
                liquibase.changeLogSync(new Contexts(EXISTING_CONTEXT), null);
            }
        } catch (SQLException | LiquibaseException ex) {
            throw new IllegalStateException(ex);
        }

        return this;
    }


}