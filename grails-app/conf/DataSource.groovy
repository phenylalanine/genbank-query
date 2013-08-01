import edu.pdx.cs.data.GenBankClient
import edu.pdx.cs.data.OrganismProcessor
import org.biojavax.bio.seq.RichSequence
import org.biojavax.bio.seq.RichSequenceIterator

dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
	// (JGM) Use this environment to use test data stored on the file system.
	// E.g.: grails -Dgrails.env=persistentTest test-app -echoOut --stacktrace -integration edu.pdx.cs.data.ProcessorComparatorTests
	persistentTest {
		dataSource {
			dbCreate = "update"
			url = "jdbc:h2:file:Db/testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		}
	}
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
        // get some real data for demo
        GenBankClient client = new GenBankClient(GenBankClient.GENBANK_FTP_URL)
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
