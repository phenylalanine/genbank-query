import groovy.sql.Sql
import org.hsqldb.cmdline.SqlFile

import edu.pdx.cs.data.GCPercentageProcessor
import edu.pdx.cs.data.MeanCodonUsageProcessor
import edu.pdx.cs.data.RSCUProcessor
import webapp.Organism

import java.sql.Connection
import java.sql.DriverManager

import org.biojava.bio.symbol.*
import org.biojava.bio.seq.*
import org.biojavax.bio.seq.RichSequence


class BootStrap {
    def init = { servletContext ->
        println "Application starting up... "

        environments {
            test {
				createBioSQLTables("jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")

            }
			persistentTest {
				File f = new File("Db-is-converted")
				if (!f.exists()) {
					createBioSQLTables("jdbc:h2:file:Db/testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
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
        // check whether the test data already exists
		if (!Organism.count()) {
			int ORGS = 200   // number of organisms to create for testing
			Alphabet dnaAlphabet = DNATools.getDNA();
            Random random = new Random()
            List<String> nucleotides = ["t", "c", "a", "g"]
			String c1, c2, c3
			String nucleotideSeq
			RichSequence richSeq
			int numCodons

            // create test data
            for (int i = 1; i <= ORGS; i++) {

				nucleotideSeq = ""
				numCodons = random.nextInt(10000 - 5000 + 1) + 5000
				for (int j = 1; j <= numCodons; j++) {
					c1 = nucleotides.get(random.nextInt(4))
					c2 = nucleotides.get(random.nextInt(4))
					c3 = nucleotides.get(random.nextInt(4))
					nucleotideSeq += (c1 + c2 + c3)
				}

				richSeq = RichSequence.Tools.createRichSequence("temp", nucleotideSeq, dnaAlphabet)

                new Organism(
                        organismId: i,
                        scientificName: "Organismus Numberus " + i.toString(),
                        taxonomyId: random.nextInt(1340000) + 1000,
						mcufCodonDistribution: (new MeanCodonUsageProcessor()).process(richSeq),
						rscuCodonDistribution: (new RSCUProcessor()).process(richSeq),
						gcPercentage: (new GCPercentageProcessor()).process(richSeq)
                ).save(failOnError: true)
            }
        }
    }
}
