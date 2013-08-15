<%--
  Created by IntelliJ IDEA.
  Author: Joseph Lee <josel@pdx.edu>
  Date: 7/17/13
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>GenBank-Query</title>
    <meta name="layout" content="main"/>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">

    <flot:resources plugins="['pie']" includeJQueryLib="false" includeExCanvasLib="false"/>
    <gvisualization:apiImport />

</head>
<body>

<g:if test="${codonDistributions.size() == 0}">
    <script>
        // show modal if no data supplied yet
        $(document).ready(function() {
            $('#upload').modal({ show: true });
        });
    </script>
</g:if>
<g:elseif test="${codonDistributions.size() == 1}">
    <%-- Single Sequence Analysis --%>
    <%-- Codon Distribution --%>
    <div id="codon-dist">
        <%
            // Set options
            def codonDist = codonDistributions[0]
            def columnHeaders = [['string', 'Codon'], ['number', 'Distribution']]
            def textStyle = [fontSize: 10]
            def titleTextStyle = [fontSize: 13]
            def options = [
                    vAxis: [maxValue: 1, minValue: 0, textStyle: textStyle],
                    hAxis: [textStyle: textStyle],
                    legend: [position: 'none'],
                    chartArea: [top: 40, bottom: 0, left: 40, right: 0]
            ]
            def rowCounts = [2, 3, 2, 2, 5, 4, 3, 2]    // Graph row lengths
            def c = 0
            def i = 0
            def codonList = codonDist.collectNested { it.name }
            def amino = null

        %>
        <g:each in="${rowCounts}" var="r">
            <div class="row">
                <% c = 0 %>
                <g:while test="${c < r}">
                    <%
                        amino = codonDist[i]
                    %>
                    <div class="dist-graph" id="${"amino" + i.toString()}"></div>
                    <gvisualization:columnCoreChart columns="${columnHeaders}" data="${amino.values}"
                        elementId="${"amino" + i.toString()}" title="${amino.name}" vAxis="${new Expando(options.vAxis)}"
                        legend="${'none'}" height="${200}" width="${40 + amino.values.size() * 80}"
                        titleTextStyle="${new Expando(titleTextStyle)}"
                        chartArea="${new Expando(options.chartArea)}" />
                    <%
                        c = c + 1
                        i = i + 1
                    %>
                </g:while>
            </div>
        </g:each>

    </div>
</g:elseif>

<g:if test="${(organisms.size() > 0) && opt && opt.contains("GC")}">
    <%-- GC Percentage --%>
    <div id="gc" class="row">
        <h2>GC Percentages</h2>
        <div id="gcPie0" class="piechart float-left"></div>
        <div id="gcText">
            <%
                c = 0
                def percentage, split
            %>
            <g:each in="${organisms}" var="organism">
                <%
                    percentage = organism.gcPercentage
                    if (percentage.length() > 5) {
                        split = percentage.split(/\./)
                        if (split[1].size() > 2 && new Integer(split[1][2]) >= 5) {
                            percentage = split[0] + "." + split[1][0] + (new Integer(split[1][1]) + 1).toString()
                        }
                        else {
                            percentage = split[0] + "." + split[1][0..1]
                        }
                    }
                %>
                <div id="${"gcPerc" + c.toString()}">
                    ${organism.scientificName}: ${percentage}%
                </div>
                <% c = c + 1 %>
            </g:each>
        </div>
        <div id="gcPie1" class="piechart float-right"></div>
        <% c = 0 %>
        <g:each in="${organisms}" var="organism">
            <%
                def gcp = new BigDecimal(organism.gcPercentage)
                def gcData = [['With GC', gcp], ['Without GC', 100 - gcp]]
                columnHeaders = [['string', 'Codon'], ['number', 'Percentage']]
                options = [
                        legend: [position: 'none']
                ]
            %>
            <gvisualization:pieCoreChart elementId="${"gcPie" + c.toString()}"
                columns="${columnHeaders}" data="${gcData}"
                pieSliceText="${"none"}"
                width="${200}" height="${200}" legend="${"none"}"/>
            <% c = c + 1 %>
        </g:each>
    </div>
</g:if>

<g:if test="${(organisms.size() == 2)  && opt && opt.contains("RSCU")}">
    <%-- RSCU Codon Analysis --%>
    <div class="row">
        <h2>RSCU Analysis</h2>
        <%
            def keys = organisms[0].rscuCodonDistribution.keySet()
            def rscuData = []
            for (codon in keys) {
                rscuData.push([
                        organisms[0].rscuCodonDistribution[codon],
                        organisms[1].rscuCodonDistribution[codon] ])
            }
        %>
        <g:javascript>
            var rscuFlotData = [
                {
                    data: ${rscuData},
                    points: {
                        radius: 4,
                        show: true,
                        fill: true,
                        fillColor: '#058DC7'
                    },
                    lines: { show: false },
                    color: '#058DC7'
                },
                {
                    data: [
                        [0, ${rscuComp.trendlineYIntercept}],
                        [1, ${rscuComp.trendlineSlope + rscuComp.trendlineYIntercept}]
                    ],
                    lines: { show: true },
                    fill: true,
                    color: '#cc2222'
                }
            ];
            var rscuFlotOptions = {
                xaxis: { min: 0, max: 1 },
                yaxis: { min: 0, max: 1 },
                shadowSize: 0
            };
        </g:javascript>
        <flot:plot id="container-flot" style="width: 500px; height: 500px;"
            data="rscuFlotData" options="rscuFlotOptions"/>
        <%-- TODO: Add axis labels with organism names --%>
    </div>
</g:if>

</body>
</html>