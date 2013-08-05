/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/3/13
 *
 */

package edu.pdx.cs.data

class GCPercentageComparison {
	String org1OrganismId, org1ScientificName, org1TaxonomyId
	String org2OrganismId, org2ScientificName, org2TaxonomyId
	String org1GCPercentage, org2GCPercentage
	BigDecimal gcDifference
	
	public GCPercentageComparison(org1OrgId, org1Name, org1TaxId, org1GCPercent,
		org2OrgId, org2Name, org2TaxId, org2GCPercent, gcDiff) {
		org1OrganismId = org1OrgId
		org1ScientificName = org1Name
		org1TaxonomyId = org1TaxId
		org1GCPercentage = org1GCPercent
		org2OrganismId = org2OrgId
		org2ScientificName = org2Name
		org2TaxonomyId = org2TaxId
		org2GCPercentage = org2GCPercent
		gcDifference = gcDiff
	}
}
