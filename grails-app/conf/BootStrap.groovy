import groovy.sql.Sql
import org.hsqldb.cmdline.SqlFile
import webapp.Organism

import java.sql.Connection
import java.sql.DriverManager

class BootStrap {
    def init = { servletContext ->
        println "Application starting up... "

        environments {
            test {
				//createBioSQLTables("jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
				File f = new File("Db-is-converted")
				if (!f.exists()) {
					createBioSQLTables("jdbc:h2:file:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
					createFakeData()
				}
            }
            development {
                createBioSQLTables("jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
                createFakeData()
            }
            production {
                //we dont know what to do here just yet, we definitely don't want to drop
                //all of the tables on a restart of the application
            }
        }

    }

    def destroy = {
        println "Application shutting down... "
    }

    def createBioSQLTables(jdbcUrl) {
        new File("grails-schema").withWriter {
            def sql = Sql.newInstance(jdbcUrl, "sa", "", "org.h2.Driver")
            sql.eachRow("SHOW TABLES FROM PUBLIC") { table ->
                it.writeLine "-------------------------------------"
                it.writeLine "Table: ${table}:"
                sql.eachRow("""SHOW COLUMNS FROM ${table.TABLE_NAME} FROM PUBLIC""".toString()) { column ->
                    it.writeLine "column: ${column}"
                }
            }
        }

        //drop the grails generated tables
        File hsqlDbDropTables = new File("resources/biosql/hsqldb/drop-tables.sql")
        SqlFile sqlFile = new SqlFile(hsqlDbDropTables)
        Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")
        sqlFile.setConnection(connection)
        sqlFile.execute()

        //then create the BioSql tables
        File hsqlDbCreateSql = new File("resources/biosql/hsqldb/biosqldb-hsqldb.sql")
        sqlFile = new SqlFile(hsqlDbCreateSql)
        sqlFile.setConnection(connection)
        sqlFile.execute()

        connection.close()

        new File("biosql-schema").withWriter {
            def sql = Sql.newInstance(jdbcUrl, "sa", "", "org.h2.Driver")
            sql.eachRow("SHOW TABLES FROM PUBLIC") { table ->
                it.writeLine "-------------------------------------"
                it.writeLine "Table: ${table}:"
                sql.eachRow("""SHOW COLUMNS FROM ${table.TABLE_NAME} FROM PUBLIC""".toString()) { column ->
                    it.writeLine "column: ${column}"
                }
            }
        }
		
		File f = new File("Db-is-converted")
		f.createNewFile()
    }

    def createFakeData() {
        int ORGS = 200   // number of organisms to create for testing

        // check whether the test data already exists
        if (!Organism.count()) {

            Random random = new Random()
            def nucleotides = ['t', 'c', 'a', 'g'] as char[]
            Map aaDistribution  // distribution of codon usage in the sequence
            String key  // key codon
            double fraction    // used for codon fraction assignment

            // create test data
            for (int i = 1; i <= ORGS; i++) {
                // reset codon usage distribution
                aaDistribution = [:]

                // create and assign test codon usage data
                // for all possible keys
                for (int i1 = 0; i1 < 4; i1++) {
                    for (int i2 = 0; i2 < 4; i2++) {

                        for (int i3 = 0; i3 < 4; i3++) {

                            // generate key codon
                            char c1 = nucleotides[i1]
                            char c2 = nucleotides[i2]
                            char c3 = nucleotides[i3]
                            key = c1.toString() + c2.toString() + c3.toString()

                            // generate random (small) fraction <= 50%
                            fraction = (random.nextInt(5000) + 1) / 10000

                            // assign fraction value to the key
                            aaDistribution.put(key, fraction)

                        }
                    }
                }

                new Organism(
                        organismId: i,
                        scientificName: "Organismus Numberus " + i.toString(),
                        taxonomyId: random.nextInt(1340000) + 1000,
                        mcufCodonDistribution: aaDistribution,
                        rscuCodonDistribution: aaDistribution,
                        gcPercentage: random.nextFloat() * 20
                ).save(failOnError: true)
            }
        }
    }
}
