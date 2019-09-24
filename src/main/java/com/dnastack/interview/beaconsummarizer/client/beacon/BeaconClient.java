package com.dnastack.interview.beaconsummarizer.client.beacon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dnastack.interview.beaconsummarizer.model.*;

import java.net.SocketTimeoutException;
import java.util.List;

// This client uses Spring's wrapper of the Feign declarative HTTP client library.
// Instructions are at <https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html>

// Documentation for the Beacon REST API is at <https://beacon-network.org/#/developers/api/beacon-network>

@FeignClient(name="beacon",decode404 = true,url = "https://beacon-network.org")
public interface BeaconClient {
	
    @GetMapping("/api/organizations")
    List<Organization> getOrganizations();
    
    @GetMapping("/api/beacons")
    List<Beacon> getAllBeacons();
	
    @GetMapping("/api/responses")
    ResponseEntity<String> getBeacon(
    		@RequestParam(required = false) String ref,
            @RequestParam(required = false) String chrom,
            @RequestParam(required = false) String pos,
            @RequestParam(required = false) String allele,
            @RequestParam(required = false) String referenceAllel,
    		@RequestParam String... beacon
    		)  throws SocketTimeoutException;

}
