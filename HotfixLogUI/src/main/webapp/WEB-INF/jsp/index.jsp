<%@page import="java.util.Date"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Hotfix Log Viewer</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" href="fontawesome-free-5.13.0-web/css/all.css">

<link
	href="https://fonts.googleapis.com/css2?family=Noto+Serif:wght@700&display=swap"
	rel="stylesheet">
<link rel="icon" href="../image/favicon3.png" type="image/gif"
	sizes="64x64">

<style>
html, body {
	height: 100%;
}
</style>


</head>
<body class="custom-body" style="margin: 0;">
<div class="se-pre-con" id="se-pre-con"></div>
	<%
		InetAddress ip = InetAddress.getByName(request.getRemoteAddr());
		String hostname = ip.getHostName();
		System.out.println("User:\t" + hostname);
		System.out.println("Time:\t" + new Date());
	%>

	<div class="container-fluid h-100" id="container-fluid">

		<div class="row h-100">
		
			<div class="col-md-4 search-panel">
				<div class="row justify-content-center"
					style="background-color: #7952b3">
					<h2 class="text-white"
						style="margin: 5px; font-family: 'Noto Serif', serif;"
						align="center">Hotfix Log Viewer</h2>
				</div>

				<form autocomplete="off" id="searchForm" method="post"
					style="margin-top: 10px;">

					<div class="form-row" style="margin-bottom: 5px; max-height: 300px">
						<div class="col">
							<!-- Card for Selecting Release -->
							<div class="card" style="max-height: 270px;">

								<div class="card-header">
								<div class="input-group input-group-sm">
								<div class="input-group-prepend">
											<span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
										</div>
									<input type="text" class="form-control form-control-sm"
										id="inputVersion" placeholder="Find Version"
										onkeyup="filterVersions()">
								</div>
								</div>

								<div class="cust-padding card-body" style="overflow-y: scroll;"
									id="versionList">
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_2" value="Cramer 2">
										<label class="custom-control-label" for="Cramer_2">Cramer
											2</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_3" value="Cramer 3">
										<label class="custom-control-label" for="Cramer_3">Cramer
											3</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_4" value="Cramer 4">
										<label class="custom-control-label" for="Cramer_4">Cramer
											4</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_5_Rel_1"
											value="Cramer 5 Rel 1"> <label
											class="custom-control-label" for="Cramer_5_Rel_1">Cramer
											5 Rel 1</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_5_Rel_2"
											value="Cramer 5 Rel 2"> <label
											class="custom-control-label" for="Cramer_5_Rel_2">Cramer
											5 Rel 2</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_5_Rel_3"
											value="Cramer 5 Rel 3"> <label
											class="custom-control-label" for="Cramer_5_Rel_3">Cramer
											5 Rel 3</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_1_GA1"
											value="Cramer 6 Rel 1 GA1"> <label
											class="custom-control-label" for="Cramer_6_Rel_1_GA1">Cramer
											6 Rel 1 GA1</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_1"
											value="Cramer 6 Rel 1"> <label
											class="custom-control-label" for="Cramer_6_Rel_1">Cramer
											6 Rel 1</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_1_SC_1_1"
											value="Cramer 6 Rel 1\n(SC 1.1)"> <label
											class="custom-control-label" for="Cramer_6_Rel_1_SC_1_1">Cramer
											6 Rel 1 (SC 1.1)</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_2"
											value="Cramer 6 Rel 2"> <label
											class="custom-control-label" for="Cramer_6_Rel_2">Cramer
											6 Rel 2</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_3"
											value="Cramer 6 Rel 3"> <label
											class="custom-control-label" for="Cramer_6_Rel_3">Cramer
											6 Rel 3</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="Cramer_6_Rel_4"
											value="Cramer 6 Rel 4"> <label
											class="custom-control-label" for="Cramer_6_Rel_4">Cramer
											6 Rel 4</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_0_0" value="OSS 10.0.0">
										<label class="custom-control-label" for="OSS_10_0_0">OSS
											10.0.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_1_0" value="OSS 10.1.0">
										<label class="custom-control-label" for="OSS_10_1_0">OSS
											10.1.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_2_0" value="OSS 10.2.0">
										<label class="custom-control-label" for="OSS_10_2_0">OSS
											10.2.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_3_0" value="OSS 10.3.0">
										<label class="custom-control-label" for="OSS_10_3_0">OSS
											10.3.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_3_1" value="OSS 10.3.1">
										<label class="custom-control-label" for="OSS_10_3_1">OSS
											10.3.1</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_10_3_2" value="OSS 10.3.2">
										<label class="custom-control-label" for="OSS_10_3_2">OSS
											10.3.2</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_7_5" value="OSS 7.5">
										<label class="custom-control-label" for="OSS_7_5">OSS
											7.5</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_7_5_1" value="OSS 7.5.1">
										<label class="custom-control-label" for="OSS_7_5_1">OSS
											7.5.1</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_7_5_2" value="OSS 7.5.2">
										<label class="custom-control-label" for="OSS_7_5_2">OSS
											7.5.2</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_8_0_0" value="OSS 8.0.0">
										<label class="custom-control-label" for="OSS_8_0_0">OSS
											8.0.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_8_1_0" value="OSS 8.1.0">
										<label class="custom-control-label" for="OSS_8_1_0">OSS
											8.1.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_8_2_0" value="OSS 8.2.0">
										<label class="custom-control-label" for="OSS_8_2_0">OSS
											8.2.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_9_0_0" value="OSS 9.0.0">
										<label class="custom-control-label" for="OSS_9_0_0">OSS
											9.0.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_9_1_0" value="OSS 9.1.0">
										<label class="custom-control-label" for="OSS_9_1_0">OSS
											9.1.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_9_2_0" value="OSS 9.2.0">
										<label class="custom-control-label" for="OSS_9_2_0">OSS
											9.2.0</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedVersion" id="OSS_9_3_0" value="OSS 9.3.0">
										<label class="custom-control-label" for="OSS_9_3_0">OSS
											9.3.0</label>
									</div>
								</div>
							</div>
						</div>

						<div class="col">
							<!-- Card for Selecting Module  -->
							<div class="card" style="max-height: 270px;">

								<div class="card-header">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text" id="basic-addon1"><i class="fas fa-search"></i></span>
										</div>
										<input type="text" class="form-control form-control-sm"
											id="inputModule" placeholder="Find Module"
											onkeyup="filterModules()">
									</div>
								</div>

								<div class="cust-padding card-body" style="overflow-y: scroll;"
									id="moduleList">

									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ActivationEngine"
											id="ActivationEngine"> <label
											class="custom-control-label" for="ActivationEngine">Activation
											Engine</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Amdocs BOSS Manager"
											id="Amdocs_BOSS_Manager"> <label
											class="custom-control-label" for="Amdocs_BOSS_Manager">Amdocs
											BOSS Manager</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Amdocs Integration Adapter"
											id="Amdocs_Integration_Adapter"> <label
											class="custom-control-label" for="Amdocs_Integration_Adapter">Amdocs
											Integration Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ANN (inc. Search)"
											id="ANN_inc_Search"> <label
											class="custom-control-label" for="ANN_inc_Search">ANN
											(inc. Search)</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Ansible (ANN)" id="Ansible_ANN">
										<label class="custom-control-label" for="Ansible_ANN">Ansible
											(ANN)</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="AutomationPackCommon"
											id="AutomationPackCommon"> <label
											class="custom-control-label" for="AutomationPackCommon">Automation
											Pack Common</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="BroadbandAutomationPack"
											id="BroadbandAutomationPack"> <label
											class="custom-control-label" for="BroadbandAutomationPack">Broadband
											Automation Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="BroadbandSyncPack"
											id="BroadbandSyncPack"> <label
											class="custom-control-label" for="BroadbandSyncPack">Broadband
											Sync Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="BusinessObjectsAdapter"
											id="BusinessObjectsAdapter"> <label
											class="custom-control-label" for="BusinessObjectsAdapter">Business
											Objects Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="CapacityPlanner"
											id="CapacityPlanner"> <label
											class="custom-control-label" for="CapacityPlanner">Capacity
											Planner</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule"
											value="CarrierEthernetServiceAutomationPack"
											id="CarrierEthernetServiceAutomationPack"> <label
											class="custom-control-label"
											for="CarrierEthernetServiceAutomationPack">Carrier
											Ethernet ServiceAutomationPack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="CES SyncPack" id="CES_SyncPack">
										<label class="custom-control-label" for="CES_SyncPack">CES
											SyncPack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="CMDBInventoryPack"
											id="CMDBInventoryPack"> <label
											class="custom-control-label" for="CMDBInventoryPack">CMDB
											Inventory Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Configurator" id="Configurator">
										<label class="custom-control-label" for="Configurator">Configurator</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Connector" id="Connector">
										<label class="custom-control-label" for="Connector">Connector</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="CoreSyncPack" id="CoreSyncPack">
										<label class="custom-control-label" for="CoreSyncPack">Core
											Sync Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="DeliveryEngine"
											id="DeliveryEngine"> <label
											class="custom-control-label" for="">Delivery Engine</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Demo" id="Demo"> <label
											class="custom-control-label" for="Demo">Demo</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="DeviceModelPack"
											id="DeviceModelPack"> <label
											class="custom-control-label" for="DeviceModelPack">Device
											Model Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="EngineeringAdapter"
											id="EngineeringAdapter"> <label
											class="custom-control-label" for="EngineeringAdapter">Engineering
											Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="EngineeringAdapterforTNE"
											id="EngineeringAdapterforTNE"> <label
											class="custom-control-label" for="EngineeringAdapterforTNE">Engineering
											Adapter for TNE</label>
									</div>

									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Foundations" id="Foundations">
										<label class="custom-control-label" for="Foundations">Foundations</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="GermanLocalizationPack"
											id="GermanLocalizationPack"> <label
											class="custom-control-label" for="GermanLocalizationPack">German
											Localization Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="GESmallworldAdapter"
											id="GESmallworldAdapter"> <label
											class="custom-control-label" for="GESmallworldAdapter">GE
											Smallworld Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="HuaweiActivationPack"
											id="HuaweiActivationPack"> <label
											class="custom-control-label" for="HuaweiActivationPack">Huawei
											Activation Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Installer" id="Installer">
										<label class="custom-control-label" for="Installer">Installer</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Inventory TMF 639 API"
											id="Inventory_TMF_639_API"> <label
											class="custom-control-label" for="Inventory_TMF_639_API">Inventory
											TMF 639 API</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="InventoryPack"
											id="InventoryPack"> <label
											class="custom-control-label" for="InventoryPack">Inventory
											Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="IPVPN Automation Pack"
											id="IPVPN_Automation_Pack"> <label
											class="custom-control-label" for="IPVPN_Automation_Pack">IPVPN
											Automation Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule"
											value="IWM - Inventory Workflow Management"
											id="IWM_Inventory_Workflow_Management"> <label
											class="custom-control-label"
											for="IWM_Inventory_Workflow_Management">IWM -
											Inventory Workflow Management</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule"
											value="IWS - Inventory Web Services for RM 6.4" id="IWSfor64">
										<label class="custom-control-label" for="IWSfor64">IWS
											- Inventory Web Services for RM 6.4</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="IWS - Inventory Web Services"
											id="IWS"> <label class="custom-control-label"
											for="IWS">IWS - Inventory Web Services</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="JapaneseLocalizationPack"
											id="JapaneseLocalizationPack"> <label
											class="custom-control-label" for="JapaneseLocalizationPack">Japanese
											Localization Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Java Client" id="Java_Client">
										<label class="custom-control-label" for="Java_Client">Java
											Client</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="LocalizationPack"
											id="LocalizationPack"> <label
											class="custom-control-label" for="LocalizationPack">Localization
											Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="MetadataManager"
											id="MetadataManager"> <label
											class="custom-control-label" for="MetadataManager">Metadata
											Manager</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Mobile_Build_Automation_Pack"
											id="Mobile_Build_Automation_Pack"> <label
											class="custom-control-label" for="">Mobile Build
											Automation Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="MPLS SyncPack"
											id="MPLS_SyncPack"> <label
											class="custom-control-label" for="MPLS_SyncPack">MPLS
											Sync Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OrderManagementAdapter"
											id="OrderManagementAdapter"> <label
											class="custom-control-label" for="OrderManagementAdapter">Order
											Management Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OSS Foundations"
											id="OSS_Foundations"> <label
											class="custom-control-label" for="OSS_Foundations">OSS
											Foundations</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OSS Reports" id="OSS_Reports">
										<label class="custom-control-label" for="">OSS Reports</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OSSExportAdapter"
											id="OSSExportAdapter"> <label
											class="custom-control-label" for="OSSExportAdapter">OSS
											Export Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OSSIntegrationAdapter_ABM"
											id="OSSIntegrationAdapter_ABM"> <label
											class="custom-control-label" for="">OSS Integration
											Adapter (ABM)</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="OSSUI" id="OSSUI"> <label
											class="custom-control-label" for="OSSUI">OSSUI</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ParameterManager"
											id="ParameterManager"> <label
											class="custom-control-label" for="ParameterManager">Parameter
											Manager</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="PlanningEngine"
											id="PlanningEngine"> <label
											class="custom-control-label" for="PlanningEngine">Planning
											Engine</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ResourceManager"
											id="ResourceManager"> <label
											class="custom-control-label" for="ResourceManager">Resource
											Manager (RM/ARM)</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ResourcePlanner"
											id="ResourcePlanner"> <label
											class="custom-control-label" for="ResourcePlanner">Resource
											Planner</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="RMIntegrations"
											id="RMIntegrations"> <label
											class="custom-control-label" for="RMIntegrations">RM
											Integrations</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="RouteFinder" id="RouteFinder">
										<label class="custom-control-label" for="RouteFinder">Route
											Finder</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="RPM_Automation_Pack"
											id="RPM_Automation_Pack"> <label
											class="custom-control-label" for="RPM_Automation_Pack">RPM
											Automation Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="RussianLocalizationPack"
											id="RussianLocalizationPack"> <label
											class="custom-control-label" for="RussianLocalizationPack">Russian
											Localization Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SDH SyncPack" id="SDH_SyncPack">
										<label class="custom-control-label" for="SDH_SyncPack">SDH
											Sync Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ServiceAssuranceAdapter"
											id="ServiceAssuranceAdapter"> <label
											class="custom-control-label" for="ServiceAssuranceAdapter">Service
											Assurance Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ServiceCatalog"
											id="ServiceCatalog"> <label
											class="custom-control-label" for="ServiceCatalog">Service
											Catalog</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="ServiceFulfilmentAdapter"
											id="ServiceFulfilmentAdapter"> <label
											class="custom-control-label" for="ServiceFulfilmentAdapter">Service
											Fulfilment Adapter</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SitePlanner" id="SitePlanner">
										<label class="custom-control-label" for="SitePlanner">Site
											Planner</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SOA" id="SOA"> <label
											class="custom-control-label" for="SOA">SOA</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SpanishLocalizationPack"
											id="SpanishLocalizationPack"> <label
											class="custom-control-label" for="SpanishLocalizationPack">Spanish
											Localization Pack</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SyncAdapter Tivoli MPA"
											id="SyncAdapter_Tivoli_MPA"> <label
											class="custom-control-label" for="SyncAdapter_Tivoli_MPA">Sync
											Adapter Tivoli MPA</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="SyncEngine" id="SyncEngine">
										<label class="custom-control-label" for="SyncEngine">Sync
											Engine</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="TaskEngine" id="TaskEngine">
										<label class="custom-control-label" for="TaskEngine">Task
											Engine</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="Technology Accelerators"
											id="Technology_Accelerators"> <label
											class="custom-control-label" for="Technology_Accelerators">Technology
											Accelerators</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="TrendPlanner" id="TrendPlanner">
										<label class="custom-control-label" for="TrendPlanner">Trend
											Planner</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="UnifiedServiceManager"
											id="UnifiedServiceManager"> <label
											class="custom-control-label" for="UnifiedServiceManager">USM
											- Unified Service Manager</label>
									</div>
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											name="SelectedModule" value="XPI (Universal Installer)"
											id="XPI_Universal_Installer"> <label
											class="custom-control-label" for="XPI_Universal_Installer">XPI
											(Universal Installer)</label>
									</div>

								</div>
							</div>
						</div>
					</div>

					<div class="form-row">
						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="ecpNo" placeholder="Hotfix Number"
								style="margin-bottom: 5px;">
						</div>
						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="caseOrCrNo" placeholder="Case/CR Number"
								style="margin-bottom: 5px;">

						</div>
					</div>
					<div class="form-row">
						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="latestHotfix" placeholder="Latest Hotfix Number"
								data-toggle="tooltip" data-placement="bottom"
								title="All the HF's contained by this HF will be displayed."
								style="margin-bottom: 5px;">
						</div>
						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="specificFunc" placeholder="Specific Function"
								style="margin-bottom: 5px;">
						</div>
					</div>


					<input type="text" placeholder="Description"
						class="form-control form-control-sm" name="description"
						style="margin-bottom: 5px;">

					<div class="form-row">
						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="requestor" placeholder="Requested By"
								style="margin-bottom: 5px;">
						</div>


						<div class="col">
							<input type="text" class="form-control form-control-sm"
								name="fixedBy" placeholder="Fixed By"
								style="margin-bottom: 5px;">
						</div>
					</div>

					<input type="text" class="form-control form-control-sm"
						name="filesModifiedInPerforce" placeholder="Files Modified"
						style="margin-bottom: 5px;"> <input type="text"
						class="form-control form-control-sm"
						name="filesReleasedToCustomer" placeholder="Files Released"
						style="margin-bottom: 5px;">






					<button type="submit" id="searchBtn" name="searchBtn"
						value="searchClicked" class="btn btn-primary btn-block">Search</button>
				</form>



			</div>
			<div class="col-md-8 custom-scrollbar-css"
				style="max-height: 100%; padding: 10px; overflow-y: scroll;">





				<div id="copyToastWrapper" aria-live="polite" aria-atomic="true"
					style="position: relative;">
					<div id="copyToast" class="toast"
						style="min-width: 200px; z-index: 9; position: fixed; top: 20px; right: 20px;"
						role="alert" data-delay="10000" aria-live="assertive"
						aria-atomic="true">
						<div class="toast-header">
							<strong class="mr-auto">Copied to clipboard !</strong>
							<button type="button" class="ml-2 mb-1 close"
								data-dismiss="toast" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div id="copyToastBody" class="toast-body">Hotfix details
							copied.</div>
					</div>
				</div>
				<!-- 	<img alt="Test image" src="image/search_graphic.png"> -->
				<%
					if (!"searchClicked".equals(request.getParameter("searchBtn"))) {
				%>
				<div class="background-search-img"></div>

				<%
					}
				%>
				<jsp:include page="hotfixResult.jsp"></jsp:include>


				<div
					style="opacity: 0.8; position: fixed; bottom: 10px; right: 60px; z-index: 6;">
					<button class="btn btn-warning text-white" data-toggle="modal"
						data-target="#guideline_modal">
						<i class="fas fa-info-circle"></i>
					</button>
				</div>

				<!-- Modal -->
				<div class="modal fade" id="guideline_modal" tabindex="-1"
					role="dialog" aria-labelledby="custom_modal_title"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered"
						style="max-width: 700px" role="document">
						<div class="modal-content">

							<div class="modal-header">
								<h5 class="modal-title" id="custom_modal_title">Information</h5>
							</div>
							<div class="modal-body">
								<ul>
									<li>Results are order by <code>Released date</code>.
									</li>
									<li>Click on the Hotfix number to <code>copy</code> the
										details to clipboard.
									</li>
									<li>Results are searched by <code>Regular
											Expression</code> according to input.
									</li>
									<li>If the HF is faulty, <span
										class="badge badge-pill badge-danger">FAULTY</span> will be
										displayed.
									</li>
									<li>If the HF is latest, <span
										class="badge badge-pill badge-success">LATEST</span> will be
										displayed.
									</li>
								</ul>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-sm btn-custom-bg text-white"
									data-dismiss="modal" id="result_close_btn">Close</button>

							</div>
						</div>
					</div>
				</div>

				<div
					style="opacity: 0.8; position: fixed; bottom: 10px; right: 10px; z-index: 6;">
					<button class="btn btn-warning text-white" type="submit"
						name="profileBtn" value="profileClicked" data-toggle="modal"
						data-target="#info_modal">
						<i class="fas fa-paper-plane"></i>
					</button>
				</div>

				<!-- Modal -->
				<div class="modal fade text-center" id="info_modal" tabindex="-1"
					role="dialog" aria-labelledby="custom_modal_title"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">

							<div class="modal-body" id="custom_modal_body">
								<div class="circle-image ">
									<img alt="info_image" src="../image/profile.jpg"
										class="profile-pic">
								</div>
								<br>
								<h4 class="modal-title" id="custom_modal_title">
									<b>Avinash Tingre</b>
								</h4>
								Software Support Engineer<br>with 2.5+ years of experience<br>
								<em>(OSS RM & Planning)</em><br>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="btn btn-sm btn-custom-bg text-white"
									data-dismiss="modal" id="result_close_btn">Close</button>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>
				<div class="row">
					<div id="browserWarningWrapper" aria-live="polite" aria-atomic="true"
					style="position: relative; z-index: 9" class="d-flex justify-content-center align-items-center">
					<div id="browserWarning" class="toast" data-autohide="false"
						style="min-width: 300px; position: fixed;top: 50px; left:50%; margin-left:-150px;"
						role="alert" aria-live="assertive"
						aria-atomic="true">
						<div class="toast-header">
							<strong class="mr-auto text-danger">Warning !</strong>
							<!-- <button type="button" class="ml-2 mb-1 close"
								data-dismiss="toast" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button> -->
						</div>
						
						<div id="browserWarningBody" class="toast-body bg-secondary text-white">Currently not supported in <em><b>Internet Explorer.</b></em><br>
						Try with <b>Google Chrome.</b></div>
					</div>
				</div>
				</div>
	

	<script type="text/javascript" src="js/jquery-3.5.1.slim.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#copyToast').toast('hide');
		});
	</script>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="tooltip"]').tooltip()
		})
	</script>
	<script type="text/javascript">
	
		function checkFields(form) {
			var checks_radios = form.find(':checkbox, :radio'), inputs = form
					.find(':input').not(checks_radios).not(
							'[type="submit"],[type="button"],[type="reset"]');

			var checked = checks_radios.filter(':checked');
			var filled = inputs.filter(function() {
				return $.trim($(this).val()).length > 0;
			});

			if (checked.length + filled.length === 0) {
				return false;
			}

			return true;
		}

		$(function() {
			$('#searchForm1')
					.on(
							'submit',
							function(e) {
								e.preventDefault();
								var oneFilled = checkFields($(this));
								if (oneFilled) {
									document.getElementById("searchBtn").value = "searchClicked";
									document.getElementById("searchBtn")
											.submit();
								} else {
									alert('At least one field is required !');
								}
							});
		});

		function filterModules() {
			var input, filter, ul, li, a, i;
			input = document.getElementById("inputModule");
			filter = input.value.toUpperCase();
			div = document.getElementById("moduleList");
			a = div.getElementsByTagName("div");
			for (i = 0; i < a.length; i++) {
				txtValue = a[i].textContent || a[i].innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					a[i].style.display = "";
				} else {
					a[i].style.display = "none";
				}
			}
		}

		function filterVersions() {
			var input, filter, ul, li, a, i;
			input = document.getElementById("inputVersion");
			filter = input.value.toUpperCase();
			div = document.getElementById("versionList");
			a = div.getElementsByTagName("div");
			for (i = 0; i < a.length; i++) {
				txtValue = a[i].textContent || a[i].innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					a[i].style.display = "";
				} else {
					a[i].style.display = "none";
				}
			}
		}

		document.onreadystatechange = function() { 
            if (document.readyState !== "complete") { 
                document.querySelector( 
                  "body").style.visibility = "hidden"; 
                document.querySelector( 
                  "#se-pre-con").style.visibility = "visible"; 
            } else { 
                document.querySelector( 
                  "#se-pre-con").style.display = "none"; 
                document.querySelector( 
                  "body").style.visibility = "visible"; 
            } 
        }; 

        (function () {
            var googleChrome = {
              string: navigator.userAgent.match(/Chrome\/(\d+)/)
              
            };
			
            googleChrome.version = googleChrome.string ? parseInt(googleChrome.string[1], 10) : null;

            if (!googleChrome.string) {
              document.getElementById("container-fluid").style.display= "none";
      		  document.getElementById('browserWarningWrapper').style.display="block";
    		  $('#browserWarning').toast('show');
    		  
            }else{
            	document.getElementById('browserWarning').style.display="none";
            	$('#browserWarning').toast('hide');
            	
            	}
        })();
                
	</script>
</body>
</html>
