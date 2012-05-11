package com.percussion.forum;

import com.percussion.extension.IPSJexlMethod;
import com.percussion.extension.PSJexlUtilBase;
import com.percussion.server.PSRequest;
import com.percussion.server.PSRequestContext;
import com.percussion.services.assembly.jexl.PSAssemblerUtils;
import com.percussion.utils.request.PSRequestInfo;
import com.percussion.xmldom.PSXmlDomContext;
import com.percussion.xmldom.PSXmlDomUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang.StringUtils;

public class ExternalContent extends PSJexlUtilBase {

	@IPSJexlMethod(description="Returns the String provided", params={@com.percussion.extension.IPSJexlParam(name="string", description="the JSON string")})
	public String parse(String string) throws Exception
	{
		String html = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(string).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			html = getTidyContent(sb.toString());
		} catch (Exception e){
			e.printStackTrace();
		}
		return html;
	}
	
	public static String getTidyContent(String string) throws Exception {
		if (StringUtils.isBlank(string))
		      return "";
		    PSRequest localPSRequest = (PSRequest)PSRequestInfo.getRequestInfo("PSREQUEST");
		    PSXmlDomContext localPSXmlDomContext = new PSXmlDomContext(PSAssemblerUtils.class.getName(), new PSRequestContext(localPSRequest));
		    localPSXmlDomContext.setRxCommentHandling(true);
		    localPSXmlDomContext.setTidyProperties("rxW2Ktidy.properties");
		    localPSXmlDomContext.setServerPageTags("rxW2KserverPageTags.xml");
		    localPSXmlDomContext.setUsePrettyPrint(false);
		    string = PSXmlDomUtils.tidyInput(localPSXmlDomContext, string);
		    String str = string.toLowerCase();
		    if ((StringUtils.contains(str, "<body")) && (StringUtils.contains(str, "</body>")))
		    {
		      string = StringUtils.substring(string, str.indexOf("<body"));
		      string = StringUtils.substring(string, string.indexOf(">") + 1);
		      string = StringUtils.substring(string, 0, string.toLowerCase().lastIndexOf("</body>"));
		    }
		    return string;
	}
}
