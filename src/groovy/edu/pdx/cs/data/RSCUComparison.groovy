/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

class RSCUComparison {
	String org1OrganismId, org1ScientificName, org1TaxonomyId
	String org2OrganismId, org2ScientificName, org2TaxonomyId
	Map<String, String> org1RSCUCodonDistribution
	Map<String, String> org2RSCUCodonDistribution
	BigDecimal trendlineSlope, trendlineYIntercept
	
	public RSCUComparison(org1OrgId, org1Name, org1TaxId, org1RSCUDist,
		org2OrgId, org2Name, org2TaxId, org2RSCUDist, slope, yIntercept) {
		org1OrganismId = org1OrgId
		org1ScientificName = org1Name
		org1TaxonomyId = org1TaxId
		org1RSCUCodonDistribution = org1RSCUDist
		org2OrganismId = org2OrgId
		org2ScientificName = org2Name
		org2TaxonomyId = org2TaxId
		org2RSCUCodonDistribution = org2RSCUDist
		trendlineSlope = slope
		trendlineYIntercept = yIntercept
	}
}
