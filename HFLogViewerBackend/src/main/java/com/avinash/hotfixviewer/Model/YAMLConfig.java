/**
 * Model class for ResponseObject for sending as response to REST call.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
	//excelFilePath: Location of ECPLog.xlsm
	private String excelFilePath;
	//allowedHosts: REST calls only from this host will be accepted.
	private List<String> allowedHosts;
	//header_acao: Header for CORS filtering.
	private String header_acao;
	
	
	public String getHeader_acao() {
		return header_acao;
	}

	public void setHeader_acao(String header_acao) {
		this.header_acao = header_acao;
	}

	public List<String> getAllowedHosts() {
		return allowedHosts;
	}

	public void setAllowedHosts(List<String> allowedHosts) {
		this.allowedHosts = allowedHosts;
	}

	public String getExcelFilePath() {
		return excelFilePath;
	}

	public void setExcelFilePath(String excelFilePath) {
		this.excelFilePath = excelFilePath;
	}
	
	
}
