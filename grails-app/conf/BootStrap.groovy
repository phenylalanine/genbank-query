import edu.pdx.cs.data.GCPercentageProcessor
import edu.pdx.cs.data.GenBankFTPClient
import edu.pdx.cs.data.MeanCodonUsageProcessor
import edu.pdx.cs.data.OrganismProcessor
import edu.pdx.cs.data.RSCUProcessor
import groovy.sql.Sql
import org.biojava.bio.seq.DNATools
import org.biojava.bio.symbol.Alphabet
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator
import org.hsqldb.cmdline.SqlFile
import webapp.Organism

import java.sql.Connection
import java.sql.DriverManager

import org.apache.shiro.crypto.hash.Sha512Hash


class BootStrap {
    def init = { servletContext ->
        println "Application starting up... "

        environments {
            test {
                createBioSQLTables("jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
                createFakeData()
                createAdmin()
            }
            // (JGM) Use this environment to use test data stored on the file system.
            // E.g.: grails -Dgrails.env=persistentTest test-app -echoOut --stacktrace -integration edu.pdx.cs.data.ProcessorComparatorTests
            persistentTest {
                File f = new File("Db/Db-is-converted")
                if (!f.exists()) {
                    createBioSQLTables("jdbc:h2:file:Db/testDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
                    createFakeDataAndCalculate()
                }
            }
            development {
                createBioSQLTables("jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000")
                createFakeData()
                createAdmin()
            }
            production {
                //we dont know what to do here just yet, we definitely don't want to drop
                //all of the tables on a restart of the application

                // get some real data for demo
                GenBankFTPClient client = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
                List<String> remoteFiles = client.getAllFilesInDirectory("genbank"){
                    it.isFile() && it.name.endsWith("seq.gz")}[0..4]
                OrganismProcessor processor = new OrganismProcessor()

                RichSequence sequence
                BufferedReader reader
                RichSequenceIterator sequences

                for (remoteFile in remoteFiles) {
                    reader = client.readRemoteFile(remoteFile)
                    sequences = RichSequence.IOTools.readGenbankDNA(reader, null)

                    while (sequences.hasNext()) {
                        sequence = sequences.nextRichSequence()
                        processor.process(sequence)
                    }
                }
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
    }

    def createFakeData() {
        int ORGS = 200 // number of organisms to create for testing

        // check whether the test data already exists
        if (!Organism.count()) {

            Random random = new Random()
            def nucleotides = ['t', 'c', 'a', 'g'] as char[]
            Map aaDistribution // distribution of codon usage in the sequence
            String key // key codon
            double fraction // used for codon fraction assignment

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

    def createFakeDataAndCalculate() {
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

            // (JGM) Create test data:  5000 to 10000 codons for each organism
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

                // (JGM) Create organisms that have real MCUF, etc., analyses based on the random codon data
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

    def createAdmin() {
        def adminUser = new ShiroUser(username: "admin",
                passwordHash: new Sha512Hash("password").toHex())
        adminUser.addToPermissions("*:*")
        adminUser.save()
    }
}
