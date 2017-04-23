package com.adscoop.website.entites;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NodeEntity
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSite extends AbstractEntity {

	private String host;
	
	private int port;
	private String path;
	@Setter
	private String userToken;

	@Relationship(type = "WEBSITE_HAS_BANNERSPACE")
	@Builder.Default
	private List<BannerSpace> bannerSpaceSet = new ArrayList<>();

	@Builder.Default
	private List<String> regions = new ArrayList<>();
	
	public boolean isEmpty() {
		return StringUtils.isEmpty(host);
	}

	public boolean isNotEmpty() {
		return StringUtils.isNotEmpty(host);
	}

}
