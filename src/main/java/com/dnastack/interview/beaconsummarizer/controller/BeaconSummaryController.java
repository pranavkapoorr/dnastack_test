package com.dnastack.interview.beaconsummarizer.controller;

import com.dnastack.interview.beaconsummarizer.model.*;
import com.dnastack.interview.beaconsummarizer.service.BeaconSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeaconSummaryController {

    @Autowired
    private BeaconSummaryService service;


    @GetMapping("/search")
    public BeaconSummary search(
    		@RequestParam(required = false) String ref,
            @RequestParam(required = false) String chrom,
            @RequestParam(required = false) String pos,
            @RequestParam(required = false) String allele,
            @RequestParam(required = false) String referenceAllel
            ) {
    	return service.getOrgSummary(ref,chrom,pos,allele,referenceAllel);
    }
}
