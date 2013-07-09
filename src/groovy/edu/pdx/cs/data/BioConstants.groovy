/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/4/13
 */

package edu.pdx.cs.data

class BioConstants {
	// DNA codons, with codons listed in standard genetic code order (TCAG table).
	public static final String TTT = "TTT"
	public static final String TTC = "TTC"
	public static final String TTA = "TTA"
	public static final String TTG = "TTG"
	public static final String CTT = "CTT"
	public static final String CTC = "CTC"
	public static final String CTA = "CTA"
	public static final String CTG = "CTG"
	public static final String ATT = "ATT"
	public static final String ATC = "ATC"
	public static final String ATA = "ATA"
	public static final String ATG = "ATG"
	public static final String GTT = "GTT"
	public static final String GTC = "GTC"
	public static final String GTA = "GTA"
	public static final String GTG = "GTG"
	public static final String TCT = "TCT"
	public static final String TCC = "TCC"
	public static final String TCA = "TCA"
	public static final String TCG = "TCG"
	public static final String CCT = "CCT"
	public static final String CCC = "CCC"
	public static final String CCA = "CCA"
	public static final String CCG = "CCG"
	public static final String ACT = "ACT"
	public static final String ACC = "ACC"
	public static final String ACA = "ACA"
	public static final String ACG = "ACG"
	public static final String GCT = "GCT"
	public static final String GCC = "GCC"
	public static final String GCA = "GCA"
	public static final String GCG = "GCG"
	public static final String TAT = "TAT"
	public static final String TAC = "TAC"
	public static final String TAA = "TAA"
	public static final String TAG = "TAG"
	public static final String CAT = "CAT"
	public static final String CAC = "CAC"
	public static final String CAA = "CAA"
	public static final String CAG = "CAG"
	public static final String AAT = "AAT"
	public static final String AAC = "AAC"
	public static final String AAA = "AAA"
	public static final String AAG = "AAG"
	public static final String GAT = "GAT"
	public static final String GAC = "GAC"
	public static final String GAA = "GAA"
	public static final String GAG = "GAG"
	public static final String TGT = "TGT"
	public static final String TGC = "TGC"
	public static final String TGA = "TGA"
	public static final String TGG = "TGG"
	public static final String CGT = "CGT"
	public static final String CGC = "CGC"
	public static final String CGA = "CGA"
	public static final String CGG = "CGG"
	public static final String AGT = "AGT"
	public static final String AGC = "AGC"
	public static final String AGA = "AGA"
	public static final String AGG = "AGG"
	public static final String GGT = "GGT"
	public static final String GGC = "GGC"
	public static final String GGA = "GGA"
	public static final String GGG = "GGG"
	
	// List of DNA non-STOP codons for amino acids with degenerate codings,
	// used in some of our measures, such as RSCU.
	public static final List<String> codons = [
		TTT, TTC, TTA, TTG, CTT, CTC, CTA, CTG, ATT, ATC, ATA, GTT, GTC, GTA, GTG,
		TCT, TCC, TCA, TCG, CCT, CCC, CCA, CCG, ACT, ACC, ACA, ACG, GCT, GCC, GCA, GCG,
		TAT, TAC, CAT, CAC, CAA, CAG, AAT, AAC, AAA, AAG, GAT, GAC, GAA, GAG,
		TGT, TGC, CGT, CGC, CGA, CGG, AGT, AGC, AGA, AGG, GGT, GGC, GGA, GGG
	]
	
	// List of all DNA codons.
	public static final List<String> allCodons = [
		TTT, TTC, TTA, TTG, CTT, CTC, CTA, CTG, ATT, ATC, ATA, ATG, GTT, GTC, GTA, GTG,
		TCT, TCC, TCA, TCG, CCT, CCC, CCA, CCG, ACT, ACC, ACA, ACG, GCT, GCC, GCA, GCG,
		TAT, TAC, TAA, TAG, CAT, CAC, CAA, CAG, AAT, AAC, AAA, AAG, GAT, GAC, GAA, GAG,
		TGT, TGC, TGA, TGG, CGT, CGC, CGA, CGG, AGT, AGC, AGA, AGG, GGT, GGC, GGA, GGG
	]
	
	// List of DNA codons ignored during some measurements, such as RSCU.
	// (STOP codons, and methionine and tryptophan (single-coded amino acids)).
	// Can be used with allCodons List to achieve same effect as codons List. 
	public static final List<String> ignoredCodons = [
		ATG, TAA, TAG, TGA, TGG
	]
	
