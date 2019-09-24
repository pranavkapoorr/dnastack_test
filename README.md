# DNAstack Interview Exercise

This reposiory contains a skeletal Spring Boot 2.0 web application. Basic familiarity with Spring Boot is
assumed.

## Get the example running

### Get the Lombok Plugin
The starer project uses Lombok to keep the noise down in value objects, so you will need the Lombok plugin
for your IDE. Without it, you will encounter build errors when you import the project.

### Import into IDE
This is a Maven project. Import it in the normal way you would import any Maven project into your IDE.

### Run the app
The main class is `com.dnastack.interview.beaconsummarizer.BeaconSummarizerApplication`. Run it however
you normally run a Java main class in your IDE.

### Try it
Once the app has started, from the command line, you can use cURL:

```bash
$ curl 'http://localhost:8080/search?allele=C&chrom=13&pos=32936731&ref=GRCh37&referenceAllele=G'
```

You could also paste the same URL into your web browser. Since this exercise is just a REST
resource that returns JSON (no UI), it helps to have a browser plugin that formats JSON responses.

As given, the application ignores the request parameters, fetches the list of organizations
participating in the Beacon Network, and returns a JSON structure listing their names:

```json
{
  "organizations": [
    "AMPLab, UC Berkeley",
    "Australian Genomics Health Alliance",
    "Belgian Medical Genomics Initiative",
    "BGI",
    "Bioinformatics Area, Fundacion Progreso y Salud",
    "BioReference Laboratories",
    "Brazilian Initiative on Precision Medicine",
    "BRCA Exchange",
    "Broad Institute",
    "Centre for Genomic Regulation",
    "Centro Nacional de Analisis Genomico",
    "Children's Mercy Hospital",
    "Curoverse",
    "DNAstack",
    "ELIXIR",
    "EMBL European Bioinformatics Institute",
    "Garvan Institute of Medical Research",
    "Global Alliance for Genomics and Health",
    "Global Gene Corp",
    "Google",
    "Institute for Systems Biology",
    "Instituto Nacional de Medicina Genomica",
    "Japan Science and Technology Agency - National Bioscience Database Center",
    "Mike Lin",
    "MyGene2.org",
    "National Center for Biotechnology Information",
    "Ontario Institute for Cancer Research",
    "OpenSNP",
    "PhenomeCentral",
    "SciLifeLab",
    "Sequencing.com",
    "SingHealth Duke-NUS Institute of Precision Medicine",
    "Southern African Human Genome Programme",
    "The Scripps Research Institute",
    "University of California, Santa Cruz",
    "University of Leicester",
    "Variant Interpretation for Cancer Consortium",
    "Wellcome Trust Sanger Institute"
  ]
}
```

## Your task

Your task is to enhance the example `/search` resource so it uses its request parameters to perform an
actual Beacon search, and returns these summaries of the search results:

1. A count of how many beacons responded from each organization
2. Total counts for
  * Found (response == true)
  * Not Found (response == false)
  * Not Applicable (response == null)
  * Not Responding (API timeout or error)

The result should match this structure:

```json
{
  "organizations": [
    {
      "organization": "The Scripps Research Institute",
      "beacons": 11
    },
    {
      "organization": "Wellcome Trust Sanger Institute",
      "beacons": 5
    },
    {
      "organization": "Google",
      "beacons": 4
    },
    // ... the rest of the orgs, sorted in descending order by number of beacons
  ],
  "found": 10,
  "notFound": 37,
  "notApplicable": 19,
  "notResponding": 3
}
```

You will need to enhance the BeaconClient class to make additional Beacon Network API calls. The
Beacon Network API is documented here: [https://beacon-network.org/#/developers/api/beacon-network].

### Constraints

* Please extend the existing application rather than starting from scratch; however, feel free to add
  any Maven dependencies that you feel would help you get the task finished more quickly or more cleanly.
* Perform the search using multiple parallel requests to the Beacon API, each request querying at most
  3 beacons (that is, use the optional `beacon` parameter to pass up to 3 beacon names to the
  `/api/responses` resource.)
* Allow up to 30 seconds for each Beacon query to respond, after which that request is considered to have
  timed out.
* If any of the search requests fails (due to timeout or HTTP error response), treat all beacons included
  in those failed requests as "not responding." There is no need to retry.

### Evaluation

Your solution will be evaluated for readability, ease of future maintenance in the face of changing
requirements, and correctness. Considerations for performance are nice-to-have, but must not interfere
with the primary goals.
