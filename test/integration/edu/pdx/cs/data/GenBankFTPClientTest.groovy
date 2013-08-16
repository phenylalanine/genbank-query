package edu.pdx.cs.data

import org.junit.Ignore
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 7/6/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
class GenBankFTPClientTest {

    @Test
    void testGetAllFilesInDirectory() {
        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)

        //check that we list the right # of dirs with no filter
        def fileList = genbankClient.getAllFilesInDirectory("genbank")
        assert fileList.size() > 0

        //check that we list the right # of dirs with filter
        fileList = genbankClient.getAllFilesInDirectory("genbank") { file ->
            file.name.endsWith("Number")
        }

        assert fileList.size() > 0
        assert fileList[0] == "genbank/GB_Release_Number"
    }

    @Test
    void testGetAllFilesInDirectoryRecursively() {
        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)

        //check that we list the right # of dirs with no filter
        def fileList = genbankClient
                .recursivelyGetAllFilesInDirectory("genbank/catalog")
        assert !fileList.empty

        //check that we list the right # of dirs with a filter
        fileList = genbankClient
                .recursivelyGetAllFilesInDirectory("genbank/daily-nc") {
            it.name.endsWith("flat.gz")
        }
        assert !fileList.empty
    }


    @Test
    void testCreateGenomeAssemblyObjectsEuk() {

        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)

        def fileList = [
                'genbank/genomes/Eukaryotes/invertebrates/Acromyrmex_echinatior/Aech_3.9/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Acromyrmex_echinatior/Aech_3.9/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Acyrthosiphon_pisum/Acyr_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Acyrthosiphon_pisum/Acyr_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Amphimedon_queenslandica/v1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Amphimedon_queenslandica/v1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Anopheles_albimanus/Anop_albi_ALBI9_A_V1/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Anopheles_albimanus/Anop_albi_ALBI9_A_V1/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Anopheles_arabiensis/Anop_arab_DONG5_A_V1/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Anopheles_arabiensis/Anop_arab_DONG5_A_V1/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Apis_mellifera/Amel_4.5/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Apis_mellifera/Amel_4.5/Primary_Assembly/assembled_chromosomes/FASTA/lg1.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Apis_mellifera/Amel_4.5/Primary_Assembly/placed_scaffolds/FASTA/lg2.placed.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Apis_mellifera/Amel_4.5/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_albomicans/DroAlb_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_albomicans/DroAlb_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_biarmipes/Dbia_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_biarmipes/Dbia_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_biarmipes/Dbia_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_biarmipes/Dbia_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_bipectinata/Dbip_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_bipectinata/Dbip_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_bipectinata/Dbip_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_bipectinata/Dbip_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_elegans/Dele_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_elegans/Dele_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_elegans/Dele_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_elegans/Dele_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_eugracilis/Deug_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_eugracilis/Deug_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_eugracilis/Deug_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_eugracilis/Deug_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_ficusphila/Dfic_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_ficusphila/Dfic_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_ficusphila/Dfic_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_ficusphila/Dfic_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_kikkawai/Dkik_1.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_kikkawai/Dkik_1.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_kikkawai/Dkik_2.0/ASSEMBLY_INFO',
                'genbank/genomes/Eukaryotes/invertebrates/Drosophila_kikkawai/Dkik_2.0/Primary_Assembly/unplaced_scaffolds/FASTA/unplaced.scaf.fa.gz'
        ]

        def GenomeAssembly[] results = genbankClient.createGenomeAssemblyObjects(fileList, true)

        assert results.size() == 19

        results.each { genome ->
            assert genome.isEukarya
            assert !genome.taxonomyFile.isEmpty()
            assert genome.taxonomyFile.endsWith(genbankClient.getEukTaxonomyFileEnding())
            assert genome.sequenceFiles.size() > 0
            genome.sequenceFiles.each {
                assert it.endsWith(genbankClient.getEukSequenceFileEnding())
            }
        }
    }

    @Test
    void testCreateGenomeAssemblyObjectsBac() {

        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        def fileList = [
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011142.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011143.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011144.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011145.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011146.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011147.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011148.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011149.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011150.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011151.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011152.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011153.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011154.rpt',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011142.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011143.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011144.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011145.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011146.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011147.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_22_uid31135/AP011148.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011149.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011150.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011151.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011152.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011153.fna',
                'genbank/genomes/Bacteria/Acetobacter_pasteurianus_IFO_3283_26_uid31137/AP011154.fna']


        def GenomeAssembly[] results = genbankClient.createGenomeAssemblyObjects(fileList, false)

        assert results.size() == 13

        results.each { genome ->
            assert !genome.isEukarya
            assert !genome.taxonomyFile.isEmpty()
            assert genome.taxonomyFile.endsWith(genbankClient.getBacTaxonomyFileEnding())
            assert genome.sequenceFiles.size() == 1
            genome.sequenceFiles.each {
                assert it.endsWith(genbankClient.getBacSequenceFileEnding())
            }
        }
    }

    //  Time-consuming tests, good for occasional re-check
    @Ignore
    @Test
    void testGetEukaryaGemoneFiles() {

        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        def subgroup = "invertebrates"
        def fileList = genbankClient.getEukaryaGenomeFiles(subgroup)
        assert !fileList.empty

        assert fileList.each {
            it.taxonomyFile.endsWith("ASSEMBLY_INFO")
        }
        assert fileList.each {
            it.sequenceFiles.each { it.endsWith(".fa.gz") }
        }
    }


    @Ignore
    @Test
    void testGetBacteriaGemoneFiles() {

        def genbankClient = new GenBankFTPClient(GenBankFTPClient.GENBANK_FTP_URL)
        def fileList = genbankClient.getAllBacteriaGenomeFiles()
        assert !fileList.empty

        fileList.each { file ->
            println file
        }

        assert fileList.each {
            it.taxonomyFile.endsWith(".rpt")
        }
        assert fileList.each {
            it.sequenceFiles.each { it.endsWith(".fna") }
        }
    }
}

