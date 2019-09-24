package com.dnastack.interview.beaconsummarizer.model;

import lombok.Value;
import java.util.List;

@Value
public class BeaconSummary {
    private List<OrganizationSummary> organizations;
    private int found;
    private int notFound; 
    private int notApplicable;
    private int notResponding;
}
