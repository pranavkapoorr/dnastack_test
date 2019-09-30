package com.dnastack.interview.beaconsummarizer.service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.dnastack.interview.beaconsummarizer.client.beacon.BeaconClient;
import com.dnastack.interview.beaconsummarizer.model.*;

@Service
public class BeaconSummaryService {
	@Autowired
    private BeaconClient beaconClient;
	
    private List<Organization> getOrganizations(){
    	return beaconClient.getOrganizations();
    }
    private List<Beacon> getAllBeacons(){
    	return beaconClient.getAllBeacons();
    }
	
	@Async
    private ResponseEntity<String> getBeacon(String ref,String chrom,String pos,String allele,String referenceAllel,String... beacons){
		ResponseEntity<String> res = null;
	                try{
	                	res = beaconClient.getBeacon(ref,chrom,pos,allele,referenceAllel,beacons);
					  }catch(Exception e) {
							res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
					  }
		        return res;
	}
	
	
	public BeaconSummary getOrgSummary(String ref,String chrom,String pos,String allele,String referenceAllel){
		AtomicInteger notResponding = new AtomicInteger(0);
		AtomicInteger found = new AtomicInteger(0);
		AtomicInteger notFound = new AtomicInteger(0);
		List <Organization> organizations =  getOrganizations();
		List<Beacon> beacons = getAllBeacons();
		List<OrganizationSummary> summary = organizations
				.stream()
				.map((o) -> {
					int beaconCount = (int) beacons.stream().filter((b) -> b.getOrganization().equalsIgnoreCase(o.getName())).count();
					return new OrganizationSummary(o.getName(), beaconCount);
				})
				.sorted((OrganizationSummary o1,OrganizationSummary o2) -> o2.getBeacons()-o1.getBeacons())
				.collect(Collectors.toList());
		beacons.forEach((b) -> {
			 CompletableFuture.supplyAsync(() -> getBeacon(ref,chrom,pos,allele,referenceAllel,b.getId()))
			 //.completeOnTimeout(new Result(Status.TIMED_OUT),1, TimeUnit.MINUTES)
			 .thenAccept((bResp) -> {
							if(bResp.getStatusCode().equals(HttpStatus.OK)) {
								found.incrementAndGet();
							}else if(bResp.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
								notFound.incrementAndGet();
							}else {
								notResponding.incrementAndGet();
							}
						});
			
			/*if(beaconResp.getStatusCode().equals(HttpStatus.OK)) {
				found.incrementAndGet();
			}else if(beaconResp.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				notFound.incrementAndGet();
			}else {
				notResponding.incrementAndGet();
			}*/
		});
		
		return new BeaconSummary(summary, found.get(), notFound.get(), 0, notResponding.get());
	}
}
