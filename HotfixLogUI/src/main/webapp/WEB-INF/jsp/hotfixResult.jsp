<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.net.InetAddress"%>
<%@page import="com.avinash.HotfixLogUI.Model.ECPLog"%>
<%@page import="com.avinash.HotfixLogUI.Model.ECPLogResponseObject"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

</head>
<body ">

	<%
	
		if ("searchClicked".equals(request.getParameter("searchBtn"))) {
			
			InetAddress ip = InetAddress.getByName(request.getRemoteAddr());
			String hostname = ip.getHostName();

			String[] arrVersions = request.getParameterValues("SelectedVersion");
			List<String> selectedVersions = new ArrayList<String>();
			if (arrVersions != null) {
				for (String s : arrVersions) {
					selectedVersions.add(s);
				}
			} else {
				selectedVersions.add("");
			}
			if (selectedVersions.contains("Cramer 5 Rel 1")) {
				selectedVersions.add("cramer 5 rel 1");
			}

			if (selectedVersions.contains("Cramer 6 Rel 1")) {
				selectedVersions.add("Cramer 6 rel 1");
				selectedVersions.add("cramer 6 rel 1");
			}
			if (selectedVersions.contains("Cramer 6 Rel 2")) {
				selectedVersions.add("Cramer 6 rel 2");
			}
			if (selectedVersions.contains("Cramer 6 Rel 3")) {
				selectedVersions.add("Cramer 6 rel 3");
				selectedVersions.add("cramer 6 rel 3");
			}
			if (selectedVersions.contains("Cramer 6 Rel 4")) {
				selectedVersions.add("Cramer 6 rel 4");
			}
			if (selectedVersions.contains("OSS 9.1.0")) {
				selectedVersions.add("oss 9.0.0");
			}

			String[] arrModules = request.getParameterValues("SelectedModule");
			List<String> selectedModules = new ArrayList<String>();
			if (arrModules != null) {
				for (String s : arrModules) {
					selectedModules.add(s);
				}
			} else {
				selectedModules.add("");
			}

			if (selectedModules.contains("Foundations")) {
				selectedModules.add("foundations");
			}

			String ecpNo = request.getParameter("ecpNo").trim();
			String latestHF=request.getParameter("latestHotfix").trim();
			String description = request.getParameter("description").trim();
			String requestor = request.getParameter("requestor").trim();
			String fixedBy = request.getParameter("fixedBy").trim();
			String caseOrCrNo = request.getParameter("caseOrCrNo").trim();
			String filesModifiedInPerforce = request.getParameter("filesModifiedInPerforce").trim();
			String filesReleasedToCustomer = request.getParameter("filesReleasedToCustomer").trim();
			String specificFunc = request.getParameter("specificFunc").trim();

			if (!"".equals(description) || !"".equals(ecpNo) || !"".equals(latestHF) || !"".equals(requestor) || !"".equals(fixedBy)
					|| !"".equals(caseOrCrNo) || !"".equals(filesModifiedInPerforce)
					|| !"".equals(filesReleasedToCustomer) || !"".equals(specificFunc)
					|| !selectedModules.contains("") || !selectedVersions.contains("")) {

				StringBuilder sb = new StringBuilder();
				int i = 0;
				while (i < selectedModules.size()) {
					sb.append(selectedModules.get(i));
					if (i != selectedModules.size() - 1) {
						sb.append(",");
					}
					i++;
				}
				String modules = sb.toString();
				sb.delete(0, sb.length());

				int j = 0;
				while (j < selectedVersions.size()) {
					sb.append(selectedVersions.get(j));
					if (j != selectedVersions.size() - 1) {
						sb.append(",");
					}
					j++;
				}
				String versions = sb.toString();
				sb.delete(0, sb.length());

				String paramString = "?ecpNo=" + URLEncoder.encode(ecpNo, "UTF-8")
				+"&latestEcp=" + URLEncoder.encode(latestHF, "UTF-8")
				+ "&description="
						+ URLEncoder.encode(description, "UTF-8") + "&cramerVersion="
						+ URLEncoder.encode(versions, "UTF-8") + "&requestor="
						+ URLEncoder.encode(requestor, "UTF-8") + "&fixedBy=" + URLEncoder.encode(fixedBy, "UTF-8")
						+ "&module=" + URLEncoder.encode(modules, "UTF-8") + "&caseOrCrNo="
						+ URLEncoder.encode(caseOrCrNo, "UTF-8") + "&filesModifiedInPerforce="
						+ URLEncoder.encode(filesModifiedInPerforce, "UTF-8") + "&filesReleasedToCustomer="
						+ URLEncoder.encode(filesReleasedToCustomer, "UTF-8") + "&specificFunc="
						+ URLEncoder.encode(specificFunc, "UTF-8");

				String plainInput = "?ecpNo=" + ecpNo +"&latestEcp="+latestHF + "&description=" + description + "&cramerVersion=" + versions
						+ "&requestor=" + requestor + "&fixedBy=" + fixedBy + "&module=" + modules + "&caseOrCrNo="
						+ caseOrCrNo + "&filesModifiedInPerforce=" + filesModifiedInPerforce
						+ "&filesReleasedToCustomer=" + filesReleasedToCustomer + "&specificFunc=" + specificFunc;
				ObjectMapper objectMapper = new ObjectMapper();
				HttpURLConnection con = null;
				try {

					URL url = new URL("http://localhost:4569/HFLogViewer/getAllResults" + paramString);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("Content-Type", "application/json");
					con.setRequestProperty("Hostname", hostname);
					con.setRequestProperty("SearchIput", plainInput);
					con.setConnectTimeout(5000);
					con.setReadTimeout(5000);

					int status = con.getResponseCode();
					if (status > 299) {
						Reader streamReader = null;

						BufferedReader in = new BufferedReader(streamReader);
						String inputLine;
						StringBuffer content = new StringBuffer();
						while ((inputLine = in.readLine()) != null) {
							content.append(inputLine);
						}
						in.close();
						System.out.println("Below error occured: \n" + content.toString());

					} else {
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer content = new StringBuffer();
						while ((inputLine = in.readLine()) != null) {
							content.append(inputLine);
						}
						in.close();
						String reponse = content.toString();

						ECPLogResponseObject respObject = objectMapper.readValue(reponse,
								ECPLogResponseObject.class);
						System.out.println("Results found: " + respObject.getCount());
						if (respObject.getCount() > 0) {
	%>
	<div class="alert alert-success text-center" role="alert">
		<h6 class="alert-heading">
			Results found:
			<b><%=respObject.getCount()%></b></h6>

	</div>
	<div class="list-group " id="hflist">
		<%
			for (int count = 0; count < respObject.getCount(); count++) {
				ECPLog ecp = new ECPLog();
				ecp = respObject.getRecords().get(count);
		%>

		<a class="list-group-item list-group-item-action hf-details-parent"
			data-toggle="collapse" href="#hfCollapse<%=ecp.get_id()%>"
			aria-expanded="false" aria-controls="hfCollapse<%=ecp.get_id()%>">
			<div class="row hf-list-top">
				<div class="col col-sm-4">
					<div id="ecpno_<%=ecp.get_id()%>" data-toggle="tooltip"
						data-placement="bottom" title="Click to Copy"
						onclick="copyToClipboard('ecpno_<%=ecp.get_id()%>', 'description_<%=ecp.get_id()%>')"><%=ecp.getEcpNo()%></div>
					<%
						if ("YES".equalsIgnoreCase(ecp.getEcpFaulty())) {
					%>
					<span class="badge badge-danger badge-pill">FAULTY</span>
					<%
						}
												if ("TRUE".equalsIgnoreCase(ecp.getIsThisLatestHF())) {
					%>
					<span class="badge badge-success badge-pill">LATEST</span>
					<%
						}
					%>




				</div>
				<div class="col col-sm-8">
					<small><b>Description:</b></small><br>
					<div id="description_<%=ecp.get_id()%>"><%=ecp.getDescription()%></div>
				</div>
			</div>

		</a>


		<div class="collapse multi-collapse hf-details-child"
			data-parent="#hflist" id="hfCollapse<%=ecp.get_id()%>">
			<%
				if ("TRUE".equals(ecp.getIsThisLatestHF())) {
			%>
			<div class="bd-callout bd-callout-success">
				<%
					} else {
				%>
				<div class="bd-callout bd-callout-warning">
					<%
						}
					%>
					<!-- Row 1 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Prereq's for latest HF</span><br>
								<textarea class="form-control" aria-label="With textarea"
									disabled><%=ecp.getPrereqForLatestEcp()%>
                        </textarea>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Latest HF</span><br>
								<%=ecp.getLatestEcp()%>
							</div>
						</div>



						<div class="col">
							<div>
								<span class="badge badge-secondary">Case/CR No.</span><br>
								<%=ecp.getCaseOrCrNo()%>
							</div>

						</div>
					</div>


					<!-- Row 2 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Version</span><br>
								<%=ecp.getCramerVersion()%>
							</div>


						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Rolled into version</span><br>
								<%=ecp.getRolledIntoVersion()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Rollup CR</span> <br><%=ecp.getRollupCr()%>
							</div>

						</div>
					</div>

					<!-- Row 3 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Module</span><br>
								<%=ecp.getModule()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Is mandatory ?</span><br>
								<%=ecp.getMandatoryEcp()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Status</span><br>
								<%=ecp.getStatus()%>
							</div>

						</div>
					</div>

					<!-- Row 4 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Specific function</span><br>
								<%=ecp.getSpecificFunc()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Severity</span><br>
								<%=ecp.getSeverity()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Priority</span><br>
								<%=ecp.getPriority()%>
							</div>

						</div>
					</div>

					<!-- Row 5 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Requested by</span><br>
								<%=ecp.getRequestor()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Fixed by</span><br>
								<%=ecp.getFixedBy()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Notes</span><br>
								<textarea class="form-control" aria-label="With textarea"
									disabled><%=ecp.getNotes()%>
                        </textarea>
							</div>

						</div>
					</div>

					<!-- Row 6 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Requested on</span><br>
								<%=ecp.getRequestDate()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Released on</span><br>
								<%=ecp.getReleasedDate()%>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Additional info</span><br>
								<textarea class="form-control" aria-label="With textarea"
									disabled><%=ecp.getAdditionalInfo()%>
                        </textarea>
							</div>

						</div>
					</div>

					<!-- Row 7 -->
					<div class="row">
						<div class="col">
							<div>
								<span class="badge badge-secondary">Files modified</span><br>
								<textarea class="form-control" aria-label="With textarea"
									disabled><%=ecp.getFilesModifiedInPerforce()%>
                        </textarea>
							</div>

						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Files location</span><br>
								<textarea class="form-control" aria-label="With textarea"
									disabled><%=ecp.getFileLocationInPerforce()%>
                        </textarea>
							</div>
						</div>
						<div class="col">
							<div>
								<span class="badge badge-secondary">Files released</span><br>
								<textarea class="form-control"  aria-label="With textarea"
									disabled><%=ecp.getFilesReleasedToCustomer()%>
                            </textarea>

							</div>
						</div>
					</div>

					<!-- Row 8 -->
					<div class="row">
						<div class="col"></div>
						<div class="col"></div>
						<div class="col"></div>
					</div>
				</div>


			</div>

			<%
				}
			%>
		</div>

		<%
			} else {
								System.out.println("No records found!");
		%>
		<div class="alert alert-warning" role="alert">
			<h4 class="alert-heading">Oops !</h4>
			<p>No matching records found.</p>
			<hr>
			<p class="mb-0">Try again with improved criteria.</p>
		</div>
		<%
			}

						}

					} catch (Exception ex) {
						System.out.println("Server Down.\n" + ex.getMessage());
						ex.printStackTrace();
		%>
		<div class="alert alert-warning" role="alert">
			<h4 class="alert-heading">Oops !</h4>
			<p>
				Operation failed due to any of below reasons:<br> 1. Server is
				down.<br> 2. Connection timed out. <br>
			</p>
			<hr>
			<p class="mb-0">Try again with improved criteria.</p>
		</div>
		<%
			} finally {
						con.disconnect();
						System.out.println("Connection closed.");
					}
				} else {
		%>
		<div class="alert alert-warning" role="alert">
			<h4 class="alert-heading">Oops !</h4>
			<p>
				Operation failed due to any of below reasons:<br> 1. No input
				is provided.<br>
			</p>
			<hr>
			<p class="mb-0">Try again with improved criteria.</p>
		</div>
		<%
			System.out.println("All fields are empty.");
				}

			}
		%>
	</div>
	<script type="text/javascript">
		function copyToClipboard(ecpNo, description) {

			const myTextarea = document.createElement('textarea');

			var copyString = 'Hotfix : '
					+ document.getElementById(ecpNo).innerHTML
					+ '\r\nDescription: '
					+ document.getElementById(description).innerHTML;

			myTextarea.innerHTML = copyString;

			document.body.appendChild(myTextarea);

			myTextarea.select();

			document.execCommand('copy');

			document.body.removeChild(myTextarea);
			document.getElementById('copyToastWrapper').style.display="block";
			document.getElementById('copyToastBody').innerHTML =document.getElementById(ecpNo).innerHTML;
			
			$('#copyToast').toast('show');


		}
		
	</script>
</body>
</html>