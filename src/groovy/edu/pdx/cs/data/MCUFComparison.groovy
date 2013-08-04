/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

class MCUFComparison {
	String org1OrganismId, org1ScientificName, org1TaxonomyId
	String org2OrganismId, org2ScientificName, org2TaxonomyId
	Map<String, String> org1MCUFCodonDistribution
	Map<String, String> org2MCUFCodonDistribution
	BigDecimal mcuf
	
	public MCUFComparison(org1OrgId, org1Name, org1TaxId, org1MCUFDist,
		org2OrgId, org2Name, org2TaxId, org2MCUFDist, mcuf) {
		org1OrganismId = org1OrgId
		org1ScientificName = org1Name
		org1TaxonomyId = org1TaxId
		org1MCUFCodonDistribution = org1MCUFDist
		org2OrganismId = org2OrgId
		org2ScientificName = org2Name
		org2TaxonomyId = org2TaxId
		org2MCUFCodonDistribution = org2MCUFDist
		this.mcuf = mcuf
	}
}