	// The first DNA codon in each value List is always the same as that particular map key,
	// which might be useful in some applications.  Otherwise, each value's List[0]
	// can be ignored.  Codons can have 1, 2, 3, 4, or 6 synonyms (including themselves).
	// (Parentheses enclosing keys here are used to avoid a parsing problem in this
	// somewhat complex initialization.)
	public static final Map<String, List<String>> synonyms = [
		// Phenylalanine (F)
		(TTT):[TTT, TTC],
		(TTC):[TTC, TTT],
		// Leucine (L)
		(TTA):[TTA, TTG, CTT, CTC, CTA, CTG],
		(TTG):[TTG, TTA, CTT, CTC, CTA, CTG],
		(CTT):[CTT, TTA, TTG, CTC, CTA, CTG],
		(CTC):[CTC, TTA, TTG, CTT, CTA, CTG],
		(CTA):[CTA, TTA, TTG, CTT, CTC, CTG],
		(CTG):[CTG, TTA, TTG, CTT, CTC, CTA],
		// Isoleucine (I)
		(ATT):[ATT, ATC, ATA],
		(ATC):[ATC, ATT, ATA],
		(ATA):[ATA, ATT, ATC],
		// Methionine (M)
		(ATG):[ATG],
		// Valine (V)
		(GTT):[GTT, GTC, GTA, GTG],
		(GTC):[GTC, GTT, GTA, GTG],
		(GTA):[GTA, GTT, GTC, GTG],
		(GTG):[GTG, GTT, GTC, GTA],
		// Serine (S)
		(TCT):[TCT, TCC, TCA, TCG, AGT, AGC],
		(TCC):[TCC, TCT, TCA, TCG, AGT, AGC],
		(TCA):[TCA, TCT, TCC, TCG, AGT, AGC],
		(TCG):[TCG, TCT, TCC, TCA, AGT, AGC],
		(AGT):[AGT, TCT, TCC, TCA, TCG, AGC],
		(AGC):[AGC, TCT, TCC, TCA, TCG, AGT],
		// Proline (P)
		(CCT):[CCT, CCC, CCA, CCG],
		(CCC):[CCC, CCT, CCA, CCG],
		(CCA):[CCA, CCT, CCC, CCG],
		(CCG):[CCG, CCT, CCC, CCA],
		// Threonine (T)
		(ACT):[ACT, ACC, ACA, ACG],
		(ACC):[ACC, ACT, ACA, ACG],
		(ACA):[ACA, ACT, ACC, ACG],
		(ACG):[ACG, ACT, ACC, ACA],
		// Alanine (A)
		(GCT):[GCT, GCC, GCA, GCG],
		(GCC):[GCC, GCT, GCA, GCG],
		(GCA):[GCA, GCT, GCC, GCG],
		(GCG):[GCG, GCT, GCC, GCA],
		// Tyrosine (Y)
		(TAT):[TAT, TAC],
		(TAC):[TAC, TAT],
		// The STOP codons.  These are probably NOT considered synonymous or used in our measures.
		(TAA):[TAA, TAG, TGA],
		(TAG):[TAG, TAA, TGA],
		(TGA):[TGA, TAA, TAG],
		// Histidine (H)
		(CAT):[CAT, CAC],
		(CAC):[CAC, CAT],
		// Glutamine (Q)
		(CAA):[CAA, CAG],
		(CAG):[CAG, CAA],
		// Asparagine (N)
		(AAT):[AAT, AAC],
		(AAC):[AAC, AAT],
		// Lysine (K)
		(AAA):[AAA, AAG],
		(AAG):[AAG, AAA],
		// Aspartic acid (D)
		(GAT):[GAT, GAC],
		(GAC):[GAC, GAT],
		// Glutamic acid (E)
		(GAA):[GAA, GAG],
		(GAG):[GAG, GAA],
		// Cysteine (C)
		(TGT):[TGT, TGC],
		(TGC):[TGC, TGT],
		// Tryptophan (W)
		(TGG):[TGG],
		// Arginine (R)
		(CGT):[CGT, CGC, CGA, CGG, AGA, AGG],
		(CGC):[CGC, CGT, CGA, CGG, AGA, AGG],
		(CGA):[CGA, CGT, CGC, CGG, AGA, AGG],
		(CGG):[CGG, CGT, CGC, CGA, AGA, AGG],
		(AGA):[AGA, CGT, CGC, CGA, CGG, AGG],
		(AGG):[AGG, CGT, CGC, CGA, CGG, AGA],
		// Glycine (G)
		(GGT):[GGT, GGC, GGA, GGG],
		(GGC):[GGC, GGT, GGA, GGG],
		(GGA):[GGA, GGT, GGC, GGG],
		(GGG):[GGG, GGT, GGC, GGA]
	]

    // map of codons by amino acid
    public static final Map<String, List<String>> aminoAcids = [
        Ala: [GCT, GCC, GCA, GCG],
        Arg: [CGT, CGC, CGA, CGG, AGA, AGG],
        Asn: [AAT, AAC],
        Asp: [GAT, GAC],
        Cys: [TGT, TGC],
        Gln: [CAA, CAG],
        Glu: [GAA, GAG],
        Gly: [GGT, GGC, GGA, GGG],
        His: [CAT, CAC],
        Ile: [ATT, ATC, ATA],
        Leu: [TTA, TTG, CTT, CTC, CTA, CTG],
        Lys: [AAA, AAG],
        Met: [ATG],
        Phe: [TTT, TTC],
        Pro: [CCT, CCC, CCA, CCG],
        Ser: [TCT, TCC, TCA, TCG, AGT, AGC],
        Thr: [ACT, ACC, ACA, ACG],
        Trp: [TGG],
        Tyr: [TAT, TAC],
        Val: [GTT, GTC, GTA, GTG],
        Stop: [TAA, TGA, TAG]
    ]
}
