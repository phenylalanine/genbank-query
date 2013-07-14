/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 7/4/13
 */

package edu.pdx.cs.data

class BioConstants {
	// DNA codons, with codons listed in standard genetic code order (TCAG table).
	public static final String TTT = "ttt"
	public static final String TTC = "ttc"
	public static final String TTA = "tta"
	public static final String TTG = "ttg"
	public static final String CTT = "ctt"
	public static final String CTC = "ctc"
	public static final String CTA = "cta"
	public static final String CTG = "ctg"
	public static final String ATT = "att"
	public static final String ATC = "atc"
	public static final String ATA = "ata"
	public static final String ATG = "atg"
	public static final String GTT = "gtt"
	public static final String GTC = "gtc"
	public static final String GTA = "gta"
	public static final String GTG = "gtg"
	public static final String TCT = "tct"
	public static final String TCC = "tcc"
	public static final String TCA = "tca"
	public static final String TCG = "tcg"
	public static final String CCT = "cct"
	public static final String CCC = "ccc"
	public static final String CCA = "cca"
	public static final String CCG = "ccg"
	public static final String ACT = "act"
	public static final String ACC = "acc"
	public static final String ACA = "aca"
	public static final String ACG = "acg"
	public static final String GCT = "gct"
	public static final String GCC = "gcc"
	public static final String GCA = "gca"
	public static final String GCG = "gcg"
	public static final String TAT = "tat"
	public static final String TAC = "tac"
	public static final String TAA = "taa"
	public static final String TAG = "tag"
	public static final String CAT = "cat"
	public static final String CAC = "cac"
	public static final String CAA = "caa"
	public static final String CAG = "cag"
	public static final String AAT = "aat"
	public static final String AAC = "aac"
	public static final String AAA = "aaa"
	public static final String AAG = "aag"
	public static final String GAT = "gat"
	public static final String GAC = "gac"
	public static final String GAA = "gaa"
	public static final String GAG = "gag"
	public static final String TGT = "tgt"
	public static final String TGC = "tgc"
	public static final String TGA = "tga"
	public static final String TGG = "tgg"
	public static final String CGT = "cgt"
	public static final String CGC = "cgc"
	public static final String CGA = "cga"
	public static final String CGG = "cgg"
	public static final String AGT = "agt"
	public static final String AGC = "agc"
	public static final String AGA = "aga"
	public static final String AGG = "agg"
	public static final String GGT = "ggt"
	public static final String GGC = "ggc"
	public static final String GGA = "gga"
	public static final String GGG = "ggg"
	
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
