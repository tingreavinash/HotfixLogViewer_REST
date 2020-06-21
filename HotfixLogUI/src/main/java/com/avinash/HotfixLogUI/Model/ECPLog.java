/**
 * Model class for ECPLog Document.
 * @author Avinash Tingre
 */
package com.avinash.HotfixLogUI.Model;

import java.util.Map;

public class ECPLog{
	
	private long _id;
	private String cramerVersion;
	private String isPreRequisite;
	private String prereqForLatestEcp;
	private String ecpNo;
	private String latestEcp;
	private String isThisLatestHF;
	private String sequence;
	private String orNo;
	private String description;
	private String status;
	private String requestor;
	private String fixedBy;
	private String requestDate;
	private String targetDate;
	private String releasedDate;
	
	
	private String module;
	private String version;
	private String caseOrCrNo;
	private String filesModifiedInPerforce;
	private String fileLocationInPerforce;
	private String filesReleasedToCustomer;
	private String type;
	private String notes;
	private String downloadCenter;
	private String ecpReplaced;
	private String additionalInfo;
	private String fixRolledIntoModule;
	private String rolledIntoVersion;
	private String rollupCr;
	private String escapingDefect;
	private String reportingVersion;
	private String originalIssue;
	private String addedToExtranet;
	private String addedToExtranetUpdate;
	private String addedToPatchBundle;
	private String hfNotBuiltSep;
	private String c4IssueAlso;
	private String c5IssueAlso;
	private String missingBasicFunc;
	private String newComponent;
	private String causedByNewComp;
	private String platformIssue;
	private String perfIssue;
	private String upgradeIssue;
	private String newFuncAdded;
	private String mandatoryEcp;
	private String specificFunc;
	private String multiModulesAffected;
	private String severity;
	private String priority;
	private String ecpFaulty;
	private String hfRolllupInfo;
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public String getCramerVersion() {
		return cramerVersion;
	}
	public void setCramerVersion(String cramerVersion) {
		this.cramerVersion = cramerVersion;
	}
	public String getIsPreRequisite() {
		return isPreRequisite;
	}
	public void setIsPreRequisite(String isPreRequisite) {
		this.isPreRequisite = isPreRequisite;
	}
	public String getPrereqForLatestEcp() {
		return prereqForLatestEcp;
	}
	public void setPrereqForLatestEcp(String prereqForLatestEcp) {
		this.prereqForLatestEcp = prereqForLatestEcp;
	}
	public String getEcpNo() {
		return ecpNo;
	}
	public void setEcpNo(String ecpNo) {
		this.ecpNo = ecpNo;
	}
	public String getLatestEcp() {
		return latestEcp;
	}
	public void setLatestEcp(String latestEcp) {
		this.latestEcp = latestEcp;
	}
	public String getIsThisLatestHF() {
		return isThisLatestHF;
	}
	public void setIsThisLatestHF(String isThisLatestHF) {
		this.isThisLatestHF = isThisLatestHF;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getOrNo() {
		return orNo;
	}
	public void setOrNo(String orNo) {
		this.orNo = orNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getFixedBy() {
		return fixedBy;
	}
	public void setFixedBy(String fixedBy) {
		this.fixedBy = fixedBy;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public String getReleasedDate() {
		return releasedDate;
	}
	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCaseOrCrNo() {
		return caseOrCrNo;
	}
	public void setCaseOrCrNo(String caseOrCrNo) {
		this.caseOrCrNo = caseOrCrNo;
	}
	public String getFilesModifiedInPerforce() {
		return filesModifiedInPerforce;
	}
	public void setFilesModifiedInPerforce(String filesModifiedInPerforce) {
		this.filesModifiedInPerforce = filesModifiedInPerforce;
	}
	public String getFileLocationInPerforce() {
		return fileLocationInPerforce;
	}
	public void setFileLocationInPerforce(String fileLocationInPerforce) {
		this.fileLocationInPerforce = fileLocationInPerforce;
	}
	public String getFilesReleasedToCustomer() {
		return filesReleasedToCustomer;
	}
	public void setFilesReleasedToCustomer(String filesReleasedToCustomer) {
		this.filesReleasedToCustomer = filesReleasedToCustomer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getDownloadCenter() {
		return downloadCenter;
	}
	public void setDownloadCenter(String downloadCenter) {
		this.downloadCenter = downloadCenter;
	}
	public String getEcpReplaced() {
		return ecpReplaced;
	}
	public void setEcpReplaced(String ecpReplaced) {
		this.ecpReplaced = ecpReplaced;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getFixRolledIntoModule() {
		return fixRolledIntoModule;
	}
	public void setFixRolledIntoModule(String fixRolledIntoModule) {
		this.fixRolledIntoModule = fixRolledIntoModule;
	}
	public String getRolledIntoVersion() {
		return rolledIntoVersion;
	}
	public void setRolledIntoVersion(String rolledIntoVersion) {
		this.rolledIntoVersion = rolledIntoVersion;
	}
	public String getRollupCr() {
		return rollupCr;
	}
	public void setRollupCr(String rollupCr) {
		this.rollupCr = rollupCr;
	}
	public String getEscapingDefect() {
		return escapingDefect;
	}
	public void setEscapingDefect(String escapingDefect) {
		this.escapingDefect = escapingDefect;
	}
	public String getReportingVersion() {
		return reportingVersion;
	}
	public void setReportingVersion(String reportingVersion) {
		this.reportingVersion = reportingVersion;
	}
	public String getOriginalIssue() {
		return originalIssue;
	}
	public void setOriginalIssue(String originalIssue) {
		this.originalIssue = originalIssue;
	}
	public String getAddedToExtranet() {
		return addedToExtranet;
	}
	public void setAddedToExtranet(String addedToExtranet) {
		this.addedToExtranet = addedToExtranet;
	}
	public String getAddedToExtranetUpdate() {
		return addedToExtranetUpdate;
	}
	public void setAddedToExtranetUpdate(String addedToExtranetUpdate) {
		this.addedToExtranetUpdate = addedToExtranetUpdate;
	}
	public String getAddedToPatchBundle() {
		return addedToPatchBundle;
	}
	public void setAddedToPatchBundle(String addedToPatchBundle) {
		this.addedToPatchBundle = addedToPatchBundle;
	}
	public String getHfNotBuiltSep() {
		return hfNotBuiltSep;
	}
	public void setHfNotBuiltSep(String hfNotBuiltSep) {
		this.hfNotBuiltSep = hfNotBuiltSep;
	}
	public String getC4IssueAlso() {
		return c4IssueAlso;
	}
	public void setC4IssueAlso(String c4IssueAlso) {
		this.c4IssueAlso = c4IssueAlso;
	}
	public String getC5IssueAlso() {
		return c5IssueAlso;
	}
	public void setC5IssueAlso(String c5IssueAlso) {
		this.c5IssueAlso = c5IssueAlso;
	}
	public String getMissingBasicFunc() {
		return missingBasicFunc;
	}
	public void setMissingBasicFunc(String missingBasicFunc) {
		this.missingBasicFunc = missingBasicFunc;
	}
	public String getNewComponent() {
		return newComponent;
	}
	public void setNewComponent(String newComponent) {
		this.newComponent = newComponent;
	}
	public String getCausedByNewComp() {
		return causedByNewComp;
	}
	public void setCausedByNewComp(String causedByNewComp) {
		this.causedByNewComp = causedByNewComp;
	}
	public String getPlatformIssue() {
		return platformIssue;
	}
	public void setPlatformIssue(String platformIssue) {
		this.platformIssue = platformIssue;
	}
	public String getPerfIssue() {
		return perfIssue;
	}
	public void setPerfIssue(String perfIssue) {
		this.perfIssue = perfIssue;
	}
	public String getUpgradeIssue() {
		return upgradeIssue;
	}
	public void setUpgradeIssue(String upgradeIssue) {
		this.upgradeIssue = upgradeIssue;
	}
	public String getNewFuncAdded() {
		return newFuncAdded;
	}
	public void setNewFuncAdded(String newFuncAdded) {
		this.newFuncAdded = newFuncAdded;
	}
	public String getMandatoryEcp() {
		return mandatoryEcp;
	}
	public void setMandatoryEcp(String mandatoryEcp) {
		this.mandatoryEcp = mandatoryEcp;
	}
	public String getSpecificFunc() {
		return specificFunc;
	}
	public void setSpecificFunc(String specificFunc) {
		this.specificFunc = specificFunc;
	}
	public String getMultiModulesAffected() {
		return multiModulesAffected;
	}
	public void setMultiModulesAffected(String multiModulesAffected) {
		this.multiModulesAffected = multiModulesAffected;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getEcpFaulty() {
		return ecpFaulty;
	}
	public void setEcpFaulty(String ecpFaulty) {
		this.ecpFaulty = ecpFaulty;
	}
	public String getHfRolllupInfo() {
		return hfRolllupInfo;
	}
	public void setHfRolllupInfo(String hfRolllupInfo) {
		this.hfRolllupInfo = hfRolllupInfo;
	}

	
	
	@Override
	public String toString() {
		return "ECPLog [_id=" + _id + ", cramerVersion=" + cramerVersion + ", isPreRequisite=" + isPreRequisite
				+ ", prereqForLatestEcp=" + prereqForLatestEcp + ", ecpNo=" + ecpNo + ", latestEcp=" + latestEcp
				+ ", isThisLatestHF=" + isThisLatestHF  
				+ ", sequence=" + sequence + ", orNo=" + orNo + ", description=" + description + ", status=" + status
				+ ", requestor=" + requestor + ", fixedBy=" + fixedBy + ", requestDate=" + requestDate + ", targetDate="
				+ targetDate + ", releasedDate=" + releasedDate + ", module=" + module + ", version=" + version
				+ ", caseOrCrNo=" + caseOrCrNo + ", filesModifiedInPerforce=" + filesModifiedInPerforce
				+ ", fileLocationInPerforce=" + fileLocationInPerforce + ", filesReleasedToCustomer="
				+ filesReleasedToCustomer + ", type=" + type + ", notes=" + notes + ", downloadCenter=" + downloadCenter
				+ ", ecpReplaced=" + ecpReplaced + ", additionalInfo=" + additionalInfo + ", fixRolledIntoModule="
				+ fixRolledIntoModule + ", rolledIntoVersion=" + rolledIntoVersion + ", rollupCr=" + rollupCr
				+ ", escapingDefect=" + escapingDefect + ", reportingVersion=" + reportingVersion + ", originalIssue="
				+ originalIssue + ", addedToExtranet=" + addedToExtranet + ", addedToExtranetUpdate="
				+ addedToExtranetUpdate + ", addedToPatchBundle=" + addedToPatchBundle + ", hfNotBuiltSep="
				+ hfNotBuiltSep + ", c4IssueAlso=" + c4IssueAlso + ", c5IssueAlso=" + c5IssueAlso
				+ ", missingBasicFunc=" + missingBasicFunc + ", newComponent=" + newComponent + ", causedByNewComp="
				+ causedByNewComp + ", platformIssue=" + platformIssue + ", perfIssue=" + perfIssue + ", upgradeIssue="
				+ upgradeIssue + ", newFuncAdded=" + newFuncAdded + ", mandatoryEcp=" + mandatoryEcp + ", specificFunc="
				+ specificFunc + ", multiModulesAffected=" + multiModulesAffected + ", severity=" + severity
				+ ", priority=" + priority + ", ecpFaulty=" + ecpFaulty + ", hfRolllupInfo=" + hfRolllupInfo + "]";
	}
	
}
